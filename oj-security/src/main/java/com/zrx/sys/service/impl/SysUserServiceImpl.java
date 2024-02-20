package com.zrx.sys.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.service.CaptchaService;
import com.zrx.security.utils.Md5Util;
import com.zrx.sys.entity.SysUser;
import com.zrx.sys.enums.UserStatusEnum;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.service.SysUserService;
import jakarta.annotation.Resource;
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

	@Resource
	private CaptchaService captchaService;

	@Override
	public SysUser getByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new BusinessException("用户名为空");
		}
		return mapper.selectOneByQuery(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)));
	}

	@Override
	public String login(LoginRequest loginRequest) {

		// 验证码是否正确
		boolean flag = captchaService.validate(loginRequest.getUuid(), loginRequest.getCaptcha());
		if (!flag) {
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "验证码错误");
		}

		// 用户信息
		SysUser user = getByUsername(loginRequest.getUsername());

		// 用户不存在
		if (user == null) {
			throw new BusinessException(ResultCode.FAIL.getCode(), "用户不存在");
		}

		// 密码错误
		if (!StringUtils.equals(Md5Util.inputPassToDBPass(loginRequest.getPassword(), user.getSalt()),
				user.getPassword())) {
			throw new BusinessException(ResultCode.FAIL.getCode(), "密码错误");
		}

		// 账号停用
		if (user.getStatus() == UserStatusEnum.DISABLE.getValue()) {
			throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "当前用户已被系统停用");
		}
		StpUtil.login(user.getId());
		return "登陆成功";
	}

	@Override
	public String register(RegisterRequest registerRequest) {
		String username = registerRequest.getUsername();
		String password = registerRequest.getPassword();
		String checkPassword = registerRequest.getCheckPassword();
		if (!StringUtils.equals(password, checkPassword)) {
			throw new BusinessException("两次输入的密码不一致");
		}
		SysUser sysUser = new SysUser();
		String salt = Md5Util.generateSalt();
		sysUser.setUsername(registerRequest.getUsername());
		sysUser.setPassword(Md5Util.inputPassToDBPass(password, salt));
		sysUser.setSalt(salt);
		sysUser.setStatus(UserStatusEnum.ENABLED.getValue());

		// 是否有相同用户名的用户
		if (exists(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)))) {
			throw new BusinessException("用户名重复");
		}
		boolean saveFlag = save(sysUser);
		if (!saveFlag) {
			throw new BusinessException("注册失败");
		}
		return "注册成功";
	}

}
