package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl.yahoo;

import static com.fooqoo56.dev.financefeeder.infrastructure.api.config.WebClientConfig.CONNECTOR;
import static com.fooqoo56.dev.financefeeder.infrastructure.api.config.WebClientConfig.WEBCLIENT_REQUEST_LOGGER;
import static com.fooqoo56.dev.financefeeder.infrastructure.api.config.WebClientConfig.WEBCLIENT_RESPONSE_LOGGER;


import java.nio.charset.StandardCharsets;
import java.time.Duration;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * フィード設定.
 */
@ConfigurationProperties(prefix = "extension.api.yahoo-finance-chart")
@Validated
@ConstructorBinding
@RequiredArgsConstructor
public class YahooFinanceApiConfig {

    /**
     * URL.
     */
    @NotNull
    private final String baseDomain;

    /**
     * パス.
     */
    @NotNull
    private final String basePath;

    /**
     * 接続タイムアウト.
     */
    @NotNull
    private final Duration connectTimeout;

    /**
     * 読み込みタイムアウト.
     */
    @NotNull
    private final Duration readTimeout;

    /**
     * URLを返す.
     *
     * @return URL
     */
    private String baseUrl() {
        return "https://" + baseDomain + basePath;
    }

    /**
     * 接続タイムアウトを整数型にして返す.
     *
     * @return 接続タイムアウト[ミリ秒]
     */
    private Integer getConnectTimeoutMillsPart() {
        return connectTimeout.toMillisPart();
    }

    /**
     * YahooFinanceのクライアント.
     *
     * @param builder WebClient.Builder
     * @return WebClient
     */
    @Bean
    public WebClient yahooFinanceApiClient(final WebClient.Builder builder) {

        final var connector = CONNECTOR
                .apply(getConnectTimeoutMillsPart(), readTimeout);

        return builder
                .baseUrl(baseUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(WEBCLIENT_REQUEST_LOGGER)
                .filter(WEBCLIENT_RESPONSE_LOGGER)
                .clientConnector(connector)
                .build();
    }
}
