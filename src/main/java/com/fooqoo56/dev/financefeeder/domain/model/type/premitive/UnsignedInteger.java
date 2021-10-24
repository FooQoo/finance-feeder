package com.fooqoo56.dev.financefeeder.domain.model.type.premitive;

import com.fooqoo56.dev.financefeeder.exception.domain.model.InvalidTypeParamException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 符号なし整数.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UnsignedInteger implements Serializable {

    private static final long serialVersionUID = 6756823578285765615L;

    private final Integer value;

    /**
     * ファクトリメソッド.
     *
     * @param value 値
     * @return UnsignedIntegerインスタンス
     */
    public static UnsignedInteger from(final Integer value) {

        final var isUnsigned = value >= 0;

        if (isUnsigned) {
            throw new InvalidTypeParamException("負の数です。");
        }

        return new UnsignedInteger(value);
    }

    /**
     * int型に変換する.
     *
     * @return 整数
     */
    public int toInt() {
        return value;
    }

}
