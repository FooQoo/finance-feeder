package com.fooqoo56.dev.financefeeder.exception.infrastructure.repository;

import com.fooqoo56.dev.financefeeder.exception.FinanceFeederException;

/**
 * 株価取得に失敗した場合の独自例外.
 */
public class FailedFetchStockPriceException extends FinanceFeederException {

    private static final long serialVersionUID = 8207167350057325986L;

    private static final String PREFIX = "[株価取得処理]";

    public FailedFetchStockPriceException(final String message, final Throwable exception) {
        super(PREFIX + message, exception);
    }
}
