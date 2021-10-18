package com.fooqoo56.dev.financefeeder.exception;

/**
 * アプリケーション例外.
 */
public abstract class FinanceFeederException extends RuntimeException {
    private static final long serialVersionUID = -8837177503744646859L;

    public FinanceFeederException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public FinanceFeederException(final String message) {
        super(message);
    }
}
