package com.zrx.model.dto.problem;

import com.zrx.model.common.SaveGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 题目请求体
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Data
@Schema(description = "题目请求体")
public class OjProblemRequest {

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	@NotBlank(message = "标题不能为空", groups = SaveGroup.class)
	private String title;

	/**
	 * 内容
	 */
	@Schema(description = "内容")
	@NotBlank(message = "内容不能为空", groups = SaveGroup.class)
	private String content;

	/**
	 * 标签列表
	 */
	@Schema(description = "标签列表")
	@NotNull(message = "请至少输入一个标签", groups = SaveGroup.class)
	private List<String> tags;

	/**
	 * 题目难度，0简单，1中等，2困难
	 */
	@Min(value = 0, message = "0~2")
	@Max(value = 2, message = "0~2")
	@Schema(description = "题目难度，0简单，1中等，2困难")
	@NotNull(message = "请选择题目难度", groups = SaveGroup.class)
	private Integer difficulty;

	/**
	 * 题目答案语言
	 */
	@Schema(description = "题目答案语言")
	@NotBlank(message = "题目答案语言不能为空", groups = SaveGroup.class)
	private String ansLanguage;

	/**
	 * 题目答案
	 */
	@Schema(description = "题目答案")
	@NotBlank(message = "题目答案不能为空", groups = SaveGroup.class)
	private String answer;

	/**
	 * 判题用例
	 */
	@Schema(description = "判题用例")
	@NotNull(message = "请选择判题用例", groups = SaveGroup.class)
	private List<JudgeCase> judgeCase;

	/**
	 * 判题配置
	 */
	@Schema(description = "判题配置")
	private JudgeConfig judgeConfig;

}
