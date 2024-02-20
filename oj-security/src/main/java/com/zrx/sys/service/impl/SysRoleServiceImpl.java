package com.zrx.sys.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.security.satoken.AuthConst;
import com.zrx.sys.entity.SysRole;
import com.zrx.sys.mapper.SysRoleMapper;
import com.zrx.sys.service.SysRoleService;
import com.zrx.sys.service.SysRoleUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zrx.sys.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;

/**
 * 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleUserService sysRoleUserService;

	@Override
	public List<String> getRoleIdListByUserId(String userId) {
		// 先获取其 User-Session
		SaSession session = StpUtil.getSessionByLoginId(userId, false);
		List<String> roleList = sysRoleUserService.listAs(
				new QueryWrapper().select(SYS_ROLE_USER.ROLE_ID).where(SYS_ROLE_USER.USER_ID.eq(userId)), String.class);
		if (session == null) {
			// 如果此账号的 User-Session 尚未创建，则直接查库，避免创建 Session 缓存
			return roleList;
		}

		// 从 Session 中获取
		return session.get(AuthConst.ROLE_ID_KEY, () -> roleList);
	}

}
