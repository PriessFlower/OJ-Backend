package com.zrx.security.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import com.zrx.sys.service.SysRolePermissionService;
import com.zrx.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限校验
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

	private final SysRoleService sysRoleService;

	private final SysRolePermissionService sysRolePermissionService;

	/**
	 * 返回一个账号所拥有的角色标识集合
	 * @param loginId 登录账号ID（这里用的是用户id）
	 * @param loginType 登录类型
	 * @return 权限码集合
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		String userId = SaFoxUtil.getValueByType(loginId, String.class);
		return sysRoleService.getRoleListByUserId(userId);
	}

	/**
	 * 返回一个账号所拥有的权限码集合
	 * @param loginId 登录账号ID（这里用的是用户id）
	 * @param loginType 登录类型
	 * @return 权限码集合
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		if (loginType.equals(StpUtil.TYPE)) {
			String userId = SaFoxUtil.getValueByType(loginId, String.class);
			// 获取用户对应的所有角色ID
			List<String> roleIdList = sysRoleService.getRoleIdListByUserId(userId);
			return roleIdList.stream()
				.flatMap(roleId -> sysRolePermissionService.getPermissionCodeByRoleId(roleId).stream())
				.collect(Collectors.toList());
		}
		return null;
	}

}
