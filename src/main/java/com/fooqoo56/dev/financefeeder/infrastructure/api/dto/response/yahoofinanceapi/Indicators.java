package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPriceIndex;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@EqualsAndHashCode
class Indicators implements Serializable {

    private static final long serialVersionUID = -773072678203335505L;

    @JsonProperty("quote")
    private List<Quote> quotes;

    @JsonProperty("adjclose")
    private List<AdjClose> adjCloses;

    List<StockPriceIndex> toStockPriceIndexList() {
        final var quote = quotes.stream().findFirst().orElseThrow();
        final var adjClose = adjCloses.stream().findFirst().orElseThrow();

        // quoteのサイズ基準にListを作成する
        final var quoteSize = quote.size();

        return IntStream
                .range(0, quoteSize - 1)
                .mapToObj(index -> StockPriceIndex.of(
                        quote.getHigh(index),
                        quote.getLow(index),
                        quote.getOpen(index),
                        quote.getClose(index),
                        adjClose.getAdjCloses(index),
                        quote.getVolume(index)
                )).collect(Collectors.toUnmodifiableList());
    }
}
