package com.zrx.sys.service.impl;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.sys.mapper.SysRolePermissionMapper;
import com.zrx.sys.model.entity.SysRolePermission;
import com.zrx.sys.service.SysRolePermissionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zrx.sys.model.entity.table.SysRolePermissionTableDef.SYS_ROLE_PERMISSION;

/**
 * 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
		implements SysRolePermissionService {

	@Override
	@Cacheable(value = "api_pcode_list", key = "#roleId")
	public List<String> getPermissionCodeByRoleId(String roleId) {
		return QueryChain.of(mapper)
			.select(SYS_ROLE_PERMISSION.PERMISSION_CODE)
			.where(SYS_ROLE_PERMISSION.ROLE_ID.in(roleId))
			.listAs(String.class);
	}

}
