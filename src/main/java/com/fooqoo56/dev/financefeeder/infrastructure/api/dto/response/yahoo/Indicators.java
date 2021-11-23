package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import com.fooqoo56.dev.financefeeder.exception.infrastructure.dto.EmptyArrayException;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode
@Slf4j
class Indicators implements Serializable {

    private static final long serialVersionUID = -773072678203335505L;

    private static final int ZERO_INDEX = 0;

    @JsonProperty("quote")
    @NonNull
    private final List<Quote> quotes;

    @JsonProperty("adjclose")
    @NonNull
    private final List<AdjClose> adjCloses;

    /**
     * StockPriceIndexに変換する.
     *
     * @param index インデックス
     * @return StockPriceIndex
     */
    StockPriceIndex toStockPriceIndex(final int index) {

        if (CollectionUtils.isEmpty(quotes)) {
            throw new EmptyArrayException("quotesが空です。");
        }

        if (CollectionUtils.isEmpty(adjCloses)) {
            throw new EmptyArrayException("adjcloseが空です。");
        }

        final var quote = quotes.get(ZERO_INDEX);
        final var adjClose = adjCloses.get(ZERO_INDEX);

        return StockPriceIndex.builderOf()
                .high(quote.getHigh(index))
                .low(quote.getLow(index))
                .open(quote.getOpen(index))
                .close(quote.getClose(index))
                .adjClose(adjClose.getAdjCloses(index))
                .volume(quote.getVolume(index))
                .build();
    }
}