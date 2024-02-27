package com.zrx.security.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 密码校验
 *
 * @author zhang.rx
 * @since 2024/2/19
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { PasswordValidator.class })
public @interface Password {

	String message() default "密码格式错误";

	// 默认参数
	Class<?>[] groups() default {};

	// 默认参数
	Class<? extends Payload>[] payload() default {};

}
