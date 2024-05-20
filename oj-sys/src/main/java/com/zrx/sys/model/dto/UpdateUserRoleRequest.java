package com.zrx.sys.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 更改用户角色请求
 *
 * @author zhang.rx
 * @since 2024/4/25
 */
@Data
@Schema(description = "更改用户角色请求")
public class UpdateUserRoleRequest {

	/**
	 * 用户id
	 */
	@NotNull(message = "用户id不能为空")
	@Schema(description = "用户id")
	private Long userId;

	/**
	 * 角色id
	 */
	@Valid
	@NotNull(message = "角色id列表不能为空")
	@Schema(description = "角色id")
	private List<Long> roleIds;

}
