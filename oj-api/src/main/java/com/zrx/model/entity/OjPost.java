package com.zrx.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zrx.model.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 帖子 实体类。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "帖子")
@Table(value = "oj_post")
public class OjPost extends BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private String tags;

	/**
	 * 观看数
	 */
	@Column(onInsertValue = "0")
	@Schema(description = "观看数")
	private Integer viewNum;

	/**
	 * 点赞数
	 */
	@Column(onInsertValue = "0")
	@Schema(description = "点赞数")
	private Integer thumbNum;

	/**
	 * 收藏数
	 */
	@Column(onInsertValue = "0")
	@Schema(description = "收藏数")
	private Integer favourNum;

	/**
	 * 是否删除
	 */
	@Column(isLogicDelete = true)
	@Schema(description = "是否删除")
	private Integer delFlag;

}
