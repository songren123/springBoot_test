package com.tydic.dateSourceTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages="com.tydic")
@PropertySource(value = { "classpath:application.yml" })
public class DataSourceTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSourceTestApplication.class, args);
	}

}
