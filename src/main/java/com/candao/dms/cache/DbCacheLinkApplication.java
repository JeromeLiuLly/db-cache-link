package com.candao.dms.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ComponentScan(basePackages={"com.candao.dms"}) 
public class DbCacheLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbCacheLinkApplication.class, args);
	}
}
