package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
class AdjClose implements Serializable {

    private static final long serialVersionUID = -4603933170114494528L;

    @JsonProperty("adjclose")
    @NonNull
    private final List<BigDecimal> adjCloses;

    int length() {
        return CollectionUtils.size(adjCloses);
    }

    Optional<BigDecimal> getAdjCloses(final Integer index) {
        return Optional.ofNullable(adjCloses.get(index));
    }
}
