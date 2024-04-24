package com.zrx.sys.model.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.zrx.model.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户 实体类。
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统用户")
@Table(value = "sys_user")
public class SysUser extends BaseEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "id")
	private Long id;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickName;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@Schema(description = "密码")
	private String password;

	/**
	 * 盐值
	 */
	@Schema(description = "盐值")
	private String salt;

	/**
	 * 姓名
	 */
	@Schema(description = "姓名")
	private String realName;

	/**
	 * 头像url
	 */
	@Schema(description = "头像url")
	private String avatar;

	/**
	 * 性别: 0:男; 1:女; 2:保密
	 */
	@Schema(description = "性别: 0:男; 1:女; 2:保密")
	private Integer gender;

	/**
	 * 个人简介
	 */
	@Schema(description = "个人简介")
	private String introduce;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String mobile;

	/**
	 * 状态: 0:停用; 1:正常
	 */
	@Schema(description = "状态: 0:停用; 1:正常")
	private Integer status;

}
