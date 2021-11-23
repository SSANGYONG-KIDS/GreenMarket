package com.ssangyong.GreenMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssangyong"})
public class GreenMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenMarketApplication.class, args);
	}

}

