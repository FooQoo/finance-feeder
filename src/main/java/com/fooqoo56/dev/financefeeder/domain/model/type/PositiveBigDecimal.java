package com.fooqoo56.dev.financefeeder.domain.model.type;

import com.fooqoo56.dev.financefeeder.exception.InvalidTypeParamException;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * 正の数.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class PositiveBigDecimal implements Serializable {

    private static final long serialVersionUID = -4849516410513489668L;

    private final BigDecimal value;

    /**
     * ファクトリメソッド.
     *
     * @param value 値
     * @return PositiveNumberインスタンス
     * @throws InvalidTypeParamException 引数が型違反の場合の例外
     */
    public static PositiveBigDecimal from(final BigDecimal value) throws InvalidTypeParamException {
        final var isNegative = -1 == value.signum();

        if (isNegative) {
            throw new InvalidTypeParamException("負の数です。" + value);
        }

        return new PositiveBigDecimal(value);
    }
}
