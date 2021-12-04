package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.io.Serializable;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
class Meta implements Serializable {

    private static final long serialVersionUID = -773072678203335505L;

    @JsonProperty
    private final String symbol;

    Optional<SecurityCode> getSecurityCode() {
        return Optional.ofNullable(symbol)
                .map(SecurityCode::fromCodeT);
    }
}
