package com.zrx.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 帖子 Vo。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@Data
@Schema(name = "OjPostVo", description = "帖子")
public class OjPostVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	private String content;

	/**
	 * 分区
	 */
	@Schema(description = "分区")
	private String zone;

	/**
	 * 分区名称
	 */
	@Schema(description = "分区名称")
	private String zoneName;

	/**
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

	/**
	 * 点赞数
	 */
	@Schema(description = "点赞数")
	private Integer thumbNum;

	/**
	 * 收藏数
	 */
	@Schema(description = "收藏数")
	private Integer favourNum;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 观看数
	 */
	@Schema(description = "观看数")
	private Integer viewNum;

	/**
	 * 作者Id
	 */
	@Schema(description = "作者Id")
	private Long creator;

	/**
	 * 作者
	 */
	@Schema(description = "作者")
	private String creatorName;

	/**
	 * 作者简介
	 */
	@Schema(description = "作者简介")
	private String introduce;

	/**
	 * 作者头像
	 */
	@Schema(description = "作者头像")
	private String avatar;

	/**
	 * 当前用户是否已点赞
	 */
	@Schema(description = "当前用户是否已点赞")
	private Boolean thumbFlag;

	/**
	 * 当前用户是否已收藏
	 */
	@Schema(description = "当前用户是否已收藏")
	private Boolean favourFlag;

}
