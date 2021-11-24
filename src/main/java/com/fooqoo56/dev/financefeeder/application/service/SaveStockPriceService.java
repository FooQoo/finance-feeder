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

        // 株価指標が空の場合、フィード結果を0にして返す
        if (stockPrice.empty()) {
            return Mono.just(FeedResult.from(0));
        }

        // 株価指標が存在する場合、保存する
        return saveStockPriceRepository.saveStockPrice(stockPrice);
    }
}
