package com.zrx.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 配置 打开注解式鉴权功能
 *
 * @author zhang.rx
 * @since 2024/20
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册 Sa-Token 拦截器，打开注解式鉴权功能
		registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
	}

}
