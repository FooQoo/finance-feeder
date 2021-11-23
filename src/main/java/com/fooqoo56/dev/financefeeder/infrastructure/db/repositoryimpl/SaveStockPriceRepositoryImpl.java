package com.fooqoo56.dev.financefeeder.infrastructure.db.repositoryimpl;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import com.fooqoo56.dev.financefeeder.domain.model.finance.StockPrice;
import com.fooqoo56.dev.financefeeder.domain.repository.SaveStockPriceRepository;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceDto;
import com.fooqoo56.dev.financefeeder.infrastructure.db.dto.StockPriceIndicesDto;
import com.fooqoo56.dev.financefeeder.infrastructure.db.repositoryimpl.firestore.FirestoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SaveStockPriceRepositoryImpl implements SaveStockPriceRepository {

    private final FirestoreRepository firestoreRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<FeedResult> saveStockPrice(
            final StockPrice stockPrice) {
        return firestoreRepository.saveAll(
                        StockPriceIndicesDto.from(stockPrice),
                        StockPriceDto.from(stockPrice.getSecurityCode()))
                .map(StockPriceIndicesDto::from)
                .map(StockPriceIndicesDto::toFeedResult);
    }
}
