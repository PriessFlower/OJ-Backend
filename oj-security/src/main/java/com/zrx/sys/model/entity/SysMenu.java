package com.zrx.sys.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zrx.utils.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统菜单管理 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统菜单管理")
@Table(value = "sys_menu")
public class SysMenu extends TreeNode<SysMenu> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private String id;

	/**
	 * 上级ID，一级菜单为0
	 */
	@Schema(description = "上级ID，一级菜单为0")
	private String pid;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
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
	private String meta;

	/**
	 * 授权(多个用逗号分隔，如：sys:user:list,sys:user:save)
	 */
	@Schema(description = "授权(多个用逗号分隔，如：sys:user:list,sys:user:save)")
	private String permissions;

	/**
	 * 类型 0：菜单 1：按钮
	 */
	@Schema(description = "类型   0：菜单   1：按钮")
	private Integer menuType;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

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
