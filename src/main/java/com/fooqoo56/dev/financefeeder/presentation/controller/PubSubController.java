package com.fooqoo56.dev.financefeeder.presentation.controller;

import com.fooqoo56.dev.financefeeder.application.scenario.FeedStockPriceScenario;
import com.fooqoo56.dev.financefeeder.presentation.dto.form.PubSubBody;
import com.fooqoo56.dev.financefeeder.presentation.dto.response.PubSubResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PubSubController {

    private final FeedStockPriceScenario feedStockPriceScenario;

    @PostMapping(value = "/feedStock")
    public Mono<PubSubResponse> receiveMessage(@Valid @RequestBody final PubSubBody body) {

        log.info("更新開始:" + body.toStringMessage());

        // メッセージから証券コードを取得する
        final var securityCode = body.getSecurityCode();

        // シナリオを実行し、フィード結果を受け取る
        final var feedResultMono = feedStockPriceScenario.feedStockPrice(securityCode);

        // レスポンスに変換して返す
        return feedResultMono
                .map(PubSubResponse::from)
                .doOnSuccess(response -> log.info("更新終了:" + response));
    }
}
