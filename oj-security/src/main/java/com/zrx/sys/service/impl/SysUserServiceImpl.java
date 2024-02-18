package com.zrx.sys.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.sys.entity.SysUser;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.zrx.sys.entity.table.SysUserTableDef.SYS_USER;

/**
 * 系统用户 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Override
	public SysUser getByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new BusinessException("用户名为空");
		}
		return mapper.selectOneByQuery(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)));
	}

}
