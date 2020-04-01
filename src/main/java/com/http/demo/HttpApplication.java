package com.http.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpApplication {

	private static final Logger log = LoggerFactory.getLogger(HttpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(HttpApplication.class, args);
		log.error("ok");
	}

}
