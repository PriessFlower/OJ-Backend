package com.zrx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @author zhang.rx
 * @since 2024/2/8
 */
@Slf4j
@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) throws UnknownHostException {
		// InitRabbitMq.init();
		SpringApplication application = new SpringApplication(MainApplication.class);
		ConfigurableApplicationContext applicationContext = application.run(args);

		// 获取本地 ip 地址
		String ip = InetAddress.getLocalHost().getHostAddress();

		// 获取服务配置文件，name、port、以及全局服务路径
		Environment env = applicationContext.getEnvironment();
		String port = env.getProperty("server.port");
		String path = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse("");

		// 打印输出
		log.info("\n------------------------------------------------------------------\n\t" + "成功运行！接口文档访问地址:\n\t"
				+ "Local 访问网址: \t\thttp://localhost:" + port + path + "/doc.html\n\t" + "External 访问网址: \thttp://" + ip
				+ ":" + port + path + "/doc.html\n"
				+ "------------------------------------------------------------------");
	}

}