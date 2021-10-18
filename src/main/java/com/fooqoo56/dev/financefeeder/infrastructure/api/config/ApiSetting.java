package com.fooqoo56.dev.financefeeder.infrastructure.api.config;

import java.io.Serializable;
import java.time.Duration;
import lombok.Data;

/**
 * API設定値.
 */
@Data
class ApiSetting implements Serializable {

    private static final long serialVersionUID = 1538644241366488498L;
    /**
     * URL.
     */
    private String baseDomain;

    /**
     * パス.
     */
    private String basePath;

    /**
     * 接続タイムアウト.
     */
    private Duration connectTimeout;

    /**
     * 読み込みタイムアウト.
     */
    private Duration readTimeout;

    /**
     * URLを返す.
     *
     * @return URL
     */
    String baseUrl() {
        return "https://" + baseDomain + basePath;
    }

    /**
     * 接続タイムアウトを整数型にして返す.
     *
     * @return 接続タイムアウト[ミリ秒]
     */
    Integer getConnectTimeoutMillsPart() {
        return connectTimeout.toMillisPart();
    }
}
