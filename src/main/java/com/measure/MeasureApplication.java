package com.measure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.measure" })
@ComponentScan("com.measure")
public class MeasureApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasureApplication.class, args);
	}
}
