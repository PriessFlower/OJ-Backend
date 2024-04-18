package com.zrx.security.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author zhang.rx
 * @since 2024/2/18
 */
public interface CaptchaService {

	/**
	 * 图片验证码
	 */
	void create(HttpServletResponse response, String uuid) throws IOException;

	/**
	 * 验证码效验
	 * @param uuid uuid
	 * @param code 验证码
	 * @return true：成功 false：失败
	 */
	boolean validate(String uuid, String code);

	/**
	 * 根据 key 获取验证码 -- 仅测试用，不可对外暴露
	 * @param key uuid
	 * @return 验证码
	 */
	String getCacheNotDel(String key);

}
