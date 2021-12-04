package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

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

    /**
     * 先頭インデックスのみ利用
     */
    @JsonProperty("quote")
    @NotNull
    private final List<Quote> quotes;

    /**
     * 先頭インデックスのみ利用
     */
    @JsonProperty("adjclose")
    @NotNull
    private final List<AdjClose> adjCloses;

    /**
     * StockPriceIndexのリストに変換する
     *
     * @return StockPriceIndex、quotesと
     */
    List<StockPriceIndex> toStockPriceIndexList() {

        if (CollectionUtils.isEmpty(quotes)) {
            log.warn("quotesが空です。");
            return List.of();
        }

        if (CollectionUtils.isEmpty(adjCloses)) {
            log.warn("adjcloseが空です。");
            return List.of();
        }

        final var quote = quotes.get(ZERO_INDEX);
        final var adjClose = adjCloses.get(ZERO_INDEX);

        if (quote.length() != adjClose.length()) {
            log.warn("quotesとadjcloseの長さが一致しません。");
            return List.of();
        }

        final var sizeQuote = quote.length();

        return IntStream.range(0, sizeQuote)
                .mapToObj(index -> {

                    final var high = quote.getHigh(index);
                    final var low = quote.getLow(index);
                    final var open = quote.getOpen(index);
                    final var close = quote.getClose(index);
                    final var adjCloseValue = adjClose.getAdjCloses(index);
                    final var volume = quote.getVolume(index);

                    if (!(high.isPresent() && low.isPresent() && open.isPresent() &&
                            close.isPresent() &&
                            adjCloseValue.isPresent() && volume.isPresent())) {
                        return Optional.empty();
                    }
                    return Optional.of(StockPriceIndex.builder()
                            .high(high.get())
                            .low(low.get())
                            .open(open.get())
                            .close(close.get())
                            .adjClose(adjCloseValue.get())
                            .volume(volume.get())
                            .build());
                })
                .filter(Optional::isPresent)
                .map(stockPriceIndex -> (StockPriceIndex) stockPriceIndex.get())
                .collect(Collectors.toUnmodifiableList());
    }
}
