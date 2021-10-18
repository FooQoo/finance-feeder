package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDates;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.lang.NonNull;

/**
 * 日付ごとの株価指標.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Slf4j
class StockPriceIndices implements Serializable {

    private static final long serialVersionUID = 7023124527989431455L;


    @NonNull
    private final Map<HistoryDate, StockPriceIndex> stockPriceIndexMap;

    static StockPriceIndices from(final List<StockPriceIndex> stockPriceIndexList,
                                  final HistoryDates historyDates) {

        log.info(stockPriceIndexList.toString());

        final var stockPriceIndexMap = IntStream.range(0, historyDates.length().toInt() - 1)
                .mapToObj(index -> Pair.of(historyDates.get(index),
                        stockPriceIndexList.get(index)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        return new StockPriceIndices(stockPriceIndexMap);
    }

}
