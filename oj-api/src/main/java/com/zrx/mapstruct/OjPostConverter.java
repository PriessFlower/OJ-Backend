package com.zrx.mapstruct;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mybatisflex.core.paginate.Page;
import com.zrx.model.dto.post.OjPostAddRequest;
import com.zrx.model.dto.post.OjPostUpdateRequest;
import com.zrx.model.entity.OjPost;
import com.zrx.model.vo.OjPostSimpleVo;
import com.zrx.model.vo.OjPostVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhang.rx
 * @since 2024/5/13
 */
@Mapper(componentModel = "Spring")
public interface OjPostConverter {

	OjPostVo toVo(OjPost ojPost);

	default List<String> toTagList(String tags) {
		return JSONUtil.toList(tags, String.class);
	}

	default String toTagString(List<String> tags) {
		return JSON.toJSONString(tags);
	}

	OjPost toEntity(OjPostAddRequest req);

	OjPost toEntityUpdate(OjPostUpdateRequest req);

	Page<OjPostVo> toVoPage(Page<OjPost> paginate);

	List<OjPostVo> toVoList(List<OjPost> ojPosts);

	List<OjPostSimpleVo> toSimpleVoList(List<OjPost> ojPosts);

}
