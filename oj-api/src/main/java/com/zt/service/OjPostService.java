package com.zt.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.model.dto.post.OjPostAddRequest;
import com.zt.model.dto.post.OjPostQueryRequest;
import com.zt.model.dto.post.OjPostUpdateRequest;
import com.zt.model.entity.OjPost;
import com.zt.model.vo.OjPostSimpleVo;
import com.zt.model.vo.OjPostVo;

import java.util.List;

/**
 * 帖子 服务层。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
public interface OjPostService extends IService<OjPost> {

	Boolean save(OjPostAddRequest req);

	Boolean removePostById(Long id);

	Boolean updateById(OjPostUpdateRequest req);

	OjPostVo getInfoById(String id);

	Page<OjPostVo> page(Paging page, OjPostQueryRequest req, Boolean selfFlag);

	List<OjPostSimpleVo> getFiveHotPost();

}
