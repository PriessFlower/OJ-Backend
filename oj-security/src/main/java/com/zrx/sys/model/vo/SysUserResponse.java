package com.zrx.sys.model.vo;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统用户响应体
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(name = "系统用户响应体")
public class SysUserResponse {

	/**
	 * 主键
	 */
	@Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
	@Schema(description = "主键")
	private String id;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

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
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	private String deptId;

	/**
	 * 状态: 0:停用; 1:正常
	 */
	@Schema(description = "状态: 0:停用; 1:正常")
	private Integer status;

}
