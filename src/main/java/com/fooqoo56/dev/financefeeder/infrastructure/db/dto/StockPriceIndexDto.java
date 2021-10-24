package com.fooqoo56.dev.financefeeder.infrastructure.db.dto;

import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPriceIndex;
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;
import org.springframework.lang.NonNull;

@Document(collectionName = "daily-stock-price")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class StockPriceIndexDto implements Serializable {

    private static final long serialVersionUID = -563254418400536591L;

    @DocumentId
    @NonNull
    private final String key;

    @NonNull
    private final Timestamp date;

    @NonNull
    private final Double high;

    @NonNull
    private final Double low;

    @NonNull
    private final Double open;

    @NonNull
    private final Double close;

    @NonNull
    private final Double adjClose;

    @NonNull
    private final Integer volume;

    static StockPriceIndexDto of(final HistoryDate historyDate,
                                 final StockPriceIndex stockPriceIndex) {

        return StockPriceIndexDto.builder()
                .key(historyDate.toStringFormat())
                .date(historyDate.convertToTimestamp())
                .high(stockPriceIndex.getHighPrice())
                .low(stockPriceIndex.getLowPrice())
                .open(stockPriceIndex.getOpenPrice())
                .close(stockPriceIndex.getClosePrice())
                .adjClose(stockPriceIndex.getAdjClosePrice())
                .volume(stockPriceIndex.getVolumeNumber())
                .build();
    }
}
