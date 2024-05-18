package com.zrx.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zrx.model.common.Paging;
import com.zrx.model.entity.OjPostFavour;
import com.zrx.model.vo.OjPostVo;

/**
 * 帖子收藏 服务层。
 *
 * @author zhang.rx
 * @since 2024/5/13
 */
public interface OjPostFavourService extends IService<OjPostFavour> {

	Boolean favourPost(Long id);

	Boolean cancelFavourPost(Long id);

	Page<OjPostVo> page(Paging page, String userId);

}
