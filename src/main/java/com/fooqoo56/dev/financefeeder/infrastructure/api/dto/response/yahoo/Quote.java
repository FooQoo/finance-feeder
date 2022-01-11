package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
@Builder
@Getter
class Quote implements Serializable {

    private static final long serialVersionUID = 7665077379662869079L;

    @JsonProperty
    private final List<BigDecimal> highs;

    @JsonProperty
    private final List<Integer> volumes;

    @JsonProperty
    private final List<BigDecimal> opens;

    @JsonProperty
    private final List<BigDecimal> lows;

    @JsonProperty
    private final List<BigDecimal> closes;

    int length() {
        final var sizeOfHighs = CollectionUtils.size(highs);

        final var equalAllList = Stream.of(
                        CollectionUtils.size(lows),
                        CollectionUtils.size(opens),
                        CollectionUtils.size(volumes),
                        CollectionUtils.size(closes))
                .allMatch(i -> i == sizeOfHighs);

        // 全ての配列の長さが一致する場合、highsの長さを代表して返す。それ以外の場合は0を返す
        return equalAllList ? sizeOfHighs : 0;
    }

    List<StockIndexResponse> toStockIndexResponse() {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return List.of();
        }

        return IntStream.range(0, length())
                .mapToObj(index -> {
                            final var high = Optional.ofNullable(highs)
                                    .flatMap(nonNullHighs -> Optional.ofNullable(nonNullHighs.get(index)))
                                    .orElse(null);

                            final var low = Optional.ofNullable(lows)
                                    .flatMap(nonNullLows -> Optional.ofNullable(nonNullLows.get(index)))
                                    .orElse(null);

                            final var volume = Optional.ofNullable(volumes)
                                    .flatMap(nonNullVolumes -> Optional.ofNullable(
                                            nonNullVolumes.get(index)))
                                    .orElse(null);

                            final var open = Optional.ofNullable(opens)
                                    .flatMap(nonNullOpens -> Optional.ofNullable(nonNullOpens.get(index)))
                                    .orElse(null);

                            final var close = Optional.ofNullable(closes)
                                    .flatMap(
                                            nonNullCloses -> Optional.ofNullable(nonNullCloses.get(index)))
                                    .orElse(null);

                            return StockIndexResponse.builder()
                                    .high(high)
                                    .low(low)
                                    .open(open)
                                    .volume(volume)
                                    .close(close)
                                    .build();
                        }
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());
    }
}
