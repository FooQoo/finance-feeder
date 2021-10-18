package com.fooqoo56.dev.financefeeder.domain.model.type;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

/**
 * 日時の値オブジェクトのドメインクラス.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class HistoryDates implements Serializable {

    private static final long serialVersionUID = 8470844445987935576L;

    private final List<HistoryDate> value;

    /**
     * Integerのリストから型インスタンスを生成するファクトリメソッド.
     *
     * @param value 整数のリスト
     * @return DateTimeの型インスタンス
     */
    @NonNull
    public static HistoryDates from(final List<Integer> value) {
        final var historyDateList = value.stream().map(HistoryDate::from)
                .collect(Collectors.toUnmodifiableList());
        return new HistoryDates(historyDateList);
    }

    public HistoryDate get(final Integer index) {
        return value.get(index);
    }

    public Count length() {
        return Count.from(CollectionUtils.size(value));
    }
}
