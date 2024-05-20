package com.zrx.sys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

	DISABLE("禁用", 1), ENABLED("正常", 0);

	private final String text;

	private final int value;

}
