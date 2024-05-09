package com.zrx.model.dto.problemSubmit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProblemSubmitAddRequest", description = "提交题目请求")
public class ProblemSubmitAddRequest {

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "题目 id")
	private Long questionId;

}
