package com.zrx.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zrx.model.common.Paging;
import com.zrx.model.dto.problem.OjProblemAddRequest;
import com.zrx.model.dto.problem.OjProblemQueryRequest;
import com.zrx.model.dto.problem.OjProblemUpdateRequest;
import com.zrx.model.entity.OjProblem;
import com.zrx.model.vo.OjProblemPageVo;
import com.zrx.model.vo.OjProblemVo;

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
