package com.zrx.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitQueryRequest;
import com.zrx.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zrx.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zrx.model.entity.OjProblemSubmit;
import com.zrx.sys.model.entity.SysUser;

public interface OjProblemSubmitService extends IService<OjProblemSubmit> {

	Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) throws Exception;

	OjProblemSubmitVo getInfoById(Long id);

	Page<OjProblemSubmitVo> pageInfoByUserId(Long id, OjProblemSubmitQueryRequest req, Paging paging);

}
