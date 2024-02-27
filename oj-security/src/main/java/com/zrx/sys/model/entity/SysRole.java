package com.zrx.sys.model.entity;

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
 * 系统角色 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统角色")
@Table(value = "sys_role")

public class SysRole implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@Schema(description = "id")
	private String id;

	/**
	 * 角色名称
	 */
	@Schema(description = "角色名称")
	private String name;

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
	 * 创建者
	 */
	@Schema(description = "创建者")
	private String creator;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@Schema(description = "更新者")
	private String updater;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
