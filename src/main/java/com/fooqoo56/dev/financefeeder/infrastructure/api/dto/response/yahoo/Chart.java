package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
class Chart implements Serializable {

    private static final long serialVersionUID = 149510784494652354L;

    @JsonProperty("result")
    private final List<Result> results;

    /**
     * resultsを返却する
     *
     * @return results、nullの場合は空のリストを返す
     */
    @NonNull
    List<StockPrice> toStockPrice() {
        // resultsの先頭を取得する
        return CollectionUtils.emptyIfNull(results).stream()
                .map(Result::toStockPrice)
                .collect(Collectors.toUnmodifiableList());
    }
}
