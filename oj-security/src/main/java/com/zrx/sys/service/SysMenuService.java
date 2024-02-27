package com.zrx.sys.service;


import com.mybatisflex.core.service.IService;
import com.zrx.sys.model.entity.SysMenu;

import java.util.List;

/**
 *  系统菜单管理服务层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getAllMenuList(Integer menuType);
}
