package com.zrx.sys.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统用户请求体
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(description = "系统用户请求体")
public class SysUserRequest {

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
	 * 状态: 0:停用; 1:正常
	 */
	@Schema(description = "状态: 0:停用; 1:正常")
	private Integer status;

}
