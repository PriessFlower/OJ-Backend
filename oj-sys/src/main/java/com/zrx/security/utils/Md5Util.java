package com.zrx.security.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

public class Md5Util {

	public static String md5(String str) {
		return DigestUtil.md5Hex(str);
	}

	// 直接转换成数据库中存储的密码
	public static String inputPassToDBPass(String inputPass, String salt) {
		String str = salt.charAt(1) + inputPass + salt.charAt(5);
		return md5(str);
	}

	public static String generateSalt() {
		return RandomUtil.randomString(8);
	}

}
