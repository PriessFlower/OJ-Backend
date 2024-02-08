package com.zrx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 访问默认端口跳转到接口文档
 *
 * @author zhang.rx
 * @since 2024/2/8
 */
@Configuration
public class ApiDocViewConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 设置访问路径为 “/” 跳转到指定页面
		registry.addViewController("/").setViewName("forward:/doc.html");
		// 设置为最高优先级
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

}