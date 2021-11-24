package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.domain.model.finance.index.DailyIndex;
import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.lang.NonNull;

/**
 * 株価のドメインクラス.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Slf4j
public class StockPrice implements Serializable {

    private static final long serialVersionUID = 4512465547529313124L;

    @NonNull
    @Getter
    private final SecurityCode securityCode;

    @NonNull
    private final Map<HistoryDate, StockPriceIndex> dailyIndexMap;

    public static StockPrice emptyIndex(final SecurityCode securityCode) {
        return new StockPrice(securityCode, Map.of());
    }

    public boolean empty() {
        return MapUtils.isEmpty(dailyIndexMap);
    }

    @Builder(builderClassName = "Factory")
    public static StockPrice of(final SecurityCode securityCode,
                                final List<DailyIndex> dailyIndices) {

        if (CollectionUtils.isEmpty(dailyIndices)) {
            return emptyIndex(securityCode);
        }

        // 日付で集約する
        final var dailyIndicesMap = dailyIndices.stream()
                .collect(Collectors.groupingBy(DailyIndex::getHistoryDate,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                // リストの先頭を取得する
                                StockPrice::getFirstStockPriceIndex)));

        return new StockPrice(securityCode, dailyIndicesMap);
    }

    @NonNull
    private static StockPriceIndex getFirstStockPriceIndex(final List<DailyIndex> indices) {
        return indices.stream().findFirst().orElseThrow().getStockPriceIndex();
    }

    /**
     * 日付ごとの指標データをリストにして返却する
     *
     * @return DailyIndexのリスト
     */
    @NonNull
    public List<DailyIndex> toDailyIndex() {
        return dailyIndexMap.entrySet().stream().map(dailyStockIndex -> DailyIndex.builder()
                .stockPriceIndex(dailyStockIndex.getValue())
                .historyDate(dailyStockIndex.getKey())
                .build()).collect(Collectors.toUnmodifiableList());
    }
}
