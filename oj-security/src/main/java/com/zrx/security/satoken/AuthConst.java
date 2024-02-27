package com.zrx.security.satoken;

/**
 * 权限码常量
 * @author zhang.rx
 * @since 2024/2/20
 */
public final class AuthConst {

	/**
	 * 私有构造方法
	 */
	private AuthConst() {
	}

	// --------------- 代表身份的权限 ---------------

	// --------------- 所有权限码 ---------------

	// 超级管理员
	public static final String SUPER_ADMIN = "super-admin";

	// --------------- 其它常量 ---------------

	/** 在 SaSession 上存储 角色id 使用的key */
	public static final String ROLE_ID_KEY = "ROLE_ID";

}
