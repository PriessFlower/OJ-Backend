package com.zt.judge;

import cn.hutool.json.JSONUtil;
import com.zt.codesandbox.enums.QuestionExecuteResultEnum;
import com.zt.codesandbox.model.ExecuteCodeResponse;
import com.zt.codesandbox.model.JudgeInfo;
import com.zt.enums.ProblemJudgeResultEnum;
import com.zt.model.dto.problem.JudgeCase;
import com.zt.model.dto.problem.JudgeConfig;
import com.zt.model.entity.OjProblem;
import com.zt.model.entity.OjProblemSubmit;
import com.zt.model.judge.JudgeTask;

import java.util.List;
import java.util.stream.Collectors;

public class HandleJudgeTask {

	public OjProblemSubmit doHandle(JudgeTask judgeTask) {
		ExecuteCodeResponse executeCodeResponse = judgeTask.getExecuteCodeResponse();
		JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();

		OjProblem ojProblem = judgeTask.getOjProblem();

		OjProblemSubmit ojProblemSubmit = judgeTask.getOjProblemSubmit();

		Integer status = executeCodeResponse.getStatus();

		QuestionExecuteResultEnum enumByValue = QuestionExecuteResultEnum.getEnumByValue(status);

		if (enumByValue != QuestionExecuteResultEnum.ExecuteSuccess) {
			JudgeInfo judgeInfoRes = new JudgeInfo();
			judgeInfoRes.setMessage(executeCodeResponse.getMessage());
			ojProblemSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoRes));
			if (enumByValue == QuestionExecuteResultEnum.CompileFailure) {
				ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.COMPILE_ERROR.getKey());
				return ojProblemSubmit;
			}
			if (enumByValue == QuestionExecuteResultEnum.OutOfTimeException) {
				ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.TIME_LIMIT_EXCEEDED.getKey());
				return ojProblemSubmit;
			}
			if (enumByValue == QuestionExecuteResultEnum.RunException) {
				ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.RUNTIME_ERROR.getKey());
				return ojProblemSubmit;
			}
		}

		String judgeConfigStr = ojProblem.getJudgeConfig();
		JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
		if (judgeInfo.getTime() == null || judgeInfo.getMemory() == null) {
			ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.SYSTEM_ERROR.getKey());
			return ojProblemSubmit;
		}
		if (judgeInfo.getTime() > judgeConfig.getTimeLimit()) {
			ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.TIME_LIMIT_EXCEEDED.getKey());
			return ojProblemSubmit;
		}
		if (judgeInfo.getMemory() > judgeConfig.getMemoryLimit() * 1024L) {
			ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.MEMORY_LIMIT_EXCEEDED.getKey());
			return ojProblemSubmit;
		}
		List<String> outputList = executeCodeResponse.getOuputList();
		List<JudgeCase> judgeCaseList = JSONUtil.toList(ojProblem.getJudgeCase(), JudgeCase.class);
		List<String> expertOutputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());

		if (outputList.size() != expertOutputList.size()) {
			ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.WRONG_ANSWER.getKey());
			return ojProblemSubmit;
		}
		for (int i = 0, len = outputList.size(); i < len; i++) {
			if (!outputList.get(i).equals(expertOutputList.get(i))) {
				ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.WRONG_ANSWER.getKey());
				return ojProblemSubmit;
			}
		}
		ojProblemSubmit.setCodeStatus(ProblemJudgeResultEnum.ACCEPTED.getKey());
		return ojProblemSubmit;
	}

}
