package com.zrx.sys.service.impl;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.constant.Constant;
import com.zrx.sys.mapper.SysMenuMapper;
import com.zrx.sys.model.entity.SysMenu;
import com.zrx.sys.service.SysMenuService;
import com.zrx.utils.TreeUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zrx.sys.model.entity.table.SysMenuTableDef.SYS_MENU;

/**
 * 系统菜单管理服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	@Override
	public List<SysMenu> getAllMenuList(Integer menuType) {
		List<SysMenu> sysMenus = QueryChain.of(mapper)
			.select(SYS_MENU.ALL_COLUMNS)
			.where(SYS_MENU.MENU_TYPE.eq(menuType))
			.orderBy(SYS_MENU.SORT, true)
			.list();
		return TreeUtils.build(sysMenus, Constant.MENU_ROOT);
	}

}
