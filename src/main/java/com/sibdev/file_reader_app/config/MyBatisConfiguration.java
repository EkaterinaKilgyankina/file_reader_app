package com.sibdev.file_reader_app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sibdev.file_reader_app.repository")
public class MyBatisConfiguration {
}
