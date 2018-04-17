package com.chenqiang.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * springboot启动类
 * 
 * @author qchen
 * @date 2018-4-13
 *
 */
@SpringBootApplication
// 在启动类上加上@MapperScan注解,或者在每个Mapper类上加@Mapper注解
@MapperScan("com.chenqiang.study.dao")
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
