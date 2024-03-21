package com.zrx.security.model.dto;

import com.zrx.security.validator.Password;
import com.zrx.security.validator.Username;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 注册表单
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Data
@Schema(description = "注册表单")
public class RegisterRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Username
	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;

	@Password
	@Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
	private String password;

	@Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "确认密码不能为空")
	private String checkPassword;

	@Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "手机号不能为空")
	private String mobile;

}