package com.zrx.model.dto.problemSubmit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrx.codesandbox.model.JudgeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "题目")
public class OjProblemSubmitVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private String id;

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "代码执行状态（详见ProblemJudgeResultEnum）")
	private String codeStatus;

	@Schema(description = "题目实际输出（json）")
	private String output;

	@Schema(description = "判题信息（json 对象）")
	private JudgeInfo judgeInfo;

	@Schema(description = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
	private String status;

	@Schema(description = "题目 id")
	private String questionId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "创建时间")
	private Date createTime;

}
