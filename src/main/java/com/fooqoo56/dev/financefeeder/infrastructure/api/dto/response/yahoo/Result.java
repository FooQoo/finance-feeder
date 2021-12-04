package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.DailyIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
class Result implements Serializable {

    private static final long serialVersionUID = 1841671527691933050L;

    @JsonProperty("meta")
    private final Meta meta;

    @JsonProperty("timestamp")
    private final List<Integer> timestamps;

    @JsonProperty("indicators")
    private final Indicators indicators;

    /**
     * StockPriceに変換して返す
     *
     * @return StockPrice
     */
    StockPrice toStockPrice() {
        final var sizeOfTimestamp = CollectionUtils.size(timestamps);

        final var stockPriceIndexList = Optional.ofNullable(indicators)
                .map(Indicators::toStockPriceIndexList)
                .orElse(List.of());

        final var securityCode = Optional.ofNullable(meta)
                .flatMap(Meta::getSecurityCode);

        if (securityCode.isEmpty()) {
            throw new NullPointerException("chart.results[].meta.symbolはnullです");
        }

        if (sizeOfTimestamp != CollectionUtils.size(stockPriceIndexList)) {
            log.warn("タイムスタンプ配列と株価指標リストの長さが一致しません。");
            // 空のリストを渡す
            return StockPrice.emptyIndex(securityCode.get());
        }

        final var dailyIndices = IntStream
                .range(0, sizeOfTimestamp)
                .mapToObj(index -> {

                    final var historyDate = HistoryDate.from(
                            ((ArrayList<Integer>) CollectionUtils.emptyIfNull(timestamps)).get(
                                    index));
                    final var stockPriceIndex = stockPriceIndexList.get(index);

                    return DailyIndex.builder()
                            .stockPriceIndex(stockPriceIndex)
                            .historyDate(historyDate)
                            .build();
                })
                .collect(Collectors.toUnmodifiableList());

        return StockPrice.builder()
                .dailyIndices(dailyIndices)
                .securityCode(securityCode.get())
                .build();
    }
}
