package com.zrx.security.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zrx.exception.BusinessException;
import com.zrx.reuslt.Result;
import com.zrx.reuslt.ResultCode;
import com.zrx.security.model.dto.LoginRequest;
import com.zrx.security.model.dto.RegisterRequest;
import com.zrx.security.satoken.AuthConst;
import com.zrx.security.service.CaptchaService;
import com.zrx.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 登录管理
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@RestController
@Tag(name = "登录管理")
@RequestMapping("/security")
public class LoginController {

	@Resource
	private CaptchaService captchaService;

	@Resource
	private SysUserService sysUserService;

	@GetMapping("/captcha")
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

	@GetMapping("/getCaptcha")
	@Operation(summary = "获取验证码真实值", description = "获取验证码真实值，不可对外暴露")
	public Result<String> getCaptcha(String uuid) {
		return Result.success(captchaService.getCacheNotDel(uuid));
	}

	@PostMapping("/login")
	@Operation(summary = "登录")
	public Result<String> login(@Valid @RequestBody LoginRequest loginRequest) {
		return Result.success(sysUserService.login(loginRequest));
	}

	@GetMapping("/logout")
	@Operation(summary = "退出登录")
	public Result<String> logout() {
		StpUtil.logout();
		return Result.ok();
	}

	@GetMapping("/isLogin")
	@Operation(summary = "是否登录")
	@SaCheckPermission(AuthConst.SUPER_ADMIN)
	public Result<String> isLogin() {
		return Result.success("是否登录：" + StpUtil.isLogin());
	}

	@GetMapping("/tokenInfo")
	@Operation(summary = "查询token信息")
	@SaCheckPermission(AuthConst.SUPER_ADMIN)
	public Result<SaTokenInfo> tokenInfo() {
		return Result.success(StpUtil.getTokenInfo());
	}

	@PostMapping("/register")
	@Operation(summary = "注册")
	public Result<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return Result.success(sysUserService.register(registerRequest));
	}

}
