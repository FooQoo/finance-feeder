package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@Getter
@ToString
class Chart implements Serializable {

    private static final long serialVersionUID = 149510784494652354L;

    @JsonProperty("result")
    @NonNull
    private final List<Result> results;

    Optional<StockPrice> toStockPrice() {
        // resultsの先頭を取得する
        return results.stream()
                .map(Result::toStockPrice)
                .findFirst();
    }
}
