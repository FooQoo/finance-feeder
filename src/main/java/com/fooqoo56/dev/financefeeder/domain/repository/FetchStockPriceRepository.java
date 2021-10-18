package com.fooqoo56.dev.financefeeder.domain.repository;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import reactor.core.publisher.Mono;

/**
 * 株価を取得するRepositoryのinterface.
 */
public interface FetchStockPriceRepository {

    /**
     * 株価を取得する.
     *
     * @param securityCode 証券コード
     * @return 株価(Mono)
     */
    Mono<StockPrice> fetchStockPrice(SecurityCode securityCode, FeedPeriod feedPeriod);
}
