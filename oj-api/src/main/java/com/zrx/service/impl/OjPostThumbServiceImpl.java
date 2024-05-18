package com.zrx.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjPostMapper;
import com.zrx.mapper.OjPostThumbMapper;
import com.zrx.mapstruct.OjPostConverter;
import com.zrx.model.common.Paging;
import com.zrx.model.entity.OjPost;
import com.zrx.model.entity.OjPostThumb;
import com.zrx.model.vo.OjPostVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjPostThumbService;
import com.zrx.utils.PostUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zrx.model.entity.table.OjPostTableDef.OJ_POST;
import static com.zrx.model.entity.table.OjPostThumbTableDef.OJ_POST_THUMB;

/**
 * 帖子点赞 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Service
public class OjPostThumbServiceImpl extends ServiceImpl<OjPostThumbMapper, OjPostThumb> implements OjPostThumbService {

	@Resource
	private OjPostMapper postMapper;

	@Resource
	private OjPostThumbMapper thumbMapper;

	@Resource
	private OjPostConverter postConverter;

	@Resource
	private PostUtil postUtil;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean thumbPost(Long id) {
		if (postMapper.selectOneById(id) == null) {
			throw new BusinessException("帖子不存在");
		}
		Long userId = SecurityHelper.getUser().getId();
		OjPostThumb thumb = new OjPostThumb();
		thumb.setPostId(id);
		thumb.setUserId(userId);
		synchronized (this) {
			if (thumbMapper
				.selectOneByCondition(OJ_POST_THUMB.USER_ID.eq(userId).and(OJ_POST_THUMB.POST_ID.eq(id))) != null) {
				throw new BusinessException("帖子已点赞");
			}
			thumbMapper.insert(thumb);
			UpdateChain.of(postMapper)
				.set(OJ_POST.THUMB_NUM, OJ_POST.THUMB_NUM.add(1))
				.where(OJ_POST.ID.eq(id))
				.update();
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean cancelThumbPost(Long postId) {
		if (postMapper.selectOneById(postId) == null) {
			throw new BusinessException("帖子不存在");
		}
		Long userId = SecurityHelper.getUser().getId();
		synchronized (this) {
			if (thumbMapper
				.selectOneByCondition(OJ_POST_THUMB.USER_ID.eq(userId).and(OJ_POST_THUMB.POST_ID.eq(postId))) == null) {
				throw new BusinessException("帖子未点赞");
			}
			thumbMapper.deleteByCondition(OJ_POST_THUMB.USER_ID.eq(userId).and(OJ_POST_THUMB.POST_ID.eq(postId)));
			UpdateChain.of(postMapper)
				.set(OJ_POST.THUMB_NUM, OJ_POST.THUMB_NUM.subtract(1))
				.where(OJ_POST.ID.eq(postId))
				.update();
		}
		return true;
	}

	@Override
	public Page<OjPostVo> page(Paging page, String userId) {
		Page<OjPostThumb> paginate = thumbMapper.paginate(Page.of(page.getPageNum(), page.getPageSize()),
				new QueryWrapper().where(OJ_POST_THUMB.USER_ID.eq(userId)));
		List<Long> postIdList = paginate.getRecords().stream().map(OjPostThumb::getPostId).toList();
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
