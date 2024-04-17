package com.zrx.model.vo;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zrx.codesandbox.model.JudgeInfo;
import com.zrx.enums.ProblemSubmitStatusEnum;
import com.zrx.model.entity.OjProblemSubmit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class OjProblemSubmitPageVo {

	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private Long id;

	@Schema(description = "编程语言")
	private String language;

	@Schema(description = "用户代码")
	private String code;

	@Schema(description = "代码执行状态（详见ProblemJudgeResultEnum）")
	private String codeStatus;

	@Schema(description = "题目实际输出（json）")
	private List<String> outputList;

	@Schema(description = "判题信息（json 对象）")
	private JudgeInfo judgeInfo;

	@Schema(description = "判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）")
	private String status;

	@Schema(description = "题目 id")
	private Long questionId;

	@Schema(description = "创建用户id")
	private Long userId;

	@Schema(description = "创建时间")
	private Date createTime;

	/**
	 * 包装类转对象
	 * @param ojProblemSubmitPageVo
	 * @return
	 */
	public static OjProblemSubmit voToObj(OjProblemSubmitPageVo ojProblemSubmitPageVo) {
		if (ojProblemSubmitPageVo == null) {
			return null;
		}
		OjProblemSubmit ojProblemSubmit = new OjProblemSubmit();
		BeanUtils.copyProperties(ojProblemSubmitPageVo, ojProblemSubmit);
		JudgeInfo judgeInfoObj = ojProblemSubmitPageVo.getJudgeInfo();
		if (judgeInfoObj != null) {
			ojProblemSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
		}
		return ojProblemSubmit;
	}

	/**
	 * 对象转包装类
	 * @param problemSubmit
	 * @return
	 */
	public static OjProblemSubmitPageVo objToVo(OjProblemSubmit problemSubmit) {
		if (problemSubmit == null) {
			return null;
		}
		OjProblemSubmitPageVo problemSubmitPageVo = new OjProblemSubmitPageVo();
		BeanUtils.copyProperties(problemSubmit, problemSubmitPageVo);
		problemSubmitPageVo.setStatus(ProblemSubmitStatusEnum.getEnum(problemSubmit.getStatus()).getValue());
		String judgeInfoStr = problemSubmit.getJudgeInfo();
		problemSubmitPageVo.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
		problemSubmitPageVo.setCodeStatus(ProblemSubmitStatusEnum.getEnum(problemSubmit.getCodeStatus()).getValue());
		problemSubmitPageVo.setOutputList(JSONUtil.toList(problemSubmit.getOutput(), String.class));
		return problemSubmitPageVo;
	}

}
