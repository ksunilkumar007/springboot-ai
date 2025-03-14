package com.springboot.ai.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final ChatClient chatClient;

    public WalletController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping
    public String calculateWalletValue() {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Whats the current value in dollars of my wallet based on latest stock daily prices ?
                """);
        return this.chatClient.prompt(promptTemplate.create(OpenAiChatOptions.builder()
                        .function("numberOfShares") // private data
                        .function("latestStockPrices") //public info
                        .build()))
                .call()
                .content();

    }
}
