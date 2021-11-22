package com.fooqoo56.dev.financefeeder.domain.model.finance.index;

import com.fooqoo56.dev.financefeeder.domain.model.type.premitive.UnsignedBigDecimal;
import com.fooqoo56.dev.financefeeder.domain.model.type.premitive.UnsignedInteger;
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
    private final UnsignedBigDecimal high;

    /**
     * 安値.
     */
    @NonNull
    private final UnsignedBigDecimal low;

    /**
     * 初値.
     */
    @NonNull
    private final UnsignedBigDecimal open;

    /**
     * 終値.
     */
    @NonNull
    private final UnsignedBigDecimal close;

    /**
     * 調整済み終値.
     */
    @NonNull
    private final UnsignedBigDecimal adjClose;

    /**
     * 出来高.
     */
    @NonNull
    private final UnsignedInteger volume;

    /**
     * ファクトリメソッド.
     *
     * @param high     高値
     * @param low      安値
     * @param open     初値
     * @param close    終値
     * @param adjClose 調整済み終値
     * @param volume   出来高
     * @return StockPriceIndexのインスタンス
     */
    @Builder(builderMethodName = "builderOf", builderClassName = "build")
    public static StockPriceIndex of(final BigDecimal high,
                                     final BigDecimal low, final BigDecimal open,
                                     final BigDecimal close, final BigDecimal adjClose,
                                     final Integer volume) {

        return StockPriceIndex.builder()
                .high(UnsignedBigDecimal.from(high))
                .low(UnsignedBigDecimal.from(low))
                .open(UnsignedBigDecimal.from(open))
                .close(UnsignedBigDecimal.from(close))
                .adjClose(UnsignedBigDecimal.from(adjClose))
                .volume(UnsignedInteger.from(volume))
                .build();
    }

    @NonNull
    public double getHighPrice() {
        return high.toDouble();
    }

    @NonNull
    public double getLowPrice() {
        return low.toDouble();
    }

    @NonNull
    public double getOpenPrice() {
        return open.toDouble();
    }

    @NonNull
    public double getClosePrice() {
        return close.toDouble();
    }

    @NonNull
    public double getAdjClosePrice() {
        return adjClose.toDouble();
    }

    @NonNull
    public int getVolumeNumber() {
        return volume.toInt();
    }
}
