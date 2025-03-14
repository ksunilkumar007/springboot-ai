package com.springboot.ai.springai;

import com.springboot.ai.springai.dto.request.StockRequest;
import com.springboot.ai.springai.dto.response.StockResponse;
import com.springboot.ai.springai.dto.response.WalletResponse;
import com.springboot.ai.springai.repository.WalletRepository;
import com.springboot.ai.springai.service.StockService;
import com.springboot.ai.springai.service.WalletService;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;
import java.util.function.Supplier;


@SpringBootApplication
public class SpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
	}
	@Bean
	InMemoryChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

	@Bean
	@Description("Number of shares for each company in my portfolio")
	public Supplier<WalletResponse> numberOfShares (WalletRepository walletRepository){
		return new WalletService(walletRepository);
	}

	@Bean
	@Description("latest stock prices")
	public Function<StockRequest, StockResponse> latestStockPrices (){
		return new StockService();
	}

	@Bean
	@Description("A Rest Template to invoke the api")
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
