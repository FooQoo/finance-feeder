package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class YahooApiResponse implements Serializable {

    private static final long serialVersionUID = 7828062540205365890L;

    @JsonProperty
    private final Chart chart;

    /**
     * StockPriceを返す
     *
     * @return StockPriceのリスト、chartがnullの場合、空のリストを返す
     */
    public List<StockPrice> toStockPrice() {
        return Optional.ofNullable(chart)
                .map(Chart::toStockPrice)
                .orElse(List.of());
    }
}
