package com.zrx.model.dto.problemComment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 题目评论 实体类。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@Data
@Schema(description = "题目评论")
public class ProblemCommentRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private String tags;

	/**
	 * 父id
	 */
	@Schema(description = "父id")
	private Long parentId;

	/**
	 * 题目id
	 */
	@NotNull(message = "题目id不能为空")
	@Schema(description = "题目id")
	private Long problemId;

	/**
	 * 内容
	 */
	@NotBlank(message = "内容不能为空")
	@Schema(description = "内容")
	private String content;

}
