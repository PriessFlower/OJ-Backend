package com.zrx.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjPostFavourMapper;
import com.zrx.mapper.OjPostMapper;
import com.zrx.mapstruct.OjPostConverter;
import com.zrx.model.common.Paging;
import com.zrx.model.entity.OjPost;
import com.zrx.model.entity.OjPostFavour;
import com.zrx.model.vo.OjPostVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjPostFavourService;
import com.zrx.utils.PostUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zrx.model.entity.table.OjPostFavourTableDef.OJ_POST_FAVOUR;
import static com.zrx.model.entity.table.OjPostTableDef.OJ_POST;

/**
 * 帖子收藏 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Service
public class OjPostFavourServiceImpl extends ServiceImpl<OjPostFavourMapper, OjPostFavour>
		implements OjPostFavourService {

	@Resource
	private OjPostMapper postMapper;

	@Resource
	private OjPostFavourMapper favourMapper;

	@Resource
	private OjPostConverter postConverter;

	@Resource
	private PostUtil postUtil;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean favourPost(Long id) {
		if (postMapper.selectOneById(id) == null) {
			throw new BusinessException("帖子不存在");
		}
		Long userId = SecurityHelper.getUser().getId();
		synchronized (this) {
			if (favourMapper
				.selectOneByCondition(OJ_POST_FAVOUR.USER_ID.eq(userId).and(OJ_POST_FAVOUR.POST_ID.eq(id))) != null) {
				throw new BusinessException("帖子已收藏");
			}
			OjPostFavour favour = new OjPostFavour();
			favour.setPostId(id);
			favour.setUserId(userId);
			favourMapper.insert(favour);
			UpdateChain.of(postMapper)
				.set(OJ_POST.FAVOUR_NUM, OJ_POST.FAVOUR_NUM.add(1))
				.where(OJ_POST.ID.eq(id))
				.update();
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean cancelFavourPost(Long postId) {
		if (postMapper.selectOneById(postId) == null) {
			throw new BusinessException("帖子不存在");
		}
		synchronized (this) {
			if (favourMapper.selectOneByCondition(OJ_POST_FAVOUR.USER_ID.eq(SecurityHelper.getUser().getId())
				.and(OJ_POST_FAVOUR.POST_ID.eq(postId))) == null) {
				throw new BusinessException("帖子未收藏");
			}
			Long userId = SecurityHelper.getUser().getId();
			favourMapper.deleteByCondition(OJ_POST_FAVOUR.USER_ID.eq(userId).and(OJ_POST_FAVOUR.POST_ID.eq(postId)));
			UpdateChain.of(postMapper)
				.set(OJ_POST.FAVOUR_NUM, OJ_POST.FAVOUR_NUM.subtract(1))
				.where(OJ_POST.ID.eq(postId))
				.update();
		}
		return true;
	}

	@Override
	public Page<OjPostVo> page(Paging page, String userId) {
		Page<OjPostFavour> paginate = favourMapper.paginate(Page.of(page.getPageNum(), page.getPageSize()),
				new QueryWrapper().where(OJ_POST_FAVOUR.USER_ID.eq(userId)));
		List<Long> postIdList = paginate.getRecords().stream().map(OjPostFavour::getPostId).toList();
		if (postIdList.isEmpty()) {
			return new Page<>();
		}
		List<OjPost> ojPosts = postMapper.selectListByIds(postIdList);
		if (ojPosts.isEmpty()) {
			return new Page<>();
		}
		Page<OjPostVo> ojPostVoPage = new Page<>(paginate.getPageNumber(), paginate.getPageSize());
		ojPostVoPage.setTotalPage(paginate.getTotalPage());
		ojPostVoPage.setTotalRow(paginate.getTotalRow());
		ojPostVoPage.setRecords(postConverter.toVoList(ojPosts));
		postUtil.setPostAuthor(ojPostVoPage.getRecords());
		return ojPostVoPage;
	}

}
