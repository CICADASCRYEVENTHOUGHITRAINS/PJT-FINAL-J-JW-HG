package com.ssafit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan(basePackages = "com.ssafit.model.dao")
@Configuration
public class DBConfig {
	
}
