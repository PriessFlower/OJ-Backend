package com.zrx.sys.service;

import com.mybatisflex.core.service.IService;
import com.zrx.sys.model.entity.SysRole;

import java.util.List;

/**
 * 服务层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public interface SysRoleService extends IService<SysRole> {

	List<String> getRoleIdListByUserId(String userId);

}
