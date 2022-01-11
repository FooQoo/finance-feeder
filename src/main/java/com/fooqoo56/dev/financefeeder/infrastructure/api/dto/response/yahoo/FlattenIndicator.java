package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fooqoo56.dev.financefeeder.domain.model.finance.index.StockPriceIndex;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Builder(access = AccessLevel.PRIVATE, builderMethodName = "privateBuilder", buildMethodName = "privateBuild")
class FlattenIndicator {

    @NonNull
    private final Quote quote;
    @NonNull
    private final AdjClose adjClose;

    @Builder(builderClassName = "Factory")
    public static Optional<FlattenIndicator> of(
            @Nullable final Quote quote,
            @Nullable final AdjClose adjClose
    ) {
        if (ObjectUtils.allNotNull(quote, adjClose)) {
            return Optional.empty();
        }

        return Optional.of(FlattenIndicator.privateBuilder()
                .quote(quote)
                .adjClose(adjClose)
                .privateBuild());
    }

    @NonNull
    public static List<FlattenIndicator> of(final List<Quote> quotes,
                                            final List<AdjClose> adjCloses) {
        if (CollectionUtils.size(quotes) != CollectionUtils.size(adjCloses)) {
            return List.of();
        }

        return IntStream.range(0, CollectionUtils.size(quotes))
                .mapToObj(index -> {
                    final var quote = Optional.ofNullable(quotes)
                            .flatMap(nonNullQuotes -> Optional.ofNullable(
                                    nonNullQuotes.get(index)))
                            .orElse(null);

                    final var adjClose = Optional.ofNullable(adjCloses)
                            .flatMap(nonNullAdjClose -> Optional.ofNullable(
                                    nonNullAdjClose.get(index)))
                            .orElse(null);

                    return FlattenIndicator.builder()
                            .quote(quote)
                            .adjClose(adjClose)
                            .build();
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());

    }

    @NonNull
    public List<StockPriceIndex> toStockPriceIndex() {

        final var stockIndexResponses = quote.toStockIndexResponse();

        final var adjCloses = adjClose.getAdjCloses();

        if (CollectionUtils.size(stockIndexResponses) != CollectionUtils.size(adjCloses)) {
            return List.of();
        }

        return IntStream.range(0, CollectionUtils.size(stockIndexResponses))
                .mapToObj(index -> {
                    final var quote = stockIndexResponses.get(index);

                    final var adjClose = adjCloses.get(index);

                    // adjCloseがnullの場合、nullを返す
                    if (Objects.isNull(adjClose)) {
                        return null;
                    }

                    return StockPriceIndex.builder()
                            .high(quote.getHigh())
                            .low(quote.getLow())
                            .open(quote.getOpen())
                            .close(quote.getClose())
                            .adjClose(adjClose)
                            .volume(quote.getVolume())
                            .build();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }
}
