package com.zrx.model.dto.problemSubmit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyOjProblemSubmitQueryRequest {

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "代码执行状态（详见ProblemJudgeResultEnum）")
	private Integer codeStatus;

	@Schema(description = "题目实际输出（json）")
	private String output;

	@Schema(description = "判题信息（json 对象）")
	private String judgeInfo;

	@Schema(description = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
	private Integer status;

	@Schema(description = "题目 id")
	private Long questionId;

	@Schema(description = "创建时间")
	private Date createTime;

}
