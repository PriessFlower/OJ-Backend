package com.zrx.security.service;

import cn.hutool.core.util.RandomUtil;
import com.zrx.security.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 登录服务 测试
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@SpringBootTest(classes = LoginTest.class)
public class LoginTest {

	@Test
	public void register() {
		String salt = RandomUtil.randomString(8);
		System.out.println(salt);
		String password = Md5Util.inputPassToDBPass("123456", salt);
		System.out.println(password);
	}

}
