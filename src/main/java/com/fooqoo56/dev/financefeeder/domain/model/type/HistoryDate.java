package com.fooqoo56.dev.financefeeder.domain.model.type;

import com.google.cloud.Timestamp;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

/**
 * 日時の値オブジェクトのドメインクラス.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class HistoryDate implements Serializable {

    private static final long serialVersionUID = 8470844445987935576L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd");

    private static final ZoneId JAPAN_ZONE_ID = ZoneId.of("Japan");

    private final LocalDateTime value;

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
     * Timestamp型に変換する.
     *
     * @return Timestamp
     */
    @NonNull
    public Timestamp convertToTimestamp() {
        return Timestamp.of(Date.from(value.atZone(JAPAN_ZONE_ID).toInstant()));
    }

    /**
     * 文字列として返す.
     *
     * @return uuuu-MM-dd
     */
    @NonNull
    public String toStringFormat() {
        return value.format(DATE_TIME_FORMATTER);
    }
}
