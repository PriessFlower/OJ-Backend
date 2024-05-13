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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zrx.model.entity.table.OjProblemSubmitTableDef.OJ_PROBLEM_SUBMIT;

@Service
public class OjProblemSubmitServiceImpl extends ServiceImpl<OjProblemSubmitMapper, OjProblemSubmit>
		implements OjProblemSubmitService {

	private static final Logger log = LoggerFactory.getLogger(OjProblemSubmitServiceImpl.class);
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
	public Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) {
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
		mapper.insert(ojProblemSubmit);
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
//		CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(ojProblemSubmitId);
            } catch (Exception e) {
				log.error("判题失败", e);
//                throw new RuntimeException(e);
            }
//        });

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

	@Override
	public List<OjProblemSubmitVo> getInfoListByUserId(Long id) {
		List<OjProblemSubmit> list = mapper.selectListByCondition(OJ_PROBLEM_SUBMIT.USER_ID.eq(id));
		if (list.isEmpty()) {
			return List.of();
		}
		return problemSubmitConverter.toVoList(list);
	}

}
