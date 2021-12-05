package com.fooqoo56.dev.financefeeder.presentation.dto.response.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Getter
public enum ErrorResponsePattern {
    NOT_FOUND(
            HttpStatus.NOT_FOUND,
            PubSubErrorResponse.builder()
                    .title("存在しないエンドポイントにアクセスされました")
                    .build()),
    METHOD_NOT_ARROWED(
            HttpStatus.METHOD_NOT_ALLOWED,
            PubSubErrorResponse.builder()
                    .title("許可されないHTTPメソッドでアクセスされました")
                    .build()),
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            PubSubErrorResponse.builder()
                    .title("サーバ内部でエラーが発生しました")
                    .build()),
    BAD_REQUEST(
            HttpStatus.BAD_REQUEST,
            PubSubErrorResponse.builder()
                    .title("不正なパラメータが入力されました")
                    .build());;

    @NonNull
    private final HttpStatus httpStatus;

    @NonNull
    private final PubSubErrorResponse errorResponse;
}
