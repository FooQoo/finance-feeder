package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.FetchStockPriceRepository;
import com.fooqoo56.dev.financefeeder.exception.infrastructure.FailedFetchStockPriceException;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request.YahooApiRequestParam;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi.YahooApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Yahoo!FinanceのAPIのRepository.
 */
@Repository
@RequiredArgsConstructor
public class YahooFinanceApiRepository implements FetchStockPriceRepository {

    private final WebClient yahooFinanceApiClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<StockPrice> fetchStockPrice(
            final SecurityCode securityCode, final FeedPeriod feedPeriod) {

        final var yahooApiRequestParam = YahooApiRequestParam.of(securityCode, feedPeriod);

        final Mono<YahooApiResponse> yahooApiResponseMono = yahooFinanceApiClient.get()
                .uri(yahooApiRequestParam::getURI)
                .retrieve()
                .bodyToMono(YahooApiResponse.class)
                .log()
                .onErrorResume(exception -> Mono.error(
                        new FailedFetchStockPriceException("株価の取得に失敗しました:", exception)));

        return yahooApiResponseMono.map(YahooApiResponse::toStockPrice);
    }
}
