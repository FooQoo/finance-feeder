package com.fooqoo56.dev.financefeeder.application.scenario;

import com.fooqoo56.dev.financefeeder.application.service.FetchStockPriceService;
import com.fooqoo56.dev.financefeeder.application.service.SaveStockPriceService;
import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.util.List;
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
    public Mono<List<FeedResult>> feedStockPrice(final SecurityCode securityCode) {
        // 1. 株価の取得
        final var stockPriceFlux = fetchStockPrice.fetchStockPrice(securityCode);

        // 2. 株価の保存
        final var feedResultFlux = stockPriceFlux
                .flatMap(saveStockPrice::saveStockPrice);

        // 3. 保存結果のリスト化
        return feedResultFlux.collectList();
    }
}
