package com.fooqoo56.dev.financefeeder.exception;

/**
 * 値オブジェクトの引数が不正な場合の例外.
 */
public class InvalidTypeParamException extends FinanceFeederException {

    private static final long serialVersionUID = -6010946415348043282L;

    public InvalidTypeParamException(final String message, final Throwable exception) {
        super(message, exception);
    }

    public InvalidTypeParamException(final String message) {
        super(message);
    }
}
