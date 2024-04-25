package com.zrx.sys.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zrx.model.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色用户关系 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色用户关系表")
@Table(value = "sys_role_user")
public class SysRoleUser extends BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private Long id;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID")
	private Long roleId;

	/**
	 * 用户ID
	 */
	@Schema(description = "用户ID")
	private Long userId;

}
