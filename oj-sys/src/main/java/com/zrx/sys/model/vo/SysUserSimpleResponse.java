package com.zrx.sys.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 系统用户简洁响应体
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(description = "系统用户简洁响应体")
public class SysUserSimpleResponse {

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickName;

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
	 * 个人简介
	 */
	@Schema(description = "个人简介")
	private String introduce;

}
