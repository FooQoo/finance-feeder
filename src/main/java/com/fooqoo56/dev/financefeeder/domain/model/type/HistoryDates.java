package com.fooqoo56.dev.financefeeder.domain.model.type;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
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
public class HistoryDates implements Serializable {

    private static final long serialVersionUID = 8470844445987935576L;

    private final List<HistoryDate> value;

    @NonNull
    public static HistoryDates from(final List<HistoryDate> value) {
        return new HistoryDates(value);
    }

    public List<HistoryDate> asList() {
        return Collections.unmodifiableList(value);
    }
}
