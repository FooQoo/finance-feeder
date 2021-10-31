package com.fooqoo56.dev.financefeeder.exception.infrastructure.dto;

import com.fooqoo56.dev.financefeeder.exception.FinanceFeederException;

/**
 * 配列が空の場合の例外.
 */
public class EmptyArrayException extends FinanceFeederException {

    private static final long serialVersionUID = 3829714866420762792L;

    public EmptyArrayException(final String message) {
        super(message);
    }
}
