package com.zrx.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Sets;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.mapper.OjPostCommentMapper;
import com.zrx.mapper.OjPostMapper;
import com.zrx.mapstruct.OjPostCommentConverter;
import com.zrx.model.dto.postComment.PostCommentRequest;
import com.zrx.model.entity.OjPostComment;
import com.zrx.model.vo.PostCommentVo;
import com.zrx.security.utils.SecurityHelper;
import com.zrx.service.OjPostCommentService;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.model.entity.SysUser;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zrx.model.entity.table.OjPostCommentTableDef.OJ_POST_COMMENT;
import static com.zrx.model.entity.table.OjPostTableDef.OJ_POST;
import static com.zrx.sys.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 帖子评论 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/5/20
 */
@Service
public class OjPostCommentServiceImpl extends ServiceImpl<OjPostCommentMapper, OjPostComment> implements OjPostCommentService {

    @Resource
    private OjPostCommentConverter converter;

    @Resource
    private OjPostCommentMapper commentMapper;

    @Resource
    private OjPostMapper postMapper;

    @Resource
    private SysUserMapper userMapper;

    @Override
    public Boolean save(PostCommentRequest req) {
        SysUser user = SecurityHelper.getUser();
        long count = postMapper
                .selectCountByCondition(OJ_POST.ID.eq(req.getPostId(), req.getPostId() != null));
        if (count == 0) {
            throw new BusinessException("帖子不存在");
        }
        OjPostComment entity = converter.toEntity(req);
        entity.setAuthorId(user.getId());
        return save(entity);
    }

    @Override
    public List<PostCommentVo> listChildren(String postId) {
        QueryWrapper queryWrapper = new QueryWrapper().where(OJ_POST_COMMENT.PARENT_ID.eq(postId, StringUtil::isNotBlank));
        List<OjPostComment> postComments = commentMapper.selectListByQuery(queryWrapper);
        Set<Long> userIds = postComments.stream().map(OjPostComment::getAuthorId).collect(Collectors.toSet());
        List<PostCommentVo> voList = converter.toVoList(postComments);
        findUserInfo(voList, userIds);
        return voList;
    }

    /**
     * 递归查询子节点
     */
    private void findChildren(PostCommentVo e, Set<Long> userIds) {
        userIds.add(e.getAuthorId());
        List<OjPostComment> list = QueryChain.of(commentMapper).where(OJ_POST_COMMENT.PARENT_ID.eq(e.getId())).list();
        if (CollUtil.isEmpty(list)) {
            return;
        }
        e.setChildren(converter.toVoList(list));
        e.getChildren().forEach(child -> findChildren(child, userIds));
    }


    @Override
    public List<PostCommentVo> list(String postId) {
        QueryWrapper queryWrapper = new QueryWrapper()
                .where(OJ_POST_COMMENT.POST_ID.eq(postId, StringUtil::isNotBlank))
                .and(OJ_POST_COMMENT.PARENT_ID.isNull());
        List<OjPostComment> postComments = commentMapper.selectListByQuery(queryWrapper);
        List<PostCommentVo> voList = converter.toVoList(postComments);
        Set<Long> userIds = Sets.newHashSet();
        // 查询所有子节点
        voList.forEach(e -> findChildren(e, userIds));
        findUserInfo(voList, userIds);
        return voList;
    }

    /**
     * 获取用户信息
     */
    private void findUserInfo(List<PostCommentVo> voList, Set<Long> userIds) {
        List<SysUser> sysUsers = userMapper.selectListByCondition(SYS_USER.ID.in(userIds));
        setUserInfo(voList, sysUsers);
    }

    /**
     * 递归set用户信息
     */
    private void setUserInfo(List<PostCommentVo> voList, List<SysUser> sysUsers) {
        if (CollUtil.isNotEmpty(voList)) {
            voList.forEach(item -> {
                SysUser sysUser = sysUsers.stream().filter(e -> e.getId().equals(item.getAuthorId())).findFirst().get();
                item.setAuthorName(sysUser.getNickName());
                item.setAuthorAvatar(sysUser.getAvatar());
                setUserInfo(item.getChildren(), sysUsers);
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRecursionById(Serializable id) {
        removeById(id);
        removeChildren(id);
        return true;
    }

    private void removeChildren(Serializable parentId) {
        List<Long> childrenIds = QueryChain.of(commentMapper)
                .select(OJ_POST_COMMENT.ID)
                .where(OJ_POST_COMMENT.PARENT_ID.eq(parentId))
                .listAs(Long.class);
        for (Long childrenId : childrenIds) {
            removeById(childrenId);
            removeChildren(childrenId);
        }
    }

    @Override
    public Long getNum(String postId) {
        long count = postMapper
                .selectCountByCondition(OJ_POST.ID.eq(postId, StringUtil::isNotBlank));
        if (count == 0) {
            throw new BusinessException("帖子不存在");
        }
        return commentMapper.selectCountByCondition(OJ_POST_COMMENT.POST_ID.eq(postId, StringUtil::isNotBlank));
    }
}
