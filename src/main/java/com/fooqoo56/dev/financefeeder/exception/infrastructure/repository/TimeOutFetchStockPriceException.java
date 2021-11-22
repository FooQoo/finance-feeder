package com.fooqoo56.dev.financefeeder.exception.infrastructure.repository;

/**
 * 株価取得に失敗した場合の独自例外.
 */
public class TimeOutFetchStockPriceException extends FailedFetchStockPriceException {

    private static final long serialVersionUID = 8207167350057325986L;

    private static final String PREFIX = "[読込タイムアウト]";

    public TimeOutFetchStockPriceException(final String message, final Throwable exception) {
        super(PREFIX + message, exception);
    }
}
