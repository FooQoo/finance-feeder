package com.fooqoo56.dev.financefeeder.application.scenario;

import com.fooqoo56.dev.financefeeder.application.service.FetchStockPriceService;
import com.fooqoo56.dev.financefeeder.application.service.SaveStockPriceService;
import com.fooqoo56.dev.financefeeder.domain.model.asynchronous.MonoHandler;
import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
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

    private final SaveStockPriceService saveStockPrice;

    /**
     * 株価をフィードするシナリオ.
     *
     * @param securityCode 証券コード
     */
    public void feedStockPrice(final SecurityCode securityCode) {
        // 1. 株価の取得
        final Mono<StockPrice> stockPriceMono = fetchStockPrice.fetchStockPrice(securityCode);

        // 2. 株価の保存
        final Mono<FeedResult> feedResultMono = stockPriceMono
                .flatMap(saveStockPrice::saveStockPrice);

        // 3. 非同期処理のブロック
        final var monoHandler = new MonoHandler<>(feedResultMono);
        final var feedResult = monoHandler.block();

        // 4. 株価結果のログ出力
        feedResult.logFeedResult();
    }

}
