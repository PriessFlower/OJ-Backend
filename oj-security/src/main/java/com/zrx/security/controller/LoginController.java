package com.zrx.security.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zrx.exception.BusinessException;
import com.zrx.reuslt.Result;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.service.CaptchaService;
import com.zrx.security.utils.Md5Util;
import com.zrx.sys.entity.SysUser;
import com.zrx.sys.enums.UserStatusEnum;
import com.zrx.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.zrx.sys.entity.table.SysUserTableDef.SYS_USER;

/**
 * 登录管理
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@RestController
@Tag(name = "登录管理")
@RequestMapping("security")
@Slf4j
public class LoginController {

	@Resource
	private CaptchaService captchaService;

	@Resource
	private SysUserService sysUserService;

	@GetMapping("captcha")
	@Operation(summary = "获取验证码", description = "获取验证码",
			responses = { @ApiResponse(description = "成功返回验证码图片",
					content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
							schema = @Schema(type = "string", format = "binary"))) })
	public void captcha(HttpServletResponse response, String uuid) throws IOException {
		// uuid不能为空
		if (StringUtils.isBlank(uuid)) {
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "uuid不能为空");
		}
		// 生成验证码
		captchaService.create(response, uuid);
	}

	@PostMapping("login")
	@Operation(summary = "登录")
	public Result<String> login(@Valid @RequestBody LoginRequest login) {

		// 验证码是否正确
		boolean flag = captchaService.validate(login.getUuid(), login.getCaptcha());
		if (!flag) {
			throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "验证码错误");
		}

		// 用户信息
		SysUser user = sysUserService.getByUsername(login.getUsername());

		// 用户不存在
		if (user == null) {
			throw new BusinessException(ResultCode.FAIL.getCode(), "用户不存在");
		}

		// 密码错误
		if (!StringUtils.equals(Md5Util.inputPassToDBPass(login.getPassword(), user.getSalt()), user.getPassword())) {
			throw new BusinessException(ResultCode.FAIL.getCode(), "密码错误");
		}

		// 账号停用
		if (user.getStatus() == UserStatusEnum.DISABLE.getValue()) {
			throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "当前用户已被系统停用");
		}
		StpUtil.login(user.getId());
		return Result.ok();
	}

	@PostMapping("register")
	@Operation(summary = "注册")
	public Result<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
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

		// 是否有相同用户名的用户
		if (sysUserService.exists(new QueryWrapper().where(SYS_USER.USERNAME.eq(username)))) {
			throw new BusinessException("用户名重复");
		}
		boolean saveFlag = sysUserService.save(sysUser);
		if (!saveFlag) {
			throw new BusinessException("注册失败");
		}
		return Result.ok();
	}

}
