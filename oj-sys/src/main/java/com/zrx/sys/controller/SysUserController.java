package com.zrx.sys.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.mybatisflex.core.paginate.Page;
import com.zrx.exception.BusinessException;
import com.zrx.model.common.Paging;
import com.zrx.reuslt.Result;
import com.zrx.security.satoken.AuthConst;
import com.zrx.sys.model.dto.SysUserRequest;
import com.zrx.sys.model.dto.SysUserUpdateRequest;
import com.zrx.sys.model.entity.SysUser;
import com.zrx.sys.model.vo.SysUserResponse;
import com.zrx.sys.model.vo.SysUserSimpleResponse;
import com.zrx.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 系统用户 控制层。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@RestController
@Tag(name = "SysUser", description = "系统用户管理接口")
@RequestMapping("/sys/sysUser")
public class SysUserController {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 上传头像
	 * @param file 文件
	 * @return url
	 */
	@PostMapping("/upload/avatar")
	@Operation(summary = "上传头像")
	public Result<String> upload(@RequestParam("file") MultipartFile file) {
		return Result.success(sysUserService.uploadAvatar(file));
	}

	/**
	 * 修改用户信息--开放给个人用户使用
	 * @param request 请求参数
	 * @return boolean
	 */
	@PutMapping("/update")
	@Operation(summary = "修改用户信息--用户使用")
	public Result<Boolean> updateUserInfo(@RequestBody SysUserUpdateRequest request) {
		return Result.success(sysUserService.update(request));
	}

	/**
	 * 禁用用户
	 * @param id 主键
	 * @return 禁用结果
	 */
	@PutMapping("/disable/{id}")
	@Operation(summary = "禁用用户")
	@SaCheckRole(AuthConst.SUPER_ADMIN)
	public Result<String> disable(@PathVariable @Parameter(description = "主键") String id) {
		if (!sysUserService.disable(id)) {
			throw new BusinessException("禁用失败");
		}
		return Result.ok();
	}

	/**
	 * 启用用户
	 * @param id 主键
	 * @return 启用结果
	 */
	@PutMapping("/enable/{id}")
	@Operation(summary = "启用用户")
	@SaCheckRole(AuthConst.SUPER_ADMIN)
	public Result<Boolean> enable(@PathVariable @Parameter(description = "主键") String id) {
		if (!sysUserService.enable(id)) {
			throw new BusinessException("启用失败");
		}
		return Result.ok();
	}

	/**
	 * 根据主键获取系统用户详细信息。
	 * @return 详情
	 */
	@GetMapping("/getInfo")
	@Operation(summary = "获取已登录的当前用户信息--用户使用")
	public Result<SysUserResponse> getInfo() {
		return Result.success(sysUserService.getInfo());
	}

	/**
	 * 根据主键获取系统用户信息。
	 * @return 详情
	 */
	@GetMapping("/getInfoById/{id}")
	@Operation(summary = "根据主键获取系统用户信息--用户使用")
	public Result<SysUserSimpleResponse> getInfoById(@PathVariable("id") String userId) {
		return Result.success(sysUserService.getInfoById(userId));
	}

	/**
	 * 分页查询。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Page<SysUserResponse>> page(@Parameter(description = "分页条件") Paging page,
			@Parameter(description = "查询条件") SysUserRequest request) {
		return Result.success(sysUserService.page(page, request));
	}

	/**
	 * 获取已登录的当前用户信息。
	 * @return 当前用户信息
	 */
	@GetMapping("/info")
	@Operation(summary = "根据主键获取系统用户详细信息--admin使用")
	@SaCheckRole(AuthConst.SUPER_ADMIN)
	public Result<SysUser> infoById(String id) {
		return Result.success(sysUserService.getById(id));
	}

	/**
	 * 强制踢人下线
	 */
	@PutMapping("/kick/{id}")
	@Operation(summary = "强制踢人下线")
	@SaCheckRole(AuthConst.SUPER_ADMIN)
	public Result<Boolean> kick(@PathVariable @Parameter(description = "主键") Long id) {
		return Result.success(sysUserService.kick(id));
	}

}
