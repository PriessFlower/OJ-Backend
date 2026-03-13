package com.zt.mapstruct;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.zt.enums.ProblemDifficultyEnum;
import com.zt.model.dto.problem.JudgeCase;
import com.zt.model.dto.problem.JudgeConfig;
import com.zt.model.dto.problem.OjProblemRequest;
import com.zt.model.dto.problem.OjProblemUpdateRequest;
import com.zt.model.entity.OjProblem;
import com.zt.model.vo.OjProblemPageVo;
import com.zt.model.vo.OjProblemVo;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

/**
 * 题目 Mapstruct
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
@Mapper(componentModel = "Spring")
public interface OjProblemConverter {

	static OjProblem addDto2Entity(OjProblemRequest req) {
		String title = req.getTitle();
		String content = req.getContent();
		List<String> tags = req.getTags();
		Integer difficulty = req.getDifficulty();
		String answer = req.getAnswer();
		List<JudgeCase> judgeCase = req.getJudgeCase();
		JudgeConfig judgeConfig = req.getJudgeConfig();

		OjProblem ojProblem = new OjProblem();
		ojProblem.setTitle(title);
		ojProblem.setContent(content);
		if (CollUtil.isNotEmpty(tags)) {
			ojProblem.setTags(JSON.toJSONString(tags));
		}
		ojProblem.setDifficulty(difficulty);
		ojProblem.setAnswer(answer);
		if (CollUtil.isNotEmpty(judgeCase)) {
			ojProblem.setJudgeCase(JSON.toJSONString(judgeCase));
		}
		if (judgeCase != null) {
			ojProblem.setJudgeConfig(JSON.toJSONString(judgeConfig));
		}

		return ojProblem;
	}

	static OjProblem updateDto2Entity(OjProblemUpdateRequest req) {
		Long id = req.getId();
		OjProblem ojProblem = addDto2Entity(req);
		ojProblem.setId(id);
		return ojProblem;
	}

	static OjProblemPageVo entity2VoPage(OjProblem ojProblem) {
		Long id = ojProblem.getId();
		String title = ojProblem.getTitle();
		String content = ojProblem.getContent();
		String tags = ojProblem.getTags();
		Integer difficulty = ojProblem.getDifficulty();
		Integer submitNum = ojProblem.getSubmitNum();
		Integer acceptedNum = ojProblem.getAcceptedNum();
		Integer thumbNum = ojProblem.getThumbNum();
		Integer favourNum = ojProblem.getFavourNum();

		OjProblemPageVo pageVo = new OjProblemPageVo();
		pageVo.setId(id);
		pageVo.setTitle(title);
		pageVo.setContent(content);
        if (StrUtil.isNotBlank(tags) && JSONUtil.isTypeJSONArray(tags)) {
            pageVo.setTags(JSONUtil.toList(tags, String.class));
        } else {
            pageVo.setTags(Collections.emptyList());
        }
        ProblemDifficultyEnum anEnum = ProblemDifficultyEnum.getEnum(difficulty);
		if (anEnum != null) {
			pageVo.setDifficulty(anEnum.getName());
		}
		pageVo.setSubmitNum(submitNum);
		pageVo.setAcceptedNum(acceptedNum);
		pageVo.setThumbNum(thumbNum);
		pageVo.setFavourNum(favourNum);
		return pageVo;
	}

	static OjProblemVo entity2Vo(OjProblem ojProblem) {
		Long id = ojProblem.getId();
		String title = ojProblem.getTitle();
		String content = ojProblem.getContent();
		String tags = ojProblem.getTags();
		Integer difficulty = ojProblem.getDifficulty();
		String ansLanguage = ojProblem.getAnsLanguage();
		String answer = ojProblem.getAnswer();
		Integer submitNum = ojProblem.getSubmitNum();
		Integer acceptedNum = ojProblem.getAcceptedNum();
		String judgeCase = ojProblem.getJudgeCase();
		String judgeConfig = ojProblem.getJudgeConfig();
		Integer thumbNum = ojProblem.getThumbNum();
		Integer favourNum = ojProblem.getFavourNum();

		OjProblemVo vo = new OjProblemVo();
		vo.setId(id);
		vo.setTitle(title);
		vo.setContent(content);
        if (StrUtil.isNotBlank(tags) && JSONUtil.isTypeJSONArray(tags)) {
            vo.setTags(JSONUtil.toList(tags, String.class));
        } else {
            vo.setTags(Collections.emptyList());
        }
        vo.setDifficulty(difficulty);
		vo.setSubmitNum(submitNum);
		vo.setAcceptedNum(acceptedNum);
		vo.setAnsLanguage(ansLanguage);
		vo.setAnswer(answer);
        if (StrUtil.isNotBlank(judgeCase) && JSONUtil.isTypeJSONArray(judgeCase)) {
            vo.setJudgeCase(JSONUtil.toList(judgeCase, JudgeCase.class));
        } else {
            vo.setJudgeCase(Collections.emptyList());
        }
        // 防御性判断：judgeConfig 为 null 或空字符串时，返回 null 而非抛出异常
        if (StrUtil.isNotBlank(judgeConfig) && JSONUtil.isTypeJSONObject(judgeConfig)) {
            vo.setJudgeConfig(JSONUtil.toBean(judgeConfig, JudgeConfig.class));
        } else {
            vo.setJudgeConfig(null);
        }
        vo.setThumbNum(thumbNum);
		vo.setFavourNum(favourNum);
		return vo;
	}

}
