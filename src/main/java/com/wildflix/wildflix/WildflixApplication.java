package com.wildflix.wildflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class WildflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(WildflixApplication.class, args);
	}

}
