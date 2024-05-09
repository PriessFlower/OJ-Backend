package com.zrx.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.enums.ProblemSubmitStatusEnum;
import com.zrx.exception.BusinessException;
import com.zrx.execudeCode.JudgeService;
import com.zrx.mapper.OjProblemMapper;
import com.zrx.mapper.OjProblemSubmitMapper;
import com.zrx.mapstruct.ProblemSubmitConverter;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zrx.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.entity.OjProblemSubmit;
import com.zrx.service.OjProblemSubmitService;
import com.zrx.sys.model.entity.SysUser;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
public class OjProblemSubmitServiceImpl extends ServiceImpl<OjProblemSubmitMapper, OjProblemSubmit>
		implements OjProblemSubmitService {

	@Resource
	private OjProblemMapper problemMapper;

	@Resource
	@Lazy
	private JudgeService judgeService;

	@Resource
	private ProblemSubmitConverter problemSubmitConverter;

//	@Autowired
//	private OjMessageProducer ojMessageProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) throws Exception {
		OjProblemSubmit ojProblemSubmit = new OjProblemSubmit();
		ojProblemSubmit.setStatus(ProblemSubmitStatusEnum.Submitting.getKey());
		ojProblemSubmit.setLanguage(problemSubmitAddRequest.getLanguage());
		ojProblemSubmit.setCode(problemSubmitAddRequest.getCode());

		Long questionId = problemSubmitAddRequest.getQuestionId();

		OjProblem problem = problemMapper.selectOneById(questionId);

		if (problem == null) {
			throw new BusinessException("题目不存在");
		}
		ojProblemSubmit.setQuestionId(problemSubmitAddRequest.getQuestionId());
		ojProblemSubmit.setUserId(loginUser.getId());
		this.save(ojProblemSubmit);
		Long ojProblemSubmitId = ojProblemSubmit.getId();
		if (problem.getSubmitNum() == null) {
			problem.setSubmitNum(1);
		}
		else {
			problem.setSubmitNum(problem.getSubmitNum() + 1);
		}
		problemMapper.update(problem);
		// 消息队列
//		ojMessageProducer.sendMessage("luojialong", "shenming", String.valueOf(ojProblemSubmitId));
		CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(ojProblemSubmitId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

		return ojProblemSubmitId;
	}

	@Override
	public OjProblemSubmitVo getInfoById(Long id) {
		OjProblemSubmit ojProblemSubmit = mapper.selectOneById(id);
		if (ojProblemSubmit == null) {
			throw new BusinessException("未找到该数据");
		}
		return problemSubmitConverter.toVo(ojProblemSubmit);
	}

}
