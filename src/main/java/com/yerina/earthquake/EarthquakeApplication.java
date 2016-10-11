package com.yerina.earthquake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yerina.earthquake")
public class EarthquakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarthquakeApplication.class, args);
	}

}
