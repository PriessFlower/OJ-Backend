package com.zrx.security.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class Md5Util {

	public static String md5(String str) {
		return DigestUtil.md5Hex(str);
	}

	// 这个盐随机生成, 完成的是 md5（ md5（pass+salt1）+salt2）
	public static String midPassToDBPass(String inputPass, String salt) {
		String str = salt.charAt(1) + inputPass + salt.charAt(5);
		return md5(str);
	}

	// 直接转换成数据库中存储的密码
	public static String inputPassToDBPass(String inputPass, String salt) {
		return midPassToDBPass(inputPass, salt);
	}

}
