package com.zrx.sys.mapstruct;

import com.zrx.sys.model.dto.SysMenuRequest;
import com.zrx.sys.model.entity.SysMenu;
import org.mapstruct.Mapper;

/**
 * 系统菜单 mapstruct
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
@Mapper(componentModel = "Spring")
public interface SysMenuMap {
    SysMenu dto2Entity(SysMenuRequest request);
}
