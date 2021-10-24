package com.fooqoo56.dev.financefeeder.domain.model.finance;

import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDates;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 株価のドメインクラス.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class StockPrice implements Serializable {

    private static final long serialVersionUID = 4512465547529313124L;

    @NonNull
    @Getter
    private final SecurityCode securityCode;

    @NonNull
    private final StockPriceIndices stockPriceIndices;

    public static StockPrice of(final SecurityCode securityCode,
                                final List<StockPriceIndex> stockPriceIndexList,
                                final HistoryDates historyDates) {

        final var stockPriceIndices = StockPriceIndices.from(stockPriceIndexList, historyDates);

        return new StockPrice(securityCode, stockPriceIndices);
    }

    @NonNull
    public String getSecurityCodeString() {
        return securityCode.getCode();
    }

    @NonNull
    public HistoryDates getHistoryDates() {
        return stockPriceIndices.getHistoryDates();
    }

    public StockPriceIndex getStockPriceIndex(final HistoryDate historyDate) {
        return stockPriceIndices.getStockPriceIndex(historyDate);
    }

}
