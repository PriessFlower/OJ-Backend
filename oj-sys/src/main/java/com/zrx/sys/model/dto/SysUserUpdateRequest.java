package com.zrx.sys.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统用户请求体
 *
 * @author zhang.rx
 * @since 2024/2/20
 */
@Data
@Schema(description = "系统用户 修改请求体")
public class SysUserUpdateRequest {

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull(message = "id 不能为空")
	private Long id;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

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
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String mobile;

	/**
	 * 个人简介
	 */
	@Schema(description = "个人简介")
	private String introduce;

	/**
	 * 头像url
	 */
	@Schema(description = "头像url")
	private String avatar;

}
