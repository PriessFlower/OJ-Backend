package com.zrx.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.zrx.reuslt.Result;
import com.zrx.reuslt.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

/**
 * 全局异常处理类
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 业务异常
	 */
	@ExceptionHandler({ BusinessException.class })
	public Result<?> businessExceptionHandler(BusinessException e) {
		log.error("业务异常: ", e);
		return Result.fail(e.getCode(), e.getMessage());
	}

	/**
	 * 数据绑定异常-针对 Valid 注解校验
	 */
	@ExceptionHandler({ BindException.class })
	public Result<?> bindExceptionHandler(BindException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		List<String> msgList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
		String messages = StringUtils.join(msgList.toArray(), ",");
		log.error("数据绑定异常: ", e);
		return Result.fail(ResultCode.PARAM_ERROR.getCode(), messages);
	}

	@ExceptionHandler({ NotLoginException.class })
	public Result<?> notLoginExceptionHandler(NotLoginException e) {
		log.error("未登录: ", e);
		return Result.fail(ResultCode.UNAUTHORIZED.getCode(), "请登录");
	}

	/**
	 * 角色权限异常 -- sa-token
	 */
	@ExceptionHandler({ NotRoleException.class })
	public Result<?> notRoleExceptionHandler(NotRoleException e) {
		log.error("角色权限异常: ", e);
		return Result.fail(ResultCode.FORBIDDEN);
	}

	/**
	 * 无权限异常 -- sa-token
	 */
	@ExceptionHandler({ NotPermissionException.class })
	public Result<?> notPermissionExceptionHandler(NotPermissionException e) {
		log.error("无权限异常: ", e);
		return Result.fail(ResultCode.FORBIDDEN);
	}

	@ExceptionHandler({ NoResourceFoundException.class })
	public Result<?> noResourceFoundExceptionHandler(NoResourceFoundException e) {
		log.error("资源不存在: ", e);
		return Result.fail(ResultCode.NOT_FOUND);
	}

	@ExceptionHandler({ Exception.class })
	public Result<?> exceptionHandler(Exception e) {
		log.error("服务器异常: ", e);
		return Result.fail(ResultCode.INTERNAL_SERVER_ERROR);
	}

}
