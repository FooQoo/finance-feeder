package com.fooqoo56.dev.financefeeder.exception.domain.model;

import com.fooqoo56.dev.financefeeder.exception.FinanceFeederException;

/**
 * 株価取得に失敗した場合の独自例外.
 */
public class FailedBlockMonoException extends FinanceFeederException {

    private static final long serialVersionUID = 8207167350057325986L;

    public FailedBlockMonoException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public FailedBlockMonoException(final String message) {
        super(message);
    }
}
