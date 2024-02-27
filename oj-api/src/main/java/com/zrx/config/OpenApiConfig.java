package com.zrx.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApi 配置类
 *
 * @author zhang.rx
 * @since 2024/2/8
 */
@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
			// 接口文档标题
			.info(new Info().title("API接口文档")
				// 接口文档简介
				.description("这是基于Knife4j OpenApi3的接口文档")
				// 接口文档版本
				.version("v1.0")
				// 开发者联系方式
				.contact(new Contact().name("zhang.rx").email("zrx15726109130@outlook.com")))
			.externalDocs(new ExternalDocumentation().description("OJ").url("http://localhost:8101/api/"));
	}

}
