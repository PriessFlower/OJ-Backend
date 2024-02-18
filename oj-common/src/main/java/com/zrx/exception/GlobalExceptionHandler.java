package com.zrx.exception;

import com.zrx.reuslt.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ BusinessException.class })
	public Result<?> businessExceptionHandler(BusinessException e) {
		log.error("业务异常: ", e);
		return Result.fail(e.getCode(), e.getMessage());
	}
	// TODO: 绑定异常 BindingException

}
