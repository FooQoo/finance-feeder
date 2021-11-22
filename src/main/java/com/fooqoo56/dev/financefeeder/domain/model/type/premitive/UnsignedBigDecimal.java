package com.fooqoo56.dev.financefeeder.domain.model.type.premitive;

import com.fooqoo56.dev.financefeeder.exception.domain.model.InvalidTypeParamException;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 正の数.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class UnsignedBigDecimal implements Serializable {

    private static final long serialVersionUID = -4849516410513489668L;

    @NonNull
    private final BigDecimal value;

    /**
     * ファクトリメソッド.
     *
     * @param value 値
     * @return PositiveNumberインスタンス
     * @throws InvalidTypeParamException 引数が型違反の場合の例外
     */
    public static UnsignedBigDecimal from(final BigDecimal value) throws InvalidTypeParamException {
        final var isNegative = -1 == value.signum();

        if (isNegative) {
            throw new InvalidTypeParamException("負の数です。" + value);
        }

        return new UnsignedBigDecimal(value);
    }

    /**
     * doubleに変換.
     *
     * @return Double
     */
    public double toDouble() {
        return value.doubleValue();
    }
}
