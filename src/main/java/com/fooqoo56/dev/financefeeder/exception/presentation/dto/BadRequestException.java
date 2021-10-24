package com.fooqoo56.dev.financefeeder.exception.presentation.dto;

import com.fooqoo56.dev.financefeeder.exception.FinanceFeederException;

/**
 * アプリケーション例外.
 */
public class BadRequestException extends FinanceFeederException {
    private static final long serialVersionUID = -8837177503744646859L;

    public BadRequestException(final String message, final Throwable exception) {
        super(message, exception);
    }
}
