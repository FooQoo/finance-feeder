package com.fooqoo56.dev.financefeeder.application.scenario;

import com.fooqoo56.dev.financefeeder.application.service.FetchStockPriceService;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 株価をフィードするサービスクラス.
 */
@Component
@RequiredArgsConstructor
public class FeedStockPriceScenario {

    private final FetchStockPriceService fetchStockPrice;

    public void feedStockPrice() {
        // 1. 株価の取得
        final Mono<StockPrice> stockPriceMono = fetchStockPrice.fetchStockPrice(null);
    }

}
