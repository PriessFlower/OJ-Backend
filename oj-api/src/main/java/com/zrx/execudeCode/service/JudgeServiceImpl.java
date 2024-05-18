package com.zrx.execudeCode.service;

import cn.hutool.json.JSONUtil;
import com.zrx.codesandbox.CodeSandBox;
import com.zrx.codesandbox.model.ExecuteCodeRequest;
import com.zrx.codesandbox.model.ExecuteCodeResponse;
import com.zrx.enums.ProblemJudgeResultEnum;
import com.zrx.enums.ProblemSubmitStatusEnum;
import com.zrx.execudeCode.CodeSandboxManager;
import com.zrx.execudeCode.JudgeService;
import com.zrx.judge.HandleJudgeTask;
import com.zrx.mapper.OjProblemSubmitMapper;
import com.zrx.model.dto.problem.JudgeCase;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.entity.OjProblemSubmit;
import com.zrx.model.judge.JudgeTask;
import com.zrx.service.OjProblemService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

	@Resource
	private OjProblemService ojProblemService;

	@Resource
	private OjProblemSubmitMapper submitMapper;

	@Value("${codesandbox.type:remote}")
	private String type;

	@Override
	public void doJudge(Long ojProblemSubmitId) throws Exception {

		OjProblemSubmit ojProblemSubmit = submitMapper.selectOneById(ojProblemSubmitId);
		OjProblem ojProblem = ojProblemService.getById(ojProblemSubmit.getQuestionId());

		ExecuteCodeRequest executeCodeRequest = getExecuteCodeRequest(ojProblemSubmit, ojProblem);
		CodeSandBox codeSandbox = CodeSandboxManager.instance(type);
		ojProblemSubmit.setStatus(ProblemSubmitStatusEnum.Waiting.getKey());
		submitMapper.update(ojProblemSubmit);
		ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
		JudgeTask judgeTask = setJudgeTask(executeCodeResponse, ojProblem, ojProblemSubmit);
		ojProblemSubmit.setStatus(ProblemSubmitStatusEnum.Judging.getKey());
		submitMapper.update(ojProblemSubmit);
		HandleJudgeTask handleJudgeTask = new HandleJudgeTask();
		ojProblemSubmit.setOutput(JSONUtil.toJsonStr(executeCodeResponse.getOuputList()));
		ojProblemSubmit.setJudgeInfo(JSONUtil.toJsonStr(executeCodeResponse.getJudgeInfo()));
		ojProblemSubmit = handleJudgeTask.doHandle(judgeTask);
		boolean saveFlag = submitMapper.update(ojProblemSubmit) == 1;
		Integer codeStatus = ojProblemSubmit.getCodeStatus();
		if (codeStatus.equals(ProblemJudgeResultEnum.ACCEPTED.getKey())) {
			ojProblem.setAcceptedNum(ojProblem.getAcceptedNum() == null ? 1 : ojProblem.getAcceptedNum() + 1);
			ojProblemService.updateById(ojProblem);
		}
		if (!saveFlag) {
			throw new Exception("代码提交保存失败");
		}
	}

	private ExecuteCodeRequest getExecuteCodeRequest(OjProblemSubmit ojProblemSubmit, OjProblem ojProblem) {

		ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
		executeCodeRequest.setCode(ojProblemSubmit.getCode());
		executeCodeRequest.setLanguage(ojProblemSubmit.getLanguage());
		List<JudgeCase> judgeCaseList = JSONUtil.toList(ojProblem.getJudgeCase(), JudgeCase.class);
		List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
		executeCodeRequest.setInputList(inputList);
		return executeCodeRequest;
	}

	private JudgeTask setJudgeTask(ExecuteCodeResponse executeCodeResponse, OjProblem ojProblem,
			OjProblemSubmit ojProblemSubmit) {
		JudgeTask judgeTask = new JudgeTask();
		judgeTask.setOjProblem(ojProblem);
		judgeTask.setOjProblemSubmit(ojProblemSubmit);
		judgeTask.setExecuteCodeResponse(executeCodeResponse);
		return judgeTask;
	}

}
