package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoofinanceapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
class AdjClose implements Serializable {

    private static final long serialVersionUID = -4603933170114494528L;

    @JsonProperty("adjclose")
    private List<BigDecimal> adjCloses;

    BigDecimal getAdjCloses(final Integer index) {
        return adjCloses.get(index);
    }
}
