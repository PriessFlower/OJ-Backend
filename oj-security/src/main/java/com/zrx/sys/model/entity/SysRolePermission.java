package com.zrx.sys.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统角色权限 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统角色权限")
@Table(value = "sys_role_permission")
public class SysRolePermission implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id号
	 */
	@Id(keyType = KeyType.Auto)
	@Schema(description = "id")
	private Long id;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private String roleId;

	/**
	 * 菜单项ID
	 */
	@Schema(description = "菜单项ID")
	private String permissionCode;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}
