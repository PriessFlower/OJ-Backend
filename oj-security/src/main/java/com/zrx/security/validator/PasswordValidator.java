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
public class PasswordValidator implements ConstraintValidator<Password, String> {

	private static final Pattern ONLY_ALPHANUMERIC = Pattern.compile("^[a-zA-Z0-9]+$");

	private static final Pattern MUST_ALPHANUMERIC = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).*$");

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
		if (!MUST_ALPHANUMERIC.matcher(value).find()) {
			customMessage = "密码必须包含数字和字母";
			setErrorMessage(context, customMessage);
			return false;
		}
		// 密码不能含有特殊字符
		if (!ONLY_ALPHANUMERIC.matcher(value).find()) {
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
