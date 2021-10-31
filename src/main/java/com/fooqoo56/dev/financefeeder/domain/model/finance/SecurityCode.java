package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.exception.domain.model.InvalidTypeParamException;
import java.io.Serializable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

/**
 * 証券コード.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class SecurityCode implements Serializable {

    private static final long serialVersionUID = -2414828022527010973L;

    private static final Pattern CODE_PATTERN =
            Pattern.compile("^[1-9][0-9][0-9][0-9]$");

    private static final Pattern CODE_T_PATTERN =
            Pattern.compile("^([1-9][0-9][0-9][0-9])\\.T$");

    @NonNull
    private final String value;

    /**
     * コードファクトリメソッド.
     *
     * @param value 入力値
     * @return 証券コードドメインのインスタンス
     */
    public static SecurityCode from(final String value) throws InvalidTypeParamException {

        if (CODE_PATTERN.matcher(value).matches()) {
            return new SecurityCode(value);
        }

        throw new InvalidTypeParamException("4桁の自然数ではありません。" + value);
    }

    /**
     * コードファクトリメソッド.
     *
     * @param value 入力値
     * @return 証券コードドメインのインスタンス
     */
    public static SecurityCode fromCodeT(final String value) throws InvalidTypeParamException {

        final var matcher = CODE_T_PATTERN.matcher(value);

        if (matcher.matches()) {
            return new SecurityCode(matcher.group(1));

        }

        throw new InvalidTypeParamException("東京証券取引所のコードではありません。");
    }

    public String getCode() {
        return value;
    }

    /**
     * 東京証券取引所の証券コードを取得.
     *
     * @return 東京証券取引所の証券コード
     */
    public String getTokyoStockExchangeCode() {
        return value + ".T";
    }

}
