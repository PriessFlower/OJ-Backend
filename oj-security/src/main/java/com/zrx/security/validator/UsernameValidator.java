package com.zrx.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 密码校验器
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

	private static final Pattern ONLY_ALPHANUMERIC = Pattern.compile("^[a-zA-Z0-9]+$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String customMessage;
		// 密码不能为空
		if (StringUtils.isBlank(value)) {
			customMessage = "用户名不能为空";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 密码长度至少为8位
		if (value.length() < 5) {
			customMessage = "用户名长度至少为5位";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 用户名不能含有特殊字符
		if (!ONLY_ALPHANUMERIC.matcher(value).find()) {
			customMessage = "用户名不能含有特殊字符";
			setErrorMessage(context, customMessage);
			return false;
		}
		return true;
	}

	private void setErrorMessage(ConstraintValidatorContext context, String customMessage) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(customMessage).addConstraintViolation();
	}

}
