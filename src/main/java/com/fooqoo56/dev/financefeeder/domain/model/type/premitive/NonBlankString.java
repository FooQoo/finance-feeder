package com.fooqoo56.dev.financefeeder.domain.model.type.premitive;

import com.fooqoo56.dev.financefeeder.exception.domain.model.InvalidTypeParamException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * 空文字なし文字列ドメイン.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class NonBlankString implements Serializable {

    private static final long serialVersionUID = -4999302092978735651L;

    @NonNull
    private final String value;

    /**
     * ファクトリメソッド.
     *
     * @param value 値
     * @return NonBlankStringインスタンス
     */
    public NonBlankString from(@NonNull final String value) {

        if (StringUtils.isBlank(value)) {
            throw new InvalidTypeParamException("文字列が空文字です。");
        }

        return new NonBlankString(value);
    }
}
