package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPriceIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@EqualsAndHashCode
class Result implements Serializable {

    private static final long serialVersionUID = 1841671527691933050L;

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("timestamp")
    private List<Integer> timestamps;

    @JsonProperty("indicators")
    private Indicators indicators;

    StockPrice toStockPrice() {
        final var sizeOfTimestamp = CollectionUtils.size(timestamps);

        final var stockPriceIndexMap = IntStream
                .range(0, sizeOfTimestamp)
                .mapToObj(this::getPair)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return StockPrice.of(meta.getSecurityCode(), stockPriceIndexMap);
    }

    private Pair<HistoryDate, StockPriceIndex> getPair(final int index) {
        final var historyDate = HistoryDate.from(timestamps.get(index));
        final var stockPriceIndex = indicators.toStockPriceIndex(index);

        return Pair.of(historyDate, stockPriceIndex);
    }
}
