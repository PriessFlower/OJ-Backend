package com.zt.service.impl;

import com.google.common.collect.Lists;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zt.enums.ProblemJudgeResultEnum;
import com.zt.enums.ProblemSubmitStatusEnum;
import com.zt.exception.BusinessException;
import com.zt.execudeCode.JudgeService;
import com.zt.mapper.OjProblemMapper;
import com.zt.mapper.OjProblemSubmitMapper;
import com.zt.mapstruct.ProblemSubmitConverter;
import com.zt.model.common.Paging;
import com.zt.model.dto.problemSubmit.OjProblemSubmitQueryRequest;
import com.zt.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zt.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zt.model.entity.OjProblemSubmit;
import com.zt.service.OjProblemSubmitService;
import com.zt.sys.model.entity.SysUser;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.zt.model.entity.table.OjProblemSubmitTableDef.OJ_PROBLEM_SUBMIT;

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

	// @Autowired
	// private OjMessageProducer ojMessageProducer;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) {
		OjProblemSubmit ojProblemSubmit = new OjProblemSubmit();
		ojProblemSubmit.setStatus(ProblemSubmitStatusEnum.Submitting.getKey());
		ojProblemSubmit.setLanguage(problemSubmitAddRequest.getLanguage());
		ojProblemSubmit.setCode(problemSubmitAddRequest.getCode());

		Long questionId = problemSubmitAddRequest.getQuestionId();

		if (problemMapper.selectOneById(questionId) == null) {
			throw new BusinessException("题目不存在");
		}
		ojProblemSubmit.setQuestionId(questionId);
		ojProblemSubmit.setUserId(loginUser.getId());
		mapper.insert(ojProblemSubmit);
		Long ojProblemSubmitId = ojProblemSubmit.getId();
		problemMapper.incrementSubmitNum(questionId);
		// 消息队列
		// ojMessageProducer.sendMessage("luojialong", "shenming",
		// String.valueOf(ojProblemSubmitId));
		// CompletableFuture.runAsync(() -> {
//		try {
//			judgeService.doJudge(ojProblemSubmitId);
//		}
//		catch (Exception e) {
//			log.error("判题失败", e);
//			// throw new RuntimeException(e);
//		}
//		// });
        // 改用异步方式，我现在知道为什么它不用异步方式了，因为用了异步方式 + 事务，后续第一时间查不一定查得到。
        CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(ojProblemSubmitId);
            }
            catch (Exception e) {
                log.error("判题失败", e);
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
		OjProblemSubmitVo vo = problemSubmitConverter.toVo(ojProblemSubmit);
		List<OjProblemSubmitVo> voList = Lists.newArrayList(vo);
		setCodeStatus(voList);
		setStatus(voList);
		return voList.get(0);
	}

	@Override
	public Page<OjProblemSubmitVo> pageInfoByUserId(Long id, OjProblemSubmitQueryRequest req, Paging paging) {
		String language = req.getLanguage();
		String code = req.getCode();
		Integer codeStatus = req.getCodeStatus();
		Integer status = req.getStatus();
		Long questionId = req.getQuestionId();

		QueryWrapper queryWrapper = new QueryWrapper()
			.select(OJ_PROBLEM_SUBMIT.ID, OJ_PROBLEM_SUBMIT.LANGUAGE, OJ_PROBLEM_SUBMIT.CODE_STATUS,
					OJ_PROBLEM_SUBMIT.STATUS, OJ_PROBLEM_SUBMIT.CREATE_TIME, OJ_PROBLEM_SUBMIT.JUDGE_INFO)
			.where(OJ_PROBLEM_SUBMIT.USER_ID.eq(id))
			.and(OJ_PROBLEM_SUBMIT.LANGUAGE.eq(language, StringUtil::isNotBlank))
			.and(OJ_PROBLEM_SUBMIT.CODE.eq(code, StringUtil::isNotBlank))
			.and(OJ_PROBLEM_SUBMIT.CODE_STATUS.eq(codeStatus, codeStatus != null))
			.and(OJ_PROBLEM_SUBMIT.STATUS.eq(status, status != null))
			.and(OJ_PROBLEM_SUBMIT.QUESTION_ID.eq(questionId, questionId != null))
			.orderBy(OJ_PROBLEM_SUBMIT.CREATE_TIME.desc());
		Page<OjProblemSubmit> paginate = mapper.paginate(Page.of(paging.getPageNum(), paging.getPageSize()),
				queryWrapper);
		Page<OjProblemSubmitVo> voPage = problemSubmitConverter.toVoPage(paginate);
		setCodeStatus(voPage.getRecords());
		setStatus(voPage.getRecords());
		return voPage;
	}

	/**
	 * set 代码执行状态
	 */
    private void setCodeStatus(List<OjProblemSubmitVo> voList) {
        voList.forEach(item -> {
            // 增加非空判断
            if (item.getCodeStatus() != null && !item.getCodeStatus().isEmpty()) {
                ProblemJudgeResultEnum anEnum = ProblemJudgeResultEnum.getEnum(Integer.valueOf(item.getCodeStatus()));
                if (anEnum != null) {
                    item.setCodeStatus(anEnum.getValue());
                }
            }
        });
    }

    private void setStatus(List<OjProblemSubmitVo> voList) {
        voList.forEach(item -> {
            // 同样为 status 增加非空判断以防万一
            if (item.getStatus() != null && !String.valueOf(item.getStatus()).isEmpty()) {
                ProblemSubmitStatusEnum anEnum = ProblemSubmitStatusEnum.getEnum(Integer.valueOf(item.getStatus()));
                if (anEnum != null) {
                    item.setStatus(anEnum.getValue());
                }
            }
        });
    }

}
