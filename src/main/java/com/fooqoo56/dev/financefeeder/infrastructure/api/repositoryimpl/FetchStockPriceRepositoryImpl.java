package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.FetchStockPriceRepository;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request.yahoo.YahooApiRequestParam;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo.YahooApiResponse;
import com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl.yahoo.YahooFinanceApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Repository
public class FetchStockPriceRepositoryImpl implements FetchStockPriceRepository {

    private final YahooFinanceApiRepository yahooFinanceApiRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<StockPrice> fetchStockPrice(
            final SecurityCode securityCode,
            final FeedPeriod feedPeriod) {

        return yahooFinanceApiRepository.getChart(
                        YahooApiRequestParam.of(securityCode, feedPeriod))
                .map(YahooApiResponse::toStockPrice)
                .flatMapMany(Flux::fromIterable);
    }
}
