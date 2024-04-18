/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.zrx.sys.enums;

/**
 * 用户状态
 *
 * @author zhang.rx
 * @since 2024/2/18
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

	DISABLE("禁用", 0), ENABLED("正常", 1);

	private final String text;

	private final int value;

}
