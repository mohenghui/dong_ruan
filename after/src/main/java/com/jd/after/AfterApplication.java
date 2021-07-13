package com.jd.after;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 入口文件 ，扫描MapperScan，交给spring管理dao
@SpringBootApplication
@MapperScan(basePackages = {"com.jd.after.modules.*.dao"})
public class AfterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfterApplication.class, args);
	}


}
