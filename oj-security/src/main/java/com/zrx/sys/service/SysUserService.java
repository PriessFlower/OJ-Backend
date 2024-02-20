package com.zrx.sys.service;

import com.mybatisflex.core.service.IService;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.sys.entity.SysUser;

/**
 * 系统用户 服务层。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
public interface SysUserService extends IService<SysUser> {

	SysUser getByUsername(String username);

	String login(LoginRequest loginRequest);

	String register(RegisterRequest registerRequest);

}
