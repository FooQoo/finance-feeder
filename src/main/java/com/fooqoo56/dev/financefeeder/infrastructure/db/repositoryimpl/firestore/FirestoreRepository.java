package com.fooqoo56.dev.financefeeder.infrastructure.db.repositoryimpl.firestore;

import com.fooqoo56.dev.financefeeder.exception.infrastructure.repository.FailedSaveStockPriceException;
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
public class FirestoreRepository {

    private final FirestoreTemplate firestoreTemplate;

    /**
     * firestoreにデータ保存する
     *
     * @param stockPriceIndicesDto firestoreに保存するデータ
     * @return 保存後のデータ
     */
    public Mono<List<StockPriceIndexDto>> saveAll(
            final StockPriceIndicesDto stockPriceIndicesDto) {

        final FirestoreReactiveOperations stockPriceTemplate =
                firestoreTemplate.withParent(stockPriceIndicesDto);

        return stockPriceTemplate
                .saveAll(stockPriceIndicesDto.asFlux())
                .onErrorResume(exception -> Mono.error(
                        new FailedSaveStockPriceException("記事の保存に失敗しました:", exception)))
                .collectList();
    }
}
