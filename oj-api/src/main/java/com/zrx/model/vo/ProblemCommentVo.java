package com.zrx.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 题目评论 实体类。
 *
 * @author zhang.rx
 * @since 2024/4/18
 */
@Data
@Schema(description = "题目评论")
public class ProblemCommentVo implements Serializable {

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
	 * 用户头像
	 */
	@Schema(description = "用户头像")
	private String authorAvatar;

	/**
	 * 用户昵称
	 */
	@Schema(description = "用户昵称")
	private String authorName;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 子评论
	 */
	@Schema(description = "子评论")
	private List<ProblemCommentVo> children;

	/**
	 * 是否展开
	 */
	@Schema(description = "是否展开")
	private Boolean expandedFlag;

}
