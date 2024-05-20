package com.zrx.service.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.enums.PostZoneEnums;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjPostFavourMapper;
import com.zrx.mapper.OjPostMapper;
import com.zrx.mapper.OjPostThumbMapper;
import com.zrx.mapstruct.OjPostConverter;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.post.OjPostAddRequest;
import com.zrx.model.dto.post.OjPostQueryRequest;
import com.zrx.model.dto.post.OjPostUpdateRequest;
import com.zrx.model.entity.OjPost;
import com.zrx.model.vo.OjPostSimpleVo;
import com.zrx.model.vo.OjPostVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjPostService;
import com.zrx.utils.PostUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zrx.model.entity.table.OjPostFavourTableDef.OJ_POST_FAVOUR;
import static com.zrx.model.entity.table.OjPostTableDef.OJ_POST;
import static com.zrx.model.entity.table.OjPostThumbTableDef.OJ_POST_THUMB;

/**
 * 帖子 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Service
public class OjPostServiceImpl extends ServiceImpl<OjPostMapper, OjPost> implements OjPostService {

	@Resource
	private OjPostConverter postConverter;

	@Resource
	private OjPostMapper postMapper;

	@Resource
	private OjPostThumbMapper thumbMapper;

	@Resource
	private OjPostFavourMapper favourMapper;

	@Resource
	private PostUtil postUtil;

	@Override
	public Boolean save(OjPostAddRequest req) {
		if (!PostZoneEnums.isValid(req.getZone())) {
			throw new BusinessException("分区不存在");
		}
		return postMapper.insert(postConverter.toEntity(req)) == 1;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removePostById(Long id) {
		checkPermission(id);
		thumbMapper.deleteByCondition(OJ_POST_THUMB.POST_ID.eq(id));
		favourMapper.deleteByCondition(OJ_POST_FAVOUR.POST_ID.eq(id));
		return postMapper.deleteById(id) == 1;
	}

	private void checkPermission(Long postId) {
		OjPost post = postMapper.selectOneById(postId);
		if (post == null) {
			throw new BusinessException("未找到该帖子");
		}
		String creator = post.getCreator();
		Long id = SecurityHelper.getUser().getId();
		if (!Long.valueOf(creator).equals(id)) {
			throw new BusinessException("不具有编辑该帖子的权限");
		}
	}

	@Override
	public Boolean updateById(OjPostUpdateRequest req) {
		if (!PostZoneEnums.isValid(req.getZone())) {
			throw new BusinessException("分区不存在");
		}
		checkPermission(req.getId());
		return postMapper.update(postConverter.toEntityUpdate(req)) == 1;
	}

	@Override
	public OjPostVo getInfoById(String id) {
		OjPost post = postMapper.selectOneById(id);
		if (post == null) {
			throw new BusinessException("未找到该帖子");
		}
		UpdateChain.of(postMapper).set(OJ_POST.VIEW_NUM, OJ_POST.VIEW_NUM.add(1)).where(OJ_POST.ID.eq(id)).update();
		OjPostVo vo = postConverter.toVo(post);
		postUtil.setPostAuthor(Lists.newArrayList(vo));
		setZoneName(vo);
		try {
			setThumbFlag(vo);
			setFavourFlag(vo);
		} catch (NotLoginException e) {
			// 未登录，不做处理
		}
		return vo;
	}

	private void setThumbFlag(OjPostVo ojPostVo) {
		Long userId = SecurityHelper.getUser().getId();
		Long postId = ojPostVo.getId();
		synchronized (this) {
			ojPostVo.setThumbFlag(thumbMapper
				.selectCountByCondition(OJ_POST_THUMB.POST_ID.eq(postId).and(OJ_POST_THUMB.USER_ID.eq(userId))) > 0);
		}
	}

	private void setFavourFlag(OjPostVo ojPostVo) {
		Long userId = SecurityHelper.getUser().getId();
		synchronized (this) {
			ojPostVo.setFavourFlag(favourMapper.selectCountByCondition(
					OJ_POST_FAVOUR.POST_ID.eq(ojPostVo.getId()).and(OJ_POST_FAVOUR.USER_ID.eq(userId))) > 0);
		}
	}

	private void setZoneName(OjPostVo ojPostVo) {
		ojPostVo.setZoneName(PostZoneEnums.getTextByValue(ojPostVo.getZone()));
	}

	@Override
	public Page<OjPostVo> page(Paging page, OjPostQueryRequest req, Boolean selfFlag) {
		if (StringUtil.isNotBlank(req.getZone()) && !PostZoneEnums.isValid(req.getZone())) {
			return new Page<>();
		}
		List<String> tags = req.getTags();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper
			.select(OJ_POST.ID, OJ_POST.TITLE, OJ_POST.ZONE, OJ_POST.TAGS, OJ_POST.CREATE_TIME, OJ_POST.THUMB_NUM,
					OJ_POST.FAVOUR_NUM, OJ_POST.VIEW_NUM, OJ_POST.CREATOR,
					QueryMethods.substring(OJ_POST.CONTENT, 1, 100).as("content"))
			.where(OJ_POST.TITLE.like(req.getTitle(), StringUtil::isNotBlank))
			.and(OJ_POST.CREATOR.eq(req.getUserId(), StringUtil::isNotBlank))
			.and(OJ_POST.ZONE.eq(req.getZone(), StringUtil::isNotBlank));
		if (CollUtil.isNotEmpty(tags)) {
			for (String tag : tags) {
				queryWrapper.and(OJ_POST.TAGS.like("\"" + tag + "\""));
			}
		}
		if (selfFlag) {
			// 查询当前用户的帖子
			Long userId = SecurityHelper.getUser().getId();
			queryWrapper.and(OJ_POST.CREATOR.eq(userId));
		}
		queryWrapper.orderBy(OJ_POST.VIEW_NUM.desc());
		Page<OjPost> paginate = postMapper.paginate(Page.of(page.getPageNum(), page.getPageSize()), queryWrapper);
		Page<OjPostVo> voPage = postConverter.toVoPage(paginate);
		postUtil.setPostAuthor(voPage.getRecords());
		postUtil.setPostZoneName(voPage.getRecords());
		return voPage;
	}

	@Override
	public List<OjPostSimpleVo> getFiveHotPost() {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select(OJ_POST.ID, OJ_POST.TITLE)
			.orderBy(OJ_POST.VIEW_NUM.desc(), OJ_POST.THUMB_NUM.desc(), OJ_POST.FAVOUR_NUM.desc())
			.limit(5);
		List<OjPost> ojPosts = mapper.selectListByQuery(queryWrapper);
		return postConverter.toSimpleVoList(ojPosts);
	}

}
