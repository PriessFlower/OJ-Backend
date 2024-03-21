package com.zrx.reuslt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回结果
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一返回结果")
public class Result<T> {

	private Integer code;

	private String message;

	private T result;

	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static <T> Result<T> success(T result) {
		return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), result);
	}

	public static <T> Result<T> fail(String message) {
		return new Result<>(ResultCode.FAIL.getCode(), message);
	}

	public static <T> Result<T> fail(ResultCode resultCode) {
		return new Result<>(resultCode.getCode(), resultCode.getMessage());
	}

	public static <T> Result<T> fail(int code, String message) {
		return new Result<>(code, message);
	}

	public static <T> Result<T> ok() {
		return new Result<>(ResultCode.SUCCESS.getCode(), "成功", null);
	}

}
