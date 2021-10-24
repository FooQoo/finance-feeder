package com.fooqoo56.dev.financefeeder.domain.repository;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import reactor.core.publisher.Mono;

/**
 * 株価を保存するためのRepository.
 */
public interface SaveStockPriceRepository {

    Mono<FeedResult> saveStockPrice(StockPrice stockPrice);
}
