package com.fooqoo56.dev.financefeeder.presentation.advice;

import com.fooqoo56.dev.financefeeder.presentation.controller.PubSubController;
import com.fooqoo56.dev.financefeeder.presentation.dto.response.error.ErrorResponsePattern;
import com.fooqoo56.dev.financefeeder.presentation.dto.response.error.PubSubErrorResponse;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * RESTAPIで発生した例外ハンドリングクラス
 */
@RestControllerAdvice(basePackageClasses = PubSubController.class)
@Slf4j
public class RestExceptionHandler {

    /**
     * 入力エラー
     *
     * @param exchange  ServerWebExchange
     * @param exception ValidationException
     * @return エラーレスポンス
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Mono<PubSubErrorResponse> handleValidationException(final ServerWebExchange exchange,
                                                               final ValidationException exception) {
        log.error("[入力エラー]" + exception.getMessage());

        setContentType(exchange);

        return Mono.just(ErrorResponsePattern.BAD_REQUEST.getErrorResponse());
    }

    /**
     * その他のエラー
     *
     * @param exchange  ServerWebExchange
     * @param exception Throwable
     * @return エラーレスポンス
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Mono<PubSubErrorResponse> handleException(final ServerWebExchange exchange,
                                                     final Throwable exception) {
        log.error("[不明なエラー]" + exception.getMessage());

        setContentType(exchange);

        return Mono.just(ErrorResponsePattern.INTERNAL_SERVER_ERROR.getErrorResponse());
    }

    /**
     * ContentTypeをセットする
     *
     * @param exchange ServerWebExchange
     */
    private void setContentType(final ServerWebExchange exchange) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);
    }

}
