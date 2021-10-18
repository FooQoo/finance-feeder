package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.domain.model.type.Count;
import com.fooqoo56.dev.financefeeder.domain.model.type.PositiveBigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 日付ごとの株価指標.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class StockPriceIndex implements Serializable {

    private static final long serialVersionUID = 7023124527989431455L;

    /**
     * 高値.
     */
    @NonNull
    private final PositiveBigDecimal high;

    /**
     * 安値.
     */
    @NonNull
    private final PositiveBigDecimal low;

    /**
     * 初値.
     */
    @NonNull
    private final PositiveBigDecimal open;

    /**
     * 終値.
     */
    @NonNull
    private final PositiveBigDecimal close;

    /**
     * 調整済み終値.
     */
    @NonNull
    private final PositiveBigDecimal adjClose;

    /**
     * 出来高.
     */
    @NonNull
    private final Count volume;

    public static StockPriceIndex of(final BigDecimal high,
                                     final BigDecimal low, final BigDecimal open,
                                     final BigDecimal close, final BigDecimal adjClose,
                                     final Integer volume) {
        return StockPriceIndex.builder()
                .high(PositiveBigDecimal.from(high))
                .low(PositiveBigDecimal.from(low))
                .open(PositiveBigDecimal.from(open))
                .close(PositiveBigDecimal.from(close))
                .adjClose(PositiveBigDecimal.from(adjClose))
                .volume(Count.from(volume))
                .build();
    }

}
