package com.zrx.service;


import com.mybatisflex.core.service.IService;
import com.zrx.model.dto.postComment.PostCommentRequest;
import com.zrx.model.entity.OjPostComment;
import com.zrx.model.vo.PostCommentVo;

import java.io.Serializable;
import java.util.List;

/**
 * 帖子评论 服务层。
 *
 * @author zhang.rx
 * @since 2024/5/20
 */
public interface OjPostCommentService extends IService<OjPostComment> {

    Boolean removeRecursionById(Serializable id);

    Boolean save(PostCommentRequest req);

    List<PostCommentVo> listChildren(String postId);

    List<PostCommentVo> list(String postId);

    Long getNum(String postId);

}
