package com.zt.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.model.entity.OjPostThumb;
import com.zt.model.vo.OjPostVo;

/**
 * 帖子点赞 服务层。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
public interface OjPostThumbService extends IService<OjPostThumb> {

	Boolean thumbPost(Long id);

	Boolean cancelThumbPost(Long id);

	Page<OjPostVo> page(Paging page, String userId);

}
