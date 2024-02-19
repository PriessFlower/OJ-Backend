package com.zrx.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * 密码校验器
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

	private static final String ONLY_ALPHANUMERIC = "^[a-zA-Z0-9]+$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		String customMessage;
		// 密码不能为空
		if (StringUtils.isBlank(value)) {
			customMessage = "密码不能为空";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 密码长度至少为8位
		if (value.length() < 8) {
			customMessage = "密码长度至少为8位";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 密码必须包含数字和字母
		if (!value.matches("^(?=.*\\d)(?=.*[a-zA-Z]).*$")) {
			customMessage = "密码必须包含数字和字母";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 密码不能含有特殊字符
		if (!value.matches(ONLY_ALPHANUMERIC)) {
			customMessage = "密码不能含有特殊字符";
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
