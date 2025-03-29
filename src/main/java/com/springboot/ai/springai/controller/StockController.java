package com.springboot.ai.springai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.ai.springai.model.DailyStockData;
import com.springboot.ai.springai.model.Stock;
import com.springboot.ai.springai.model.StockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger LOG = LoggerFactory.getLogger(StockController.class);
    private final RestTemplate restTemplate;
    private final VectorStore vectorStore;

    @Value("${STOCK_API_KEY}")
    private String apiKey;

    public StockController(VectorStore vectorStore, RestTemplate restTemplate) {
        this.vectorStore = vectorStore;
        this.restTemplate = restTemplate;
    }

    @GetMapping ("/load_data")
     void loadData() throws JsonProcessingException {
        final List<String> companies = List.of("APPL","MSFT","GOOG","AMZN", "META", "NVDA");
        for (String company : companies) {
            StockData stockData = restTemplate.getForObject("https://api.twelvedata.com/time_series?symbol={0}&interval=1day&outputsize=10&apikey={1}",
                    StockData.class,
                    company,
                    apiKey);
            if(stockData != null && stockData.getValues() != null) {
                var list = stockData.getValues().stream().map(DailyStockData::getClose).toList();
                var doc = Document.builder()
                        .id(company)
                        .text(objectMapper.writeValueAsString(new Stock(company,list)))
                        .build();
                vectorStore.add(List.of(doc));
                LOG.info("Document added to vectorstore : {}",company);
            }
        }
    }

    @GetMapping("/docs")
     List<Document> queryDocs() {
        SearchRequest searchRequest = SearchRequest.builder()
                .query("Find the most growth trends")
                .topK(2)
                .build();
        List<Document> docs = vectorStore.similaritySearch(searchRequest);
        return docs;
    }
}
