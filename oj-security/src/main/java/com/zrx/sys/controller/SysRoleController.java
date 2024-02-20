package com.zrx.sys.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.sys.entity.SysRole;
import com.zrx.sys.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统角色 控制层。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@RestController
@Tag(name = "接口")
@RequestMapping("/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 添加系统角色。
	 * @param sysRole 系统角色
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("save")
	@Operation(description = "保存")
	public boolean save(@RequestBody @Parameter(description = "系统角色") SysRole sysRole) {
		return sysRoleService.save(sysRole);
	}

	/**
	 * 根据主键列表删除系统角色。
	 * @param id 主键
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@DeleteMapping("remove/{id}")
	@Operation(description = "根据主键列表删除系统角色")
	public boolean remove(@PathVariable @Parameter(description = "主键") Serializable id) {
		return sysRoleService.removeById(id);
	}

	/**
	 * 根据主键更新系统角色。
	 * @param sysRole 系统角色
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@PutMapping("update")
	@Operation(description = "根据主键更新系统角色")
	public boolean update(@RequestBody @Parameter(description = "系统角色") SysRole sysRole) {
		return sysRoleService.updateById(sysRole);
	}

	/**
	 * 查询所有系统角色。
	 * @return 所有数据
	 */
	@GetMapping("list")
	@Operation(description = "查询所有系统角色")
	public List<SysRole> list() {
		return sysRoleService.list();
	}

	/**
	 * 根据主键获取系统角色详细信息。
	 * @param id 主键
	 * @return 详情
	 */
	@GetMapping("getInfo/{id}")
	@Operation(description = "根据主键获取系统角色详细信息")
	public SysRole getInfo(@PathVariable Serializable id) {
		return sysRoleService.getById(id);
	}

	/**
	 * 分页查询系统角色。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("page")
	@Operation(description = "分页查询")
	public Page<SysRole> page(@Parameter(description = "分页信息") Page<SysRole> page) {
		return sysRoleService.page(page);
	}

}
