package com.zt.mapstruct;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zt.codesandbox.model.JudgeInfo;
import com.zt.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zt.model.entity.OjProblemSubmit;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/5/8
 */
@Mapper(componentModel = "Spring")
public interface ProblemSubmitConverter {

	OjProblemSubmitVo toVo(OjProblemSubmit entity);

	default JudgeInfo judgeInfoStrToJudgeInfoObj(String judgeInfoStr) {
		return JSONUtil.toBean(judgeInfoStr, JudgeInfo.class);
	}

	List<OjProblemSubmitVo> toVoList(List<OjProblemSubmit> list);

	Page<OjProblemSubmitVo> toVoPage(Page<OjProblemSubmit> paginate);

}
