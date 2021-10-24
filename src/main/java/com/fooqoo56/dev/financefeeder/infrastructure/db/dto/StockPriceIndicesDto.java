package com.fooqoo56.dev.financefeeder.infrastructure.db.dto;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

/**
 * StockPriceCollectionのDtoクラス.
 */
@Document(collectionName = "stock-price")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class StockPriceIndicesDto implements Serializable {

    private static final long serialVersionUID = -2260830748915513312L;

    @NonNull
    private final List<StockPriceIndexDto> stockPriceIndexDtoList;

    public static StockPriceIndicesDto from(final StockPrice stockPrice) {

        final var historyDates = stockPrice.getHistoryDates();

        final var stockPriceCollectionDtoStream = historyDates.toStream()
                .map(historyDate -> StockPriceIndexDto.of(historyDate,
                        stockPrice.getStockPriceIndex(historyDate)))
                .collect(Collectors.toUnmodifiableList());

        return new StockPriceIndicesDto(stockPriceCollectionDtoStream);
    }

    public Flux<StockPriceIndexDto> asFlux() {
        return Flux.fromIterable(stockPriceIndexDtoList);
    }

    public FeedResult toFeedResult() {
        return FeedResult.from(stockPriceIndexDtoList.size());
    }
}
