package com.fooqoo56.dev.financefeeder.exception.infrastructure.repository;

import com.fooqoo56.dev.financefeeder.exception.FinanceFeederException;

/**
 * 株価保存に失敗した場合の独自例外.
 */
public class FailedSaveStockPriceException extends FinanceFeederException {

    private static final long serialVersionUID = 8207167350057325986L;

    private static final String PREFIX = "[株価保存]";

    public FailedSaveStockPriceException(final String message, final Throwable exception) {
        super(PREFIX + message, exception);
    }
}
