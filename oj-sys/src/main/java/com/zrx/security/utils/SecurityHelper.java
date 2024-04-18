package com.zrx.security.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.zrx.sys.model.entity.SysUser;

/**
 * 获取用户信息工具类
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
public class SecurityHelper {

	public static SysUser getUser() {
		String loginId = StpUtil.getLoginId() == null ? "" : (String) StpUtil.getLoginId();
		Object sysUser = StpUtil.getSession().get(loginId);
		if (sysUser == null) {
			return new SysUser();
		}
		return (SysUser) sysUser;
	}

}
