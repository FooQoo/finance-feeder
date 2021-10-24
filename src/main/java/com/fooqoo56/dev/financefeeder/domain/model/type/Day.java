package com.fooqoo56.dev.financefeeder.domain.model.type;

import com.fooqoo56.dev.financefeeder.exception.domain.model.InvalidTypeParamException;
import java.io.Serializable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.NonNull;

/**
 * 日数の値オブジェクト.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Day implements Serializable {

    private static final long serialVersionUID = 6579075660024980025L;

    private static final Pattern DAY_PATTERN =
            Pattern.compile("^([1-9][0-9]*)d$");

    @NonNull
    private final Integer value;

    @NonNull
    private final String input;

    /**
     * ファクトリメソッド.
     *
     * @param value 文字列
     * @return 日数の値オブジェクト
     */
    public static Day from(final String value) {

        final var matcher = DAY_PATTERN.matcher(value);

        if (!matcher.matches()) {
            throw new InvalidTypeParamException("Dayの引数は正規表現「^[1-9][0-9]*d$」でありません。");
        }

        final var day = matcher.group(1);

        if (!NumberUtils.isDigits(day)) {
            throw new InvalidTypeParamException("Dayの引数は正の整数でありません。");
        }

        return new Day(NumberUtils.toInt(day), value);
    }

    public boolean isShorterThan(final Day day) {
        return value < day.value;
    }

    public String toString() {
        return input;
    }
}
