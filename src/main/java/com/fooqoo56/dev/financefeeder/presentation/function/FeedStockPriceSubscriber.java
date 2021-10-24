package com.fooqoo56.dev.financefeeder.presentation.function;

import com.fooqoo56.dev.financefeeder.application.scenario.FeedStockPriceScenario;
import com.fooqoo56.dev.financefeeder.presentation.dto.PubSubMessage;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * 株価をフィードするMQのsubscriber.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FeedStockPriceSubscriber {

    private final FeedStockPriceScenario feedStockPriceScenario;

    /**
     * pubsubでメッセージを受け取る関数.
     *
     * @return PubSubMessageを引数に持つ関数
     */
    @Bean
    @NonNull
    public Consumer<PubSubMessage> pubSubFunction() {
        return message -> {
            log.info("更新開始");

            final var messageData = message.getMessageData();

            final var securityCode = messageData.getSecurityCode();

            feedStockPriceScenario.feedStockPrice(securityCode);

            log.info("更新終了");
        };
    }
}
