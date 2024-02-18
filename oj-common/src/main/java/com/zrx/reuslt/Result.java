package com.zrx.reuslt;

import lombok.Data;

/**
 * 统一返回结果
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Data
public class Result<T> {

	private Integer code;

	private String message;

	private T data;

	public Result() {
	}

	public Result(T data) {
		this.data = data;
	}

	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<>(data);
		result.setCode(ResultCode.SUCCESS.getCode());
		result.setMessage(ResultCode.SUCCESS.getMessage());
		return result;
	}

	public static <T> Result<T> fail(String message) {
		Result<T> result = new Result<>();
		result.setCode(ResultCode.FAIL.getCode());
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> fail(int code, String message) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> res(T data, int code, String message) {
		Result<T> result = new Result<>(data);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> res(T data, ResultCode resultCode) {
		Result<T> result = new Result<>(data);
		result.setCode(resultCode.getCode());
		result.setMessage(resultCode.getMessage());
		return result;
	}

	public static <T> Result<T> ok() {
		return res(null, ResultCode.SUCCESS.getCode(), "操作成功");
	}

}
