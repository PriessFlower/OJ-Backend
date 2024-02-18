package com.zrx.sys.controller;

import com.mybatisflex.core.paginate.Page;
import com.zrx.sys.entity.SysUser;
import com.zrx.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用户 控制层。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@RestController
@Tag(name = "接口")
@RequestMapping("sys/sysUser")
public class SysUserController {

	@Resource
	private SysUserService sysUserService;

	/**
	 * 添加。
	 * @param sysUser 系统用户
	 * @return {@code true} 添加成功，{@code false} 添加失败
	 */
	@PostMapping("save")
	@Operation(summary = "保存")
	public boolean save(@RequestBody @Parameter(description = "系统用户") SysUser sysUser) {
		return sysUserService.save(sysUser);
	}

	/**
	 * 根据主键删除。
	 * @param idList 主键列表
	 * @return {@code true} 删除成功，{@code false} 删除失败
	 */
	@DeleteMapping("remove")
	@Operation(summary = "根据主键")
	public boolean remove(@Parameter(description = "主键列表") List<Long> idList) {
		return sysUserService.removeByIds(idList);
	}

	/**
	 * 根据主键更新。
	 * @param sysUser 系统用户
	 * @return {@code true} 更新成功，{@code false} 更新失败
	 */
	@PutMapping("update")
	@Operation(summary = "根据主键更新")
	public boolean update(@RequestBody @Parameter(description = "主键") SysUser sysUser) {
		return sysUserService.updateById(sysUser);
	}

	/**
	 * 根据主键获取详细信息。
	 * @param id 主键
	 * @return 详情
	 */
	@GetMapping("getInfo/{id}")
	@Operation(summary = "根据主键获取")
	public SysUser getInfo(@PathVariable Serializable id) {
		return sysUserService.getById(id);
	}

	/**
	 * 分页查询。
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("page")
	@Operation(summary = "分页查询")
	public Page<SysUser> page(@Parameter(description = "分页信息") Page<SysUser> page) {
		return sysUserService.page(page);
	}

}
