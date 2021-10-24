package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDates;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        final var historyDates = HistoryDates.fromIntList(timestamps);
        final var stockPriceIndexList = indicators.toStockPriceIndexList();

        return StockPrice.of(meta.getSecurityCode(), stockPriceIndexList, historyDates);
    }
}
