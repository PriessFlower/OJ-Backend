package com.zrx.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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
import com.zrx.model.vo.OjPostVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjPostService;
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

    @Override
    public Boolean save(OjPostAddRequest req) {
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
        checkPermission(req.getId());
        return postMapper.update(postConverter.toEntityUpdate(req)) == 1;
    }

    @Override
    public OjPostVo getInfoById(Long id) {
        UpdateChain.of(postMapper)
                .set(OJ_POST.VIEW_NUM, OJ_POST.VIEW_NUM.add(1))
                .where(OJ_POST.ID.eq(id))
                .update();
        return postConverter.toVo(postMapper.selectOneById(id));
    }

    @Override
    public Page<OjPostVo> page(Paging page, OjPostQueryRequest req) {
        List<String> tags = req.getTags();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(OJ_POST.DEFAULT_COLUMNS)
                .where(OJ_POST.TITLE.like(req.getTitle()));
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.and(OJ_POST.TAGS.like("\"" + tag + "\""));
            }
        }
        queryWrapper.orderBy(OJ_POST.VIEW_NUM.desc());
        Page<OjPost> paginate = postMapper.paginate(Page.of(page.getPageNum(), page.getPageSize()), queryWrapper);
        return postConverter.toVoPage(paginate);
    }

}
