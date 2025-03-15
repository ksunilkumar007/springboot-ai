package com.springboot.ai.springai;

import com.springboot.ai.springai.repository.WalletRepository;
import com.springboot.ai.springai.tools.StockTools;
import com.springboot.ai.springai.tools.WalletTools;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}

	@Bean
	@Description("A Rest Template to invoke the api")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@Description("Wallet Tools")
	public WalletTools walletTools(WalletRepository walletRepository) {
		return new WalletTools(walletRepository);
	}

	@Bean
	@Description("Wallet Tools")
	public StockTools stockTools() {
		return new StockTools(restTemplate());
	}

}
