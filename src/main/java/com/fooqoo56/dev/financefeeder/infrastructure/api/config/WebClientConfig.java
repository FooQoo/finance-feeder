package com.fooqoo56.dev.financefeeder.infrastructure.api.config;

import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import java.time.Duration;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

/**
 * WebFluxの設定クラス
 */
@Slf4j
public class WebClientConfig {

    /**
     * connector.
     */
    public static final BiFunction<Integer, Duration, ReactorClientHttpConnector> CONNECTOR =
            (connectTimeout, readTimeout) -> new ReactorClientHttpConnector(HttpClient.create()
                    .runOn(LoopResources.create("reactor-webclient"))
                    .responseTimeout(readTimeout)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                    .resolver(DefaultAddressResolverGroup.INSTANCE)
                    .compress(true)
            );

    /**
     * リクエスト情報出力用.
     */
    public static final ExchangeFilterFunction WEBCLIENT_REQUEST_LOGGER = ExchangeFilterFunction
            .ofRequestProcessor(
                    clientRequest -> {
                        log.info("{}[{}] {}",
                                clientRequest.logPrefix(), clientRequest.method(),
                                clientRequest.url());

                        final var existHeaders = !clientRequest.headers().isEmpty();

                        if (existHeaders) {

                            final var headerRemovedCookie =
                                    clientRequest.headers().keySet().stream()
                                            .filter(key -> !HttpHeaders.COOKIE.equals(key))
                                            .map(key -> key + "="
                                                    + clientRequest.headers().getFirst(key))
                                            .collect(Collectors.joining(","));

                            final var method = clientRequest.method().toString();

                            log.info(
                                    "{}[{}] Request Headers: {{}}", clientRequest.logPrefix(),
                                    method,
                                    headerRemovedCookie

                            );
                        }
                        return Mono.just(clientRequest);
                    }
            );

    /**
     * レスポンス情報出力用.
     */
    public static final ExchangeFilterFunction WEBCLIENT_RESPONSE_LOGGER = ExchangeFilterFunction
            .ofResponseProcessor(
                    clientResponse -> {
                        log.info("{}[{}] {}",
                                clientResponse.logPrefix(), clientResponse.statusCode().value(),
                                clientResponse.headers().asHttpHeaders());
                        return Mono.just(clientResponse);
                    }
            );
}