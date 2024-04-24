package com.zrx.sys.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.security.satoken.AuthConst;
import com.zrx.sys.mapper.SysRoleMapper;
import com.zrx.sys.mapper.SysRoleUserMapper;
import com.zrx.sys.model.entity.SysRole;
import com.zrx.sys.model.entity.SysRoleUser;
import com.zrx.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zrx.sys.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static com.zrx.sys.model.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;

/**
 * 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleUserMapper roleUserMapper;

	@Override
	public List<String> getRoleIdListByUserId(String userId) {
		// 先获取其 User-Session
		SaSession session = StpUtil.getSessionByLoginId(userId, false);
		List<String> roleList = roleUserMapper.selectListByQueryAs(
				new QueryWrapper().select(SYS_ROLE_USER.ROLE_ID).where(SYS_ROLE_USER.USER_ID.eq(userId)), String.class);
		if (session == null) {
			// 如果此账号的 User-Session 尚未创建，则直接查库，避免创建 Session 缓存
			return roleList;
		}

		// 从 Session 中获取
		return session.get(AuthConst.ROLE_ID_KEY, () -> roleList);
	}

	@Override
	public List<String> getRoleListByUserId(String userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select(SYS_ROLE_USER.ROLE_ID)
				.where(SYS_ROLE_USER.USER_ID.eq(userId));
		List<SysRoleUser> sysRoleUsers = roleUserMapper.selectListByQuery(queryWrapper);
		if (sysRoleUsers.isEmpty()) {
			throw new BusinessException("该用户没有任何权限");
		}
		List<String> roleIdList = sysRoleUsers.stream().map(SysRoleUser::getRoleId).toList();
		return mapper.selectListByQueryAs(
				new QueryWrapper()
						.select(SYS_ROLE.NAME)
						.where(SYS_ROLE.ID.in(roleIdList)), String.class
		);
	}
}
