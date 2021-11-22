package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl.yahoo;

import com.fooqoo56.dev.financefeeder.exception.infrastructure.repository.FailedFetchStockPriceException;
import com.fooqoo56.dev.financefeeder.exception.infrastructure.repository.TimeOutFetchStockPriceException;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request.yahoo.YahooApiRequestParam;
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo.YahooApiResponse;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Yahoo!FinanceのAPIのRepository.
 */
@Component
@RequiredArgsConstructor
public class YahooFinanceApiRepository {

    private final WebClient yahooFinanceApiClient;

    /**
     * chart情報を取得する
     *
     * @param yahooApiRequestParam ヤフーAPIのリクエストパラメータ
     * @return YahooApiのレスポンス
     */
    public Mono<YahooApiResponse> getChart(final YahooApiRequestParam yahooApiRequestParam) {
        return yahooFinanceApiClient.get()
                .uri(yahooApiRequestParam::getURI)
                .retrieve()
                .bodyToMono(YahooApiResponse.class)
                .onErrorResume(exception -> {
                    if (exception instanceof ReadTimeoutException) {
                        return Mono.error(
                                new TimeOutFetchStockPriceException(
                                        "株価取得データの読み込み時にタイムアウトが発生しました。",
                                        exception));
                    }

                    return Mono.error(
                            new FailedFetchStockPriceException("株価の取得に失敗しました。", exception));
                });
    }
}
