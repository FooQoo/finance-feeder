package com.fooqoo56.dev.financefeeder.infrastructure.db.dto;

import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gcp.data.firestore.Document;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

/**
 * StockPriceCollectionのDtoクラス.
 */
@Document(collectionName = "stock-price")
@RequiredArgsConstructor(staticName = "from")
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class StockPriceIndicesDto implements Serializable {

    private static final long serialVersionUID = -2260830748915513312L;

    @NonNull
    private final List<StockPriceIndexDto> stockPriceIndexDtoList;

    /**
     * ファクトリメソッド
     *
     * @param stockPrice 株価ドメインクラス
     * @return オブジェクト
     */
    public static StockPriceIndicesDto from(final StockPrice stockPrice) {

        final var stockPriceIndexDtoList =
                stockPrice.toDailyIndex().stream()
                        .map(StockPriceIndexDto::from)
                        .collect(Collectors.toUnmodifiableList());

        return new StockPriceIndicesDto(stockPriceIndexDtoList);
    }

    /**
     * Fluxに変換する
     *
     * @return Flux
     */
    public Flux<StockPriceIndexDto> asFlux() {
        return Flux.fromIterable(stockPriceIndexDtoList);
    }

    /**
     * 件数を取得する
     *
     * @return 件数
     */
    public int size() {
        return CollectionUtils.size(stockPriceIndexDtoList);
    }
}
