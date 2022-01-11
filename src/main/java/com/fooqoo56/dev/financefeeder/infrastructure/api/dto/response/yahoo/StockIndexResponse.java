package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Builder(access = AccessLevel.PRIVATE, builderMethodName = "privateBuilder", buildMethodName = "privateBuild")
@Getter
public class StockIndexResponse implements Serializable {

    private static final long serialVersionUID = 7134444896309548030L;

    @NonNull
    private final BigDecimal high;
    @NonNull
    private final Integer volume;
    @NonNull
    private final BigDecimal open;
    @NonNull
    private final BigDecimal low;
    @NonNull
    private final BigDecimal close;

    @Builder(builderClassName = "Factory")
    public static Optional<StockIndexResponse> of(
            @Nullable final BigDecimal high,
            @Nullable final Integer volume,
            @Nullable final BigDecimal open,
            @Nullable final BigDecimal low,
            @Nullable final BigDecimal close
    ) {
        if (ObjectUtils.anyNull(high, volume, open, low, close)) {
            return Optional.empty();
        }

        return Optional.of(StockIndexResponse.privateBuilder()
                .high(high)
                .low(low)
                .open(open)
                .volume(volume)
                .close(close)
                .privateBuild());
    }
}
