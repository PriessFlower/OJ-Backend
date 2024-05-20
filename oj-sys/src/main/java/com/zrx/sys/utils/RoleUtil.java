package com.zrx.sys.utils;

import com.mybatisflex.core.query.QueryWrapper;
import com.zrx.sys.mapper.SysRoleMapper;
import com.zrx.sys.model.entity.SysRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zrx.sys.model.entity.table.SysRoleTableDef.SYS_ROLE;
import static com.zrx.sys.model.entity.table.SysRoleUserTableDef.SYS_ROLE_USER;

/**
 * 用户角色工具类
 *
 * @author zhang.rx
 * @since 2024/5/8
 */
@Component
public class RoleUtil {

	@Resource
	private SysRoleMapper sysRoleMapper;

	/**
	 * 根据用户id获取其最大角色
	 * @return SysRole
	 */
	public SysRole selectMaxRoleByUserId(Long userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select(SYS_ROLE.DEFAULT_COLUMNS)
			.from(SYS_ROLE)
			.leftJoin(SYS_ROLE_USER)
			.on(SYS_ROLE.ID.eq(SYS_ROLE_USER.ROLE_ID))
			.where(SYS_ROLE_USER.USER_ID.eq(userId))
			.orderBy(SYS_ROLE.SORT.asc())
			.limit(1);
		return sysRoleMapper.selectOneByQuery(queryWrapper);
	}

	/**
	 * 根据roleId列表获取最高权限角色
	 */
	public SysRole selectMaxRoleByRoleIds(List<Long> roleIds) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.select(SYS_ROLE.DEFAULT_COLUMNS)
			.from(SYS_ROLE)
			.where(SYS_ROLE.ID.in(roleIds))
			.orderBy(SYS_ROLE.SORT.asc())
			.limit(1);
		return sysRoleMapper.selectOneByQuery(queryWrapper);
	}

}
