package com.fooqoo56.dev.financefeeder.domain.model.finance.index;

import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 日付ごとの株価指標.
 */
@Builder
@EqualsAndHashCode
@Getter
public class DailyIndex implements Serializable {

    private static final long serialVersionUID = 3185453091630953746L;

    /**
     * 日付
     */
    private final HistoryDate historyDate;

    /**
     * 株価指数
     */
    private final StockPriceIndex stockPriceIndex;
}
