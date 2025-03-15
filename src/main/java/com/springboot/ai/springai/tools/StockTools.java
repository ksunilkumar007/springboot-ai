package com.springboot.ai.springai.tools;

import com.springboot.ai.springai.dto.DailyShareQuote;
import com.springboot.ai.springai.dto.StockResponse;
import com.springboot.ai.springai.model.DailyStockData;
import com.springboot.ai.springai.model.StockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class StockTools {
    private static Logger LOG = LoggerFactory.getLogger(StockTools.class);

    private RestTemplate restTemplate;

    @Value("${STOCK_API_KEY:none}")
    private String apiKey;

    public StockTools(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Tool(description = "Latest stock prices")
    public StockResponse getLatestStockPrices(@ToolParam(description = "Name of company") String company) {
        StockData stockData = restTemplate.getForObject("https://api.twelvedata.com/time_series?symbol={0}&interval=1min&outputsize=1&apikey={1}",
                StockData.class,
                company,
                apiKey);
        DailyStockData dailyStockData = stockData.getValues().get(0);
        LOG.info("Get stock prices: {} -> {}",company,dailyStockData.getClose());
        return new StockResponse(Float.parseFloat(dailyStockData.getClose()));
    }

    @Tool(description = "Historical stock prices")
    public List<DailyShareQuote> getHistoricalStockPrices(@ToolParam(description = "Search period in days") int days,
                                                         @ToolParam(description = "Name of company") String company) {
        StockData stockData = restTemplate.getForObject("https://api.twelvedata.com/time_series?symbol={0}&interval=1day&outputsize={1}&apikey={2}",
                StockData.class,
                company,
                apiKey);
        return stockData.getValues().stream()
                .map(d -> new DailyShareQuote(company,Float.parseFloat(d.getClose()), d.getDatetime()))
                .toList();
    }




}
