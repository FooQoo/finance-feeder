package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
class Quote implements Serializable {

    private static final long serialVersionUID = 7665077379662869079L;

    @JsonProperty("high")
    private List<BigDecimal> highs;

    @JsonProperty("volume")
    private List<Integer> volumes;

    @JsonProperty("open")
    private List<BigDecimal> opens;

    @JsonProperty("low")
    private List<BigDecimal> lows;

    @JsonProperty("close")
    private List<BigDecimal> closes;

    int size() {
        return CollectionUtils.size(highs);
    }

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
