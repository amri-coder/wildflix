package com.wildflix.wildflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Random;

@SpringBootApplication
public class WildflixApplication {
	Random random = new Random();
	public static void main(String[] args) {
		SpringApplication.run(WildflixApplication.class, args);
	}

}
