package com.zrx.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.enums.ProblemSubmitStatusEnum;
import com.zrx.mapper.OjProblemSubmitMapper;
import com.zrx.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.entity.OjProblemSubmit;
import com.zrx.queue.OjMessageProducer;
import com.zrx.service.OjProblemService;
import com.zrx.service.OjProblemSubmitService;
import com.zrx.sys.model.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OjProblemSubmitServiceImpl extends ServiceImpl<OjProblemSubmitMapper, OjProblemSubmit>
		implements OjProblemSubmitService {

	@Autowired
	private OjProblemService ojProblemService;

	@Autowired
	private OjMessageProducer ojMessageProducer;

	@Override
	public Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) throws Exception {
		OjProblemSubmit ojProblemSubmit = new OjProblemSubmit();
		ojProblemSubmit.setStatus(ProblemSubmitStatusEnum.Submitting.getKey());
		ojProblemSubmit.setLanguage(problemSubmitAddRequest.getLanguage());
		ojProblemSubmit.setCode(problemSubmitAddRequest.getCode());

		Long questionId = ojProblemSubmit.getQuestionId();

		OjProblem problem = ojProblemService.getById(questionId);

		if (problem == null) {
			throw new Exception("题目不存在");
		}
		ojProblemSubmit.setQuestionId(problemSubmitAddRequest.getQuestionId());
		// TODO: 可能存在问题
		if (loginUser == null) {
			throw new Exception("你还未登录");
		}
		ojProblemSubmit.setUserId(loginUser.getId());
		this.save(ojProblemSubmit);
		Long ojProblemSubmitId = ojProblemSubmit.getId();
		if (problem.getSubmitNum() == null) {
			problem.setSubmitNum(1);
		}
		else {
			problem.setSubmitNum(problem.getSubmitNum() + 1);
		}
		boolean update = ojProblemService.updateById(problem);
		// 消息队列
		ojMessageProducer.sendMessage("luojialong", "shenming", String.valueOf(ojProblemSubmitId));
		return ojProblemSubmitId;
	}

}
