package com.zt.sys.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.zt.model.common.Paging;
import com.zt.sys.model.dto.SysRoleRequest;
import com.zt.sys.model.dto.UpdateUserRoleRequest;
import com.zt.sys.model.entity.SysRole;
import com.zt.sys.model.vo.SysRoleResponse;

import java.io.Serializable;
import java.util.List;

/**
 * 服务层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public interface SysRoleService extends IService<SysRole> {

	List<String> getRoleIdListByUserId(String userId);

	List<SysRoleResponse> listRole();

	List<String> getRoleListByUserId(String userId);

	Page<SysRoleResponse> page(Paging page, SysRoleRequest sysRole);

	Boolean updateById(SysRoleRequest req);

	Boolean save(SysRoleRequest req);

	Boolean updateUserRole(UpdateUserRoleRequest req);

	Boolean removeRoleById(Serializable id);

}
