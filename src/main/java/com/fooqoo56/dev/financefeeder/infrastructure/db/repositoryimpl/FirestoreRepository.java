package com.fooqoo56.dev.financefeeder.infrastructure.db.repositoryimpl;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.SaveStockPriceRepository;
import com.fooqoo56.dev.financefeeder.exception.infrastructure.repository.FailedSaveStockPriceException;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceCollectionDto;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceIndexDto;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceIndicesDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveOperations;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Firestoreの実装クラス.
 */
@Repository
@RequiredArgsConstructor
public class FirestoreRepository implements SaveStockPriceRepository {

    private final FirestoreTemplate firestoreTemplate;

    @Override
    public Mono<FeedResult> saveStockPrice(
            final StockPrice stockPrice) {

        final var stockPriceCollectionDto =
                StockPriceCollectionDto.from(stockPrice.getSecurityCode());

        final var stockPriceIndices = StockPriceIndicesDto.from(stockPrice);

        final FirestoreReactiveOperations stockPriceTemplate =
                firestoreTemplate.withParent(stockPriceCollectionDto);

        final Mono<List<StockPriceIndexDto>> stockPriceIndexDto = stockPriceTemplate
                .saveAll(stockPriceIndices.asFlux())
                .onErrorResume(exception -> Mono.error(
                        new FailedSaveStockPriceException("記事の保存に失敗しました:", exception)))
                .collectList();

        return stockPriceIndexDto
                .map(StockPriceIndicesDto::new)
                .map(StockPriceIndicesDto::toFeedResult);
    }
}
