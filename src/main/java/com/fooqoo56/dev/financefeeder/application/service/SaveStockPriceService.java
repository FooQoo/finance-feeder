package com.fooqoo56.dev.financefeeder.application.service;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.SaveStockPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 株価を取得するサービスクラス.
 */
@Service
@RequiredArgsConstructor
public class SaveStockPriceService {

    private final SaveStockPriceRepository saveStockPriceRepository;

    /**
     * 株価を保存する.
     *
     * @param stockPrice StockPrice
     * @return 株価(Mono)
     */
    public Mono<FeedResult> saveStockPrice(final StockPrice stockPrice) {
        return saveStockPriceRepository.saveStockPrice(stockPrice);
    }
}
