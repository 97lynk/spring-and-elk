package io.a97lynk.springapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@SpringBootApplication
@RestController
public class SpringAppApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAppApplication.class);


	@GetMapping
	public String home() {
		LOGGER.info(new Date().toString());
		return new Date().toString();
	}

	@GetMapping("/exception")
	public String exception() {
		return String.valueOf(1 / 0);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringAppApplication.class, args);
	}

}
