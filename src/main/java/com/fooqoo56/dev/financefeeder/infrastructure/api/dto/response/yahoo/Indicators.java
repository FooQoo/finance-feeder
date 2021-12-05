package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
@Slf4j
class Indicators implements Serializable {

    private static final long serialVersionUID = -773072678203335505L;

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

        final List<FlattenIndicator> flattenIndicators = FlattenIndicator.of(quotes, adjCloses);

        return flattenIndicators.stream()
                .map(FlattenIndicator::toStockPriceIndex)
                // 先頭だけ返す
                .findFirst()
                // なければ、空のリスト
                .orElse(List.of());
    }
}
