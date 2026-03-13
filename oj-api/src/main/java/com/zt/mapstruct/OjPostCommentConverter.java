package com.zt.mapstruct;

import com.zt.model.dto.postComment.PostCommentRequest;
import com.zt.model.entity.OjPostComment;
import com.zt.model.vo.PostCommentVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/5/20
 */
@Mapper(componentModel = "Spring")
public interface OjPostCommentConverter {

	OjPostComment toEntity(PostCommentRequest req);

	PostCommentVo toVo(OjPostComment comment);

	List<PostCommentVo> toVoList(List<OjPostComment> list);

}
