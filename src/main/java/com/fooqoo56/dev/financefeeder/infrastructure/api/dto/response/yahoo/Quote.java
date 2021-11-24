package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
@ToString
@Builder
class Quote implements Serializable {

    private static final long serialVersionUID = 7665077379662869079L;

    @JsonProperty("high")
    @NonNull
    private final List<BigDecimal> highs;

    @JsonProperty("volume")
    @NonNull
    private final List<Integer> volumes;

    @JsonProperty("open")
    @NonNull
    private final List<BigDecimal> opens;

    @JsonProperty("low")
    @NonNull
    private final List<BigDecimal> lows;

    @JsonProperty("close")
    @NonNull
    private final List<BigDecimal> closes;

    int length() {
        final var sizeOfHighs = CollectionUtils.size(highs);
        final var sizeOfLows = CollectionUtils.size(lows);
        final var sizeOfVolumes = CollectionUtils.size(volumes);
        final var sizeOfOpens = CollectionUtils.size(opens);
        final var sizeOfCloses = CollectionUtils.size(closes);

        final var equalAllList = Stream.of(sizeOfLows, sizeOfVolumes, sizeOfOpens, sizeOfCloses)
                .allMatch(i -> i == sizeOfHighs);

        // 全ての配列の長さが一致する場合、highsの長さを代表して返す。それ以外の場合は0を返す
        return equalAllList ? sizeOfHighs : 0;
    }

    Optional<BigDecimal> getHigh(final Integer index) {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(highs.get(index));
    }

    Optional<BigDecimal> getOpen(final Integer index) {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(opens.get(index));
    }

    Optional<BigDecimal> getLow(final Integer index) {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(lows.get(index));
    }

    Optional<BigDecimal> getClose(final Integer index) {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(closes.get(index));
    }

    Optional<Integer> getVolume(final Integer index) {

        // 長さが0の場合、Optional.emptyを返す
        if (length() <= 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(volumes.get(index));
    }
}
