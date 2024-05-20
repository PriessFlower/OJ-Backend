package com.zrx.sys.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.mybatisflex.core.paginate.Page;
import com.zrx.model.common.Paging;
import com.zrx.reuslt.Result;
import com.zrx.security.satoken.AuthConst;
import com.zrx.sys.model.dto.SysRoleRequest;
import com.zrx.sys.model.dto.UpdateUserRoleRequest;
import com.zrx.sys.model.vo.SysRoleResponse;
import com.zrx.sys.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 角色管理 控制层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@RestController
@Tag(name = "SysRole", description = "角色管理")
@RequestMapping("/sys/role")
public class SysRoleController {

	@Resource
	private SysRoleService sysRoleService;

	/**
	 * 添加系统角色。
	 * @param req 系统角色
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("/save")
	@Operation(summary = "保存")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Boolean> save(@RequestBody @Parameter(description = "系统角色") SysRoleRequest req) {
		return Result.success(sysRoleService.save(req));
	}

	/**
	 * 查询所有系统角色。
	 * @return 系统角色
	 */
	@GetMapping("/list")
	@Operation(summary = "查询所有系统角色")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<List<SysRoleResponse>> list() {
		return Result.success(sysRoleService.listRole());
	}

	/**
	 * 根据主键列表删除系统角色。
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@DeleteMapping("/remove/{id}")
	@Operation(summary = "根据主键列表删除系统角色")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Boolean> remove(@PathVariable @Parameter(description = "主键") Serializable id) {
		return Result.success(sysRoleService.removeRoleById(id));
	}

	/**
	 * 根据主键更新系统角色。
	 * @param req 系统角色
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@PutMapping("/update")
	@Operation(summary = "根据主键更新系统角色")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Boolean> update(@RequestBody @Parameter(description = "系统角色") SysRoleRequest req) {
		return Result.success(sysRoleService.updateById(req));
	}

	/**
	 * 分页查询系统角色。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Page<SysRoleResponse>> page(@Parameter(description = "分页信息") Paging page,
			@Parameter(description = "查询参数") SysRoleRequest req) {
		return Result.success(sysRoleService.page(page, req));
	}

	/**
	 * 修改系统用户角色。
	 * @param req 系统角色
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@PutMapping("/update/userRole")
	@Operation(summary = "修改系统用户角色")
	@SaCheckRole(value = { AuthConst.SUPER_ADMIN, AuthConst.ADMIN }, mode = SaMode.OR)
	public Result<Boolean> update(@Valid @RequestBody @Parameter(description = "更改用户角色请求") UpdateUserRoleRequest req) {
		return Result.success(sysRoleService.updateUserRole(req));
	}

}
