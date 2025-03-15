package com.springboot.ai.springai.controller;

import com.springboot.ai.springai.tools.StockTools;
import com.springboot.ai.springai.tools.WalletTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final ChatClient chatClient;
    private final StockTools stockTools;
    private final WalletTools walletTools;

    public WalletController(ChatClient.Builder chatClientBuilder,
                            StockTools stockTools,
                            WalletTools walletTools) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.stockTools = stockTools;
        this.walletTools = walletTools;
    }

    @GetMapping("/with-tools")
    public String calculateWalletValueWithTools() {
        PromptTemplate promptTemplate = new PromptTemplate("""
                Whats the current value in dollars of my wallet based on latest stock daily prices ?
                """);
        return this.chatClient.prompt(promptTemplate.create())
                .tools(stockTools,walletTools)
                .call()
                .content();

    }

    @GetMapping("/highest-day/{days}")
    public String calculateHighestWalletValue(@PathVariable int days) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                On which day during last {days} days my wallet had the highest value in dollars based on the historical daily stock prices ?
                """);
        return this.chatClient.prompt(promptTemplate.create(Map.of("days",days)))
                .tools(stockTools,walletTools)
                .call()
                .content();

    }
}
