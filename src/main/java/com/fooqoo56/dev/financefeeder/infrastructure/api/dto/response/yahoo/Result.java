package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.DailyIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@ToString
@EqualsAndHashCode
class Result implements Serializable {

    private static final long serialVersionUID = 1841671527691933050L;

    @JsonProperty("meta")
    @NonNull
    private final Meta meta;

    @JsonProperty("timestamp")
    @NonNull
    private final List<Integer> timestamps;

    @JsonProperty("indicators")
    @NonNull
    private final Indicators indicators;

    StockPrice toStockPrice() {
        final var sizeOfTimestamp = CollectionUtils.size(timestamps);

        final var dailyIndices = IntStream
                .range(0, sizeOfTimestamp)
                .mapToObj(this::buildDailyIndex)
                .collect(Collectors.toUnmodifiableList());

        return StockPrice.builder()
                .dailyIndices(dailyIndices)
                .securityCode(meta.getSecurityCode())
                .build();
    }

    private DailyIndex buildDailyIndex(final int index) {
        final var historyDate = HistoryDate.from(timestamps.get(index));
        final var stockPriceIndex = indicators.toStockPriceIndex(index);

        return DailyIndex.builder()
                .stockPriceIndex(stockPriceIndex)
                .historyDate(historyDate)
                .build();
    }
}
