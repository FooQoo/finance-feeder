package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
@Builder
class Quote implements Serializable {

    private static final long serialVersionUID = 7665077379662869079L;

    @JsonProperty("high")
    @NonNull
    private final List<BigDecimal> highs;

    @JsonProperty("volume")
    @NonNull
    private final List<Integer> volumes;

    @JsonProperty("open")
    @NonNull
    private final List<BigDecimal> opens;

    @JsonProperty("low")
    @NonNull
    private final List<BigDecimal> lows;

    @JsonProperty("close")
    @NonNull
    private final List<BigDecimal> closes;

    BigDecimal getHigh(final Integer index) {
        return highs.get(index);
    }

    BigDecimal getOpen(final Integer index) {
        return opens.get(index);
    }

    BigDecimal getLow(final Integer index) {
        return lows.get(index);
    }

    BigDecimal getClose(final Integer index) {
        return closes.get(index);
    }

    Integer getVolume(final Integer index) {
        return volumes.get(index);
    }
}
