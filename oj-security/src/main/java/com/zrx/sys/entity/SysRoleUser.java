package com.zrx.sys.entity;

import com.mybatisflex.annotation.Id;
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
 * 角色用户关系 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色用户关系表")
@Table(value = "sys_role_user")

public class SysRoleUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@Schema(description = "id")
	private String id;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private String roleId;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private String userId;

	/**
	 * 创建者
	 */
	@Schema(description = "创建者")
	private String creator;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

}
