package com.zrx.mapstruct;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zrx.codesandbox.model.JudgeInfo;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zrx.model.entity.OjProblemSubmit;
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
