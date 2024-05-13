package com.zrx;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Mybatis-Flex 代码生成
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
public class Codegen {

	public static void main(String[] args) {
		// 配置数据源
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/sb-oj?characterEncoding=utf-8&useInformationSchema=true");
		dataSource.setUsername("root");
		dataSource.setPassword("abc123456");

		GlobalConfig globalConfig = createGlobalConfigUseStyle1();

		// 通过 datasource 和 globalConfig 创建代码生成器
		Generator generator = new Generator(dataSource, globalConfig);

		// 生成代码
		generator.generate();
	}

	public static GlobalConfig createGlobalConfigUseStyle1() {
		// 创建配置内容
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.getJavadocConfig().setAuthor("zhang.rx").setSince("2024/5/13");

		// 设置根包
		globalConfig.setBasePackage("com.zrx");

		// 设置只生成哪些表
		globalConfig.setGenerateTable("oj_post_thumb");
		// 设置作者
		globalConfig.setAuthor("zhang.rx");

		// 设置生成 entity 并启用 Lombok
		globalConfig.setEntityGenerateEnable(true);
		globalConfig.setEntityWithLombok(true);

		// 设置生成 mapper
		globalConfig.setMapperGenerateEnable(true);
		// 设置生成 mapper.xml 文件
		globalConfig.setMapperXmlGenerateEnable(true);
		// 设置生成 Controller
		globalConfig.setControllerGenerateEnable(true);
		// 设置生成 Service
		globalConfig.setServiceGenerateEnable(true);
		globalConfig.setServiceImplGenerateEnable(true);

		globalConfig.getEntityConfig().setWithSwagger(true);
		globalConfig.getEntityConfig().setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

		return globalConfig;
	}

}
