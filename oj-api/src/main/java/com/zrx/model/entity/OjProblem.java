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
 * 题目 实体类。
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "题目")
@Table(value = "oj_problem")
public class OjProblem extends BaseEntity implements Serializable {

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
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private String tags;

	/**
	 * 题目难度，0简单，1中等，2困难
	 */
	@Schema(description = "题目难度，0简单，1中等，2困难")
	private Integer difficulty;

	/**
	 * 题目答案语言
	 */
	@Schema(description = "题目答案语言")
	private String ansLanguage;

	/**
	 * 题目答案
	 */
	@Schema(description = "题目答案")
	private String answer;

	/**
	 * 题目提交数
	 */
	@Column(onInsertValue = "0")
	@Schema(description = "题目提交数")
	private Integer submitNum;

	/**
	 * 题目通过数
	 */
	@Column(onInsertValue = "0")
	@Schema(description = "题目通过数")
	private Integer acceptedNum;

	/**
	 * 判题用例（json 数组）
	 */
	@Schema(description = "判题用例（json 数组）")
	private String judgeCase;

	/**
	 * 判题配置（json 对象）
	 */
	@Schema(description = "判题配置（json 对象）")
	private String judgeConfig;

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
	 * 逻辑删除，0未删除，1删除
	 */
	@Schema(description = "逻辑删除，0未删除，1删除")
	@Column(isLogicDelete = true)
	private Integer delFlag;

}
