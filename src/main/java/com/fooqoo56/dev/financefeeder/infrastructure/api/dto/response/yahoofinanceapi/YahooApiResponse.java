package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class YahooApiResponse implements Serializable {

    private static final long serialVersionUID = 7828062540205365890L;

    @JsonProperty("chart")
    private Chart chart;

    public StockPrice toStockPrice() {
        return chart.toStockPrice();
    }
}
