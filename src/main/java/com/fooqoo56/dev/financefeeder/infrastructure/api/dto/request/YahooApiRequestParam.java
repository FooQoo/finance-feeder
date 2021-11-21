package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.fooqoo56.dev.financefeeder.infrastructure.api.utils.UriParamBuilder;
import java.io.Serializable;
import java.net.URI;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.util.UriBuilder;

/**
 * Yahoo!FinanceAPIのリクエストパラメータ.
 *
 * @see <a href="https://github.com/pkout/yahoo_finance_api2/blob/master/yahoo_finance_api2/share.py#L87-L90" >yahoo_finance_api2</a>
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class YahooApiRequestParam implements Serializable {

    private static final long serialVersionUID = -3054477990107435428L;

    @NonNull
    private final String symbol;

    @NonNull
    private final String range;

    @NonNull
    private final String interval;

    @NonNull
    @Builder.Default
    private final Boolean includePrePost = true;

    @NonNull
    @Builder.Default
    private final String events = "div|split|earn";

    @NonNull
    @Builder.Default
    private final String lang = "en-US";

    @NonNull
    @Builder.Default
    private final String region = "US";

    @NonNull
    @Builder.Default
    private final String crumb = "t5QZMhgytYZ";

    @NonNull
    @Builder.Default
    private final String corsDomain = "finance.yahoo.com";

    /**
     * ファクトリメソッド.
     *
     * @param securityCode 証券コード
     * @param feedPeriod   フィード期間
     * @return YahooApiRequestParamを返す
     */
    public static YahooApiRequestParam of(final SecurityCode securityCode,
                                          final FeedPeriod feedPeriod) {
        return YahooApiRequestParam.builder()
                .range(feedPeriod.getRange().toString())
                .interval(feedPeriod.getInterval().toString())
                .symbol(securityCode.getTokyoStockExchangeCode())
                .build();
    }

    /**
     * URIの取得.
     *
     * @param uriBuilder uriBuilder
     * @return URI
     */
    public URI getURI(final UriBuilder uriBuilder) {

        return UriParamBuilder.builder()
                .addPathParam(symbol)
                .addQueryParam("range", range)
                .addQueryParam("interval", interval)
                .addQueryParam("includePrePost", includePrePost)
                .addQueryParam("events", events)
                .addQueryParam("lang", lang)
                .addQueryParam("region", region)
                .addQueryParam("crumb", crumb)
                .addQueryParam("corsDomain", corsDomain)
                .build(uriBuilder);
    }
}
