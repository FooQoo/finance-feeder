package com.fooqoo56.dev.financefeeder.infrastructure.db.repositoryimpl.firestore;

import com.fooqoo56.dev.financefeeder.exception.infrastructure.repository.FailedSaveStockPriceException;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceDto;
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

    private static final int RETRY = 3;

    private final FirestoreTemplate firestoreTemplate;

    /**
     * firestoreにデータ保存する
     *
     * @param stockPriceIndicesDto 証券コードに対応する指標データ
     * @param stockPriceDto        証券コード
     * @return 保存後のデータ
     */
    public Mono<List<StockPriceIndexDto>> saveAll(
            final StockPriceIndicesDto stockPriceIndicesDto, final StockPriceDto stockPriceDto) {

        final FirestoreReactiveOperations stockPriceTemplate =
                firestoreTemplate.withParent(stockPriceDto);

        return stockPriceTemplate
                .saveAll(stockPriceIndicesDto.asFlux())
                .retry(RETRY)
                .onErrorResume(exception -> Mono.error(
                        new FailedSaveStockPriceException("記事の保存に失敗しました:", exception)))
                .collectList();
    }
}
