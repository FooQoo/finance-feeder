package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.web.util.UriBuilder;

/**
 * 編集不可なUriBuilder.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class UriParamBuilder {

    @NonNull
    private final List<UriPathParam> pathParams;

    @NonNull
    private final List<UriQueryParam> queryParams;

    /**
     * 空のUriParamBuilderを返す
     *
     * @return オブジェクト
     */
    public static UriParamBuilder builder() {
        return new UriParamBuilder(List.of(), List.of());
    }

    /**
     * クエリパラメータを追加する
     *
     * @param key   クエリパラメータのキー
     * @param value クエリパラメータの値
     * @return オブジェクト
     */
    public UriParamBuilder addQueryParam(final String key, final Object value) {

        final var uriQueryParam = UriQueryParam.builder()
                .key(key)
                .value(value)
                .build();

        // 新規でパラメータのリストを作成する
        final var newParamList = new ArrayList<>(queryParams);
        newParamList.add(uriQueryParam);

        // インスタンスを再生成して返す
        return new UriParamBuilder(
                List.copyOf(pathParams),
                Collections.unmodifiableList(newParamList));
    }

    /**
     * パスパラメータを追加
     *
     * @param value 値
     * @return オブジェクト
     */
    public UriParamBuilder addPathParam(final Object value) {
        final var uriPathParam = UriPathParam.builder()
                .value(value)
                .build();

        // 新規でパラメータのリストを作成する
        final var newParamList = new ArrayList<>(pathParams);
        newParamList.add(uriPathParam);

        // インスタンスを再生成して返す
        return new UriParamBuilder(
                Collections.unmodifiableList(newParamList),
                List.copyOf(queryParams));
    }

    /**
     * URIを取得する、値の編集は実施しない
     *
     * @param uriBuilder uriBuilder
     * @return URI
     */
    public URI build(final UriBuilder uriBuilder) {

        // パスパラメータを設定する
        pathParams.forEach(uriPathParam -> uriPathParam.addPath(uriBuilder));
        // クエリパラメータを設定する
        queryParams.forEach(uriQueryParam -> uriQueryParam.addQueryParam(uriBuilder));

        return uriBuilder.build();
    }

    /**
     * パスパラメータの格納クラス
     */
    @Builder
    @ToString
    @Getter
    private static class UriPathParam {

        @NonNull
        private final Object value;

        /**
         * パス情報を追加する
         *
         * @param uriBuilder パス情報を追加するuriBuilder
         */
        private void addPath(final UriBuilder uriBuilder) {
            uriBuilder.path(String.format("/%s", value));
        }
    }

    /**
     * クエリパラメータの格納クラス
     */
    @Builder
    @ToString
    @Getter
    private static class UriQueryParam {

        @NonNull
        private final String key;

        @NonNull
        private final Object value;

        /**
         * クエリパラメータ情報を追加する
         *
         * @param uriBuilder クエリパラメータ情報を追加するuriBuilder
         */
        private void addQueryParam(final UriBuilder uriBuilder) {
            uriBuilder.queryParam(key, value);
        }
    }
}
