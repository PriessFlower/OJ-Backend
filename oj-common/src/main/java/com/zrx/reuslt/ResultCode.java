package com.zrx.reuslt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回码
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

	SUCCESS(200, "成功"), FAIL(50000, "失败"), PARAM_ERROR(40000, "参数错误"), UNAUTHORIZED(40001, "未授权"),
	FORBIDDEN(40003, "权限不足"), NOT_FOUND(40004, "资源不存在"), METHOD_NOT_ALLOWED(40005, "方法不支持"),
	INTERNAL_SERVER_ERROR(500, "服务器内部错误"), SERVICE_UNAVAILABLE(503, "服务不可用");

	private final int code;

	private final String message;

}
