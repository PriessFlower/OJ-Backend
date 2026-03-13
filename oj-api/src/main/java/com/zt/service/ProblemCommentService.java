package com.zt.service;

import com.mybatisflex.core.service.IService;
import com.zt.model.dto.problemComment.ProblemCommentRequest;
import com.zt.model.entity.ProblemComment;
import com.zt.model.vo.ProblemCommentVo;

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
