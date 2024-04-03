package com.zrx.model.vo;

import com.zrx.model.dto.problem.JudgeCase;
import com.zrx.model.dto.problem.JudgeConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 题目响应体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Data
@Schema(description = "题目响应体")
public class OjProblemVo {

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
	 * 标签列表（json 数组）
	 */
	@Schema(description = "标签列表（json 数组）")
	private List<String> tags;

	/**
	 * 题目难度，简单，中等，困难
	 */
	@Schema(description = "题目难度，简单，中等，困难")
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
	@Schema(description = "题目提交数")
	private Integer submitNum;

	/**
	 * 题目通过数
	 */
	@Schema(description = "题目通过数")
	private Integer acceptedNum;

	/**
	 * 判题用例
	 */
	@Schema(description = "判题用例")
	private List<JudgeCase> judgeCase;

	/**
	 * 判题配置（json 对象）
	 */
	@Schema(description = "判题配置（json 对象）")
	private JudgeConfig judgeConfig;

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

}
