package com.springboot.ai.springai.service;

import com.springboot.ai.springai.dto.request.StockRequest;
import com.springboot.ai.springai.dto.response.StockResponse;
import com.springboot.ai.springai.model.DailyStockData;
import com.springboot.ai.springai.model.StockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@Service
public class StockService implements Function<StockRequest, StockResponse> {

    private static final Logger LOG = LoggerFactory.getLogger(StockService.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${STOCK_API_KEY}")
    private String apiKey;

    @Override
    public StockResponse apply(StockRequest stockRequest) {
        StockData stockData = restTemplate.getForObject("https://api.twelvedata.com/time_series?symbol={0}&interval=1min&outputsize=1&apikey={1}",
                StockData.class,
                stockRequest.companyName(),
                apiKey);
        DailyStockData dailyStockData = stockData.getValues().get(0);
        LOG.info("Get Stock prices: {} -> {}", stockRequest.companyName(), dailyStockData.getClose());

        return new StockResponse(Float.parseFloat(dailyStockData.getClose()));
    }

}
