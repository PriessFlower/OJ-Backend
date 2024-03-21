package com.zrx.security.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录表单
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Data
@Schema(description = "登录表单")
public class LoginRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "用户名不能为空")
	private String username;

	@Schema(description = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	@Schema(description = "验证码")
	@NotBlank(message = "验证码不能为空")
	private String captcha;

	@Schema(description = "uuid")
	@NotBlank(message = "uuid不能为空")
	private String uuid;

}