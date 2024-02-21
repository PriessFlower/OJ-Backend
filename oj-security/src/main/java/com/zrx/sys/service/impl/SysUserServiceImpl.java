package com.zrx.sys.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.util.StringUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zrx.exception.BusinessException;
import com.zrx.model.common.Paging;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.model.vo.LoginResponse;
import com.zrx.security.service.CaptchaService;
import com.zrx.security.utils.Md5Util;
import com.zrx.sys.enums.UserStatusEnum;
import com.zrx.sys.mapper.SysUserMapper;
import com.zrx.sys.mapstruct.SysUserMap;
import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import com.zrx.sys.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.zrx.sys.model.entity.table.SysUserTableDef.SYS_USER;

/**
 * 系统用户 服务层实现。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final CaptchaService captchaService;

	private final SysUserMap sysUserMap;

	@Override
	public SysUser getByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			throw new BusinessException("用户名为空");
		}
		return mapper.selectOneByQuery(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)));
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {

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

		// 删除之前的token信息
		if (StpUtil.isLogin()) {
			StpUtil.logout();
		}

		StpUtil.login(user.getId());
        return new LoginResponse(StpUtil.getTokenInfo().getTokenValue(), user.getId());
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

	@Override
	public Boolean disable(String id) {
		return UpdateChain.of(mapper)
			.where(SYS_USER.ID.eq(id))
			.set(SYS_USER.STATUS, UserStatusEnum.DISABLE.getValue())
			.update();
	}

	@Override
	public Boolean enable(String id) {
		return UpdateChain.of(mapper)
			.where(SYS_USER.ID.eq(id))
			.set(SYS_USER.STATUS, UserStatusEnum.ENABLED.getValue())
			.update();
	}

	@Override
	public SysUserResponse getInfoById(Serializable id) {
		SysUser sysUser = QueryChain.of(mapper).where(SYS_USER.ID.eq(id)).one();
		if (sysUser == null) {
			throw new BusinessException("未找到该用户信息");
		}
		return sysUserMap.entity2Dto(sysUser);
	}

	@Override
	public Page<SysUserResponse> page(Paging page, SysUserRequest request) {
		QueryWrapper queryWrapper = new QueryWrapper().select()
			.from(SYS_USER)
			.where(SYS_USER.USERNAME.like(request.getUsername(), StringUtil::isNotBlank))
			.and(SYS_USER.REAL_NAME.like(request.getRealName(), StringUtil::isNotBlank))
			.and(SYS_USER.GENDER.eq(request.getGender()))
			.and(SYS_USER.EMAIL.like(request.getEmail(), StringUtil::isNotBlank))
			.and(SYS_USER.MOBILE.like(request.getMobile(), StringUtil::isNotBlank))
			.and(SYS_USER.STATUS.eq(request.getStatus()));
		return mapper.paginateAs(Page.of(page.getPageNum(), page.getPageSize()), queryWrapper, SysUserResponse.class);
	}

}
