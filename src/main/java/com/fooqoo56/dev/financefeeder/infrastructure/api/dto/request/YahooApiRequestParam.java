package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.io.Serializable;
import java.net.URI;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
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

    private final String symbol;

    private final String range;

    private final String interval;

    private final Boolean includePrePost = true;

    private final String events = "div|split|earn";

    private final String lang = "en-US";

    private final String region = "US";

    private final String crumb = "t5QZMhgytYZ";

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
                .range(feedPeriod.getRangeParam())
                .interval(feedPeriod.getIntervalParam())
                .symbol(securityCode.getTokyoStockExchangeCode())
                .build();
    }

    /**
     * URIの取得.
     *
     * @param uriBuilder URIBuilder
     * @return URI
     */
    public URI getURI(final UriBuilder uriBuilder) {
        return uriBuilder
                .path("/{}")
                .queryParam("range", "{range}")
                .queryParam("interval", "{interval}")
                .queryParam("includePrePost", "{includePrePost}")
                .queryParam("events", "{events}")
                .queryParam("region", "{region}")
                .queryParam("crumb", "{crumb}")
                .queryParam("corsDomain", "{corsDomain}")
                .build(symbol, range, interval, includePrePost, events, region, crumb, corsDomain);
    }
}
