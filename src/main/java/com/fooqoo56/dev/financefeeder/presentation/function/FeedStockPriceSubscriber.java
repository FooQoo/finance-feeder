package com.fooqoo56.dev.financefeeder.presentation.function;

import com.fooqoo56.dev.financefeeder.presentation.dto.PubSubMessage;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;

/**
 * 株価をフィードするMQのsubscriber.
 */
@Slf4j
@RequiredArgsConstructor
public class FeedStockPriceSubscriber {

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

            log.info("更新終了");
        };
    }
}
