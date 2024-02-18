package com.zrx.exception;

import com.zrx.reuslt.ResultCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	private Integer code;

	private String message;

	public BusinessException(String message) {
		this.message = message;
		this.code = ResultCode.FAIL.getCode();
	}

	public BusinessException(ResultCode resultCode) {
		this.message = resultCode.getMessage();
		this.code = resultCode.getCode();
	}

	public BusinessException(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
