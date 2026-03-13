package com.zt.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.model.dto.problemSubmit.OjProblemSubmitQueryRequest;
import com.zt.model.dto.problemSubmit.OjProblemSubmitVo;
import com.zt.model.dto.problemSubmit.ProblemSubmitAddRequest;
import com.zt.model.entity.OjProblemSubmit;
import com.zt.sys.model.entity.SysUser;

public interface OjProblemSubmitService extends IService<OjProblemSubmit> {

	Long doSubmit(ProblemSubmitAddRequest problemSubmitAddRequest, SysUser loginUser) throws Exception;

	OjProblemSubmitVo getInfoById(Long id);

	Page<OjProblemSubmitVo> pageInfoByUserId(Long id, OjProblemSubmitQueryRequest req, Paging paging);

}
