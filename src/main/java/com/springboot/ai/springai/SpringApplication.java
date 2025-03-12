package com.springboot.ai.springai;

import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}
	@Bean
	InMemoryChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

}
