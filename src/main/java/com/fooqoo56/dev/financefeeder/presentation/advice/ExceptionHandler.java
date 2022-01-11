package com.fooqoo56.dev.financefeeder.presentation.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooqoo56.dev.financefeeder.presentation.dto.response.error.ErrorResponsePattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandler implements WebExceptionHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @NotNull
    @Override
    public Mono<Void> handle(final ServerWebExchange exchange,
                             @NotNull final Throwable exception) {

        final var errorResponse = getErrorResponse(exception);

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        try {
            final DataBuffer db = new DefaultDataBufferFactory()
                    .wrap(OBJECT_MAPPER.writeValueAsBytes(errorResponse.getErrorResponse()));

            exchange.getResponse().setStatusCode(errorResponse.getHttpStatus());

            log.error("{} {}", exception.getMessage(), exception.getStackTrace());
            return exchange.getResponse().writeWith(Mono.just(db));
        } catch (final JsonProcessingException jsonProcessingException) {
            log.error("{} {}", exchange.getLogPrefix(), jsonProcessingException.getMessage(),
                    jsonProcessingException);
        }

        return Mono.error(exception);
    }

    /**
     * エラーレスポンス取得
     *
     * @param exception 例外
     * @return エラーレスポンスのPair
     */
    @NonNull
    private static ErrorResponsePattern getErrorResponse(
            final Throwable exception) {

        if (exception instanceof MethodNotAllowedException) {
            return ErrorResponsePattern.METHOD_NOT_ARROWED;
        }

        if (exception instanceof ResponseStatusException
                && ((ResponseStatusException) exception).getStatus() == HttpStatus.NOT_FOUND) {
            return ErrorResponsePattern.NOT_FOUND;
        }

        return ErrorResponsePattern.INTERNAL_SERVER_ERROR;
    }
}
