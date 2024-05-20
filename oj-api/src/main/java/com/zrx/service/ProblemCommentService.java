package com.zrx.service;

import com.mybatisflex.core.service.IService;
import com.zrx.model.dto.problemComment.ProblemCommentRequest;
import com.zrx.model.entity.ProblemComment;
import com.zrx.model.vo.ProblemCommentVo;

import java.io.Serializable;
import java.util.List;

/**
 * 题目评论 服务层。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
public interface ProblemCommentService extends IService<ProblemComment> {

	Boolean save(ProblemCommentRequest req);

	List<ProblemCommentVo> list(Long problemId);

	List<ProblemCommentVo> listChildren(Long parentId);

	Boolean removeRecursionById(Serializable id);

}
