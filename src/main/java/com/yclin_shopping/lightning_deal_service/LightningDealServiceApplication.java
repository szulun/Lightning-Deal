package com.yclin_shopping.lightning_deal_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.yclin_shopping.lightning_deal_service.db.mappers")
@ComponentScan(basePackages = {"com.yclin_shopping"})
public class LightningDealServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LightningDealServiceApplication.class, args);
	}

}
