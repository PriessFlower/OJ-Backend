package com.zrx.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 题目评论 实体类。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "题目评论")
@Table(value = "oj_problem_comment")
public class ProblemComment implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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
	@Schema(description = "题目id")
	private Long problemId;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	private String content;

	/**
	 * 作者id
	 */
	@Schema(description = "作者id")
	private Long authorId;

	/**
	 * 创建时间
	 */
	@Column(onInsertValue = "now()")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 逻辑删除（0-未删除，1-已删除）
	 */
	@Column(isLogicDelete = true)
	@Schema(description = "逻辑删除（0-未删除，1-已删除）")
	private Integer delFlag;

}
