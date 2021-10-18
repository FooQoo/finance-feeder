package com.fooqoo56.dev.financefeeder.domain.model.type;

import com.fooqoo56.dev.financefeeder.exception.InvalidTypeParamException;
import com.google.cloud.Timestamp;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 日時の値オブジェクトのドメインクラス.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class HistoryDate implements Serializable {

    private static final long serialVersionUID = 8470844445987935576L;

    private static final ZoneId JAPAN_ZONE_ID = ZoneId.of("Japan");

    private final LocalDateTime value;

    /**
     * Stringから型インスタンスを生成するファクトリメソッド.
     *
     * @param value String
     * @return DateTimeの型インスタンス
     */
    @NonNull
    public static HistoryDate from(final String value) {
        try {
            return new HistoryDate(
                    LocalDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME));
        } catch (final DateTimeParseException dateTimeParseException) {
            throw new InvalidTypeParamException("文字列がRFC1123の日付フォーマットに準拠していません。",
                    dateTimeParseException);
        } catch (final NullPointerException nullPointerException) {
            throw new InvalidTypeParamException("文字列がnullです。",
                    nullPointerException);
        }
    }

    /**
     * Stringから型インスタンスを生成するファクトリメソッド.
     *
     * @param timestamp unix timestamp
     * @return DateTimeの型インスタンス
     */
    @NonNull
    public static HistoryDate from(final Integer timestamp) {
        final var instant = Instant.ofEpochSecond(timestamp);
        final var localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return new HistoryDate(localDateTime);
    }

    /**
     * LocalDateTime型に変換する.
     *
     * @return LocalDateTime
     */
    @NonNull
    public LocalDateTime convertToLocalDate() {
        return value;
    }

    /**
     * Timestamp型に変換する.
     *
     * @return Timestamp
     */
    @NonNull
    public Timestamp convertToTimestamp() {
        return Timestamp.of(Date.from(value.atZone(JAPAN_ZONE_ID).toInstant()));
    }
}
