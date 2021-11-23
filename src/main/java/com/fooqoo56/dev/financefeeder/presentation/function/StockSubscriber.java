package com.fooqoo56.dev.financefeeder.presentation.function;

import com.fooqoo56.dev.financefeeder.application.scenario.FeedStockPriceScenario;
import com.fooqoo56.dev.financefeeder.presentation.dto.form.PubSubMessage;
import com.fooqoo56.dev.financefeeder.presentation.dto.response.PubSubResponse;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 株価をフィードするPub/Subのsubscriber.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockSubscriber {

    private final FeedStockPriceScenario feedStockPriceScenario;

    /**
     * pubsubでメッセージを受け取る関数.
     *
     * @return PubSubMessageを引数に持つ関数
     */
    @Bean
    public Function<PubSubMessage, PubSubResponse> feedStock() {
        return message -> {
            log.info("更新開始:" + message);

            // メッセージから証券コードを取得する
            final var securityCode = message.getMessageData().getSecurityCode();

            // シナリオを実行し、フィード結果を受け取る
            final var feedResult = feedStockPriceScenario.feedStockPrice(securityCode);

            // フィード結果をメッセージに詰めて返す
            final var response = PubSubResponse.from("numOfUpdated:" + feedResult);

            log.info("更新終了" + response);

            return response;
        };
    }
}
