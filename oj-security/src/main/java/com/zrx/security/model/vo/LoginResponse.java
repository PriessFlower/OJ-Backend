package com.zrx.security.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录响应
 *
 * @author zhang.rx
 * @since 2024/2/21
 */
@Data
@Schema(description = "登录响应")
public class LoginResponse {

	/**
	 * token
	 */
	@Schema(description = "token")
	private String token;

	/**
	 * 用户id
	 */
	@Schema(description = "用户id")
	private String userId;

	public LoginResponse(String token, String userId) {
		this.token = token;
		this.userId = userId;
	}

}
