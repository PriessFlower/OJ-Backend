package com.zt.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.model.dto.problem.OjProblemAddRequest;
import com.zt.model.dto.problem.OjProblemQueryRequest;
import com.zt.model.dto.problem.OjProblemUpdateRequest;
import com.zt.model.entity.OjProblem;
import com.zt.model.vo.OjProblemPageVo;
import com.zt.model.vo.OjProblemVo;

import java.io.Serializable;

/**
 * 题目 服务层。
 *
 * @author zhang.rx
 * @since 2024/3/20
 */
public interface OjProblemService extends IService<OjProblem> {

	Boolean save(OjProblemAddRequest req);

	Boolean updateById(OjProblemUpdateRequest req);

	OjProblemVo getInfoById(Serializable id);

	Page<OjProblemPageVo> page(Paging page, OjProblemQueryRequest req);

}
