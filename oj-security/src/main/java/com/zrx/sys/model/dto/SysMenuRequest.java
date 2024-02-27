package com.zrx.sys.model.dto;

import com.zrx.model.common.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统菜单 请求体
 *
 * @author zhang.rx
 * @since 2024/2/26
 */
@Data
@Schema(name = "系统菜单 请求体")
public class SysMenuRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@NotBlank(message = "id 不能为空", groups = { UpdateGroup.class })
	@Schema(description = "id")
	private String id;

	/**
	 * 上级ID
	 */
	@NotBlank(message = "上级ID 不能为空")
	@Schema(description = "上级ID")
	private String pid;

	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称 不能为空")
	@Schema(description = "菜单名称")
	private String name;

	/**
	 * 菜单路径
	 */
	@Schema(description = "菜单路径")
	private String path;

	/**
	 * 重定向
	 */
	@Schema(description = "重定向")
	private String redirect;

	/**
	 * 组件路径
	 */
	@Schema(description = "组件路径")
	private String component;

	/**
	 * 其他配置
	 */
	@Schema(description = "其他配置")
	private RouteMeta meta;

	/**
	 * 类型 0:菜单 1:按钮
	 */
	@NotNull(message = "类型 不能为空")
	@Schema(description = "类型 0:菜单 1:按钮")
	private Integer menuType;

	/**
	 * 授权规则
	 */
	@NotBlank(message = "授权规则 不能为空")
	@Schema(description = "授权规则(多个用逗号分隔，如：sys:user:list,sys:user:save)")
	private String permissions;

	/**
	 * 排序序号
	 */
	@NotNull(message = "排序序号 不能为空")
	@Schema(description = "排序序号")
	private Integer sort;

}
