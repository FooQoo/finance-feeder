package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDates;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * 日付ごとの株価指標.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Slf4j
@ToString
class StockPriceIndices implements Serializable {

    private static final long serialVersionUID = 7023124527989431455L;

    @NonNull
    private final Map<HistoryDate, StockPriceIndex> stockPriceIndexMap;

    static StockPriceIndices from(final Map<HistoryDate, StockPriceIndex> stockPriceIndexMap) {
        return new StockPriceIndices(stockPriceIndexMap);
    }

    public HistoryDates getHistoryDates() {
        return HistoryDates.from(new ArrayList<>(stockPriceIndexMap.keySet()));
    }

    public StockPriceIndex getStockPriceIndex(final HistoryDate historyDate) {
        return stockPriceIndexMap.get(historyDate);
    }

}
