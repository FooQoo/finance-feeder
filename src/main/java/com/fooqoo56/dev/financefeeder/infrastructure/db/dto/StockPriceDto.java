package com.fooqoo56.dev.financefeeder.infrastructure.db.dto;

import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import com.google.cloud.firestore.annotation.DocumentId;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;
import org.springframework.lang.NonNull;

/**
 * StockPriceのDtoクラス.
 */
@Document(collectionName = "stock-price")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class StockPriceDto implements Serializable {

    private static final long serialVersionUID = -2260830748915513312L;

    /**
     * 証券コード.
     */
    @DocumentId
    @NonNull
    private final String securityCode;

    /**
     * ファクトリメソッド.
     *
     * @param securityCode 証券コード
     * @return StockPriceCollectionDtoのインスタンス
     */
    public static StockPriceDto from(final SecurityCode securityCode) {
        return StockPriceDto.builder()
                .securityCode(securityCode.getCode())
                .build();
    }
}
