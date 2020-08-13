package com.web.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.web.blog" })

public class WebBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBlogApplication.class, args);
	}

}
