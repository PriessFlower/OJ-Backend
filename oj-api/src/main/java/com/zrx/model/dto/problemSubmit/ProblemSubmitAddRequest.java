package com.zrx.model.dto.problemSubmit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProblemSubmitAddRequest", description = "提交题目请求")
public class ProblemSubmitAddRequest {

	@NotBlank(message = "请选择编程语言")
	@Schema(description = "编程语言")
	private String language;

	@NotBlank(message = "请输入代码")
	@Schema(description = "用户代码")
	private String code;

	@NotNull(message = "题目 id不能为空")
	@Schema(description = "题目 id")
	private Long questionId;

}
