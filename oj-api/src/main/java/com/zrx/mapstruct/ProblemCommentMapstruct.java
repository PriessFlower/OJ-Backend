package com.zrx.mapstruct;

import com.mybatisflex.core.paginate.Page;
import com.zrx.model.dto.problemComment.ProblemCommentRequest;
import com.zrx.model.entity.ProblemComment;
import com.zrx.model.vo.ProblemCommentVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/4/26
 */
@Mapper(componentModel = "Spring")
public interface ProblemCommentMapstruct {

	ProblemComment toEntity(ProblemCommentRequest req);

	ProblemCommentVo toVo(ProblemComment entity);

	List<ProblemCommentVo> toVoList(List<ProblemComment> entity);

	Page<ProblemCommentVo> toVoPage(Page<ProblemComment> entity);

}
