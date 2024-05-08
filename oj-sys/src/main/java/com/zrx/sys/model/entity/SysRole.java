package com.zrx.sys.model.entity;

import com.mybatisflex.annotation.Column;
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
 * 系统角色 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统角色")
@Table(value = "sys_role")
public class SysRole extends BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
	@Schema(description = "id")
	private Long id;

	/**
	 * 角色标识
	 */
	@Schema(description = "角色标识")
	private String roleCode;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

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

	/**
	 * 逻辑删除 0-正常，1-已删除
	 */
	@Column(isLogicDelete = true)
	@Schema(description = "逻辑删除 0-正常，1-已删除")
	private Integer delFlag;

}
