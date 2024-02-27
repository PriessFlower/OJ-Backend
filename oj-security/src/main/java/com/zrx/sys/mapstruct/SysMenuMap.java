package com.zrx.sys.mapstruct;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zrx.sys.model.dto.RouteMeta;
import com.zrx.sys.model.dto.SysMenuRequest;
import com.zrx.sys.model.entity.SysMenu;
import com.zrx.sys.model.vo.SysMenuResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 系统菜单 mapstruct
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
@Mapper(componentModel = "Spring")
public interface SysMenuMap {

	SysMenu dto2Entity(SysMenuRequest request);

	List<SysMenuResponse> entity2Dto(List<SysMenu> allMenuList);

	default String routeMeta2String(RouteMeta meta) {
		return meta == null ? null : JSON.toJSONString(meta);
	}

	default JSONObject string2JsonObject(String meta) {
		return meta == null ? null : JSONObject.parseObject(meta);
	}
}
