package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
class Chart implements Serializable {

    private static final long serialVersionUID = 149510784494652354L;

    @JsonProperty("result")
    private List<Result> results;

    StockPrice toStockPrice() {
        // resultsの先頭を取得する
        return results.stream().map(Result::toStockPrice).findFirst().orElseThrow();
    }
}
