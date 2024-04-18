package com.zrx.sys.service;

import com.mybatisflex.core.service.IService;
import com.zrx.sys.model.entity.SysRolePermission;

import java.util.List;

/**
 * 角色权限关系 服务层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

	List<String> getPermissionCodeByRoleId(String roleId);

}
