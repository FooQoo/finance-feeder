package com.fooqoo56.dev.financefeeder.application.service;

import com.fooqoo56.dev.financefeeder.config.FeederSetting;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.FetchStockPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 株価を取得するサービスクラス.
 */
@Service
@RequiredArgsConstructor
public class FetchStockPriceService {

    private final FetchStockPriceRepository fetchStockPriceRepository;

    private final FeederSetting feederSetting;

    /**
     * 株価を取得する.
     *
     * @param securityCode 証券コード
     * @return 株価(Mono)
     */
    public Flux<StockPrice> fetchStockPrice(final SecurityCode securityCode) {
        final var feedPeriod = feederSetting.toFeedPeriod();

        return fetchStockPriceRepository.fetchStockPrice(securityCode, feedPeriod);
    }
}
