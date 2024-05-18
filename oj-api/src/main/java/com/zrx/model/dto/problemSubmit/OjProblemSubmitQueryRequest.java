package com.zrx.model.dto.problemSubmit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OjProblemSubmitQueryRequest {

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "代码执行状态（详见ProblemJudgeResultEnum）")
	private Integer codeStatus;

	@Schema(description = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
	private Integer status;

	@Schema(description = "题目 id")
	private Long questionId;

}
