package com.zrx.sys.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统角色
 *
 * @author zhang.rx
 * @since 2024/4/24
 */
@Data
@Schema(description = "系统角色")
public class SysRoleResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 角色标识
	 */
	@Schema(description = "角色标识")
	private String roleCode;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	private String deptId;

}