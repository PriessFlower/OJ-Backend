package com.zt.sys.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mapper扫描配置
 *
 * @author zhang.rx
 * @since 2024/2/18
 */
@Configuration
@MapperScan({"com.zt.**.mapper"})
public class MapperScanConfig {

}
