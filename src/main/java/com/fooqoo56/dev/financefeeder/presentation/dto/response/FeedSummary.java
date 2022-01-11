package com.fooqoo56.dev.financefeeder.presentation.dto.response;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(staticName = "of")
@Getter
@ToString
@Builder
public class FeedSummary implements Serializable {

    private static final long serialVersionUID = 4560934950077809557L;

    private final String code;

    private final Integer saved;

    public static FeedSummary from(final FeedResult feedResult) {
        return FeedSummary.builder()
                .code(feedResult.getCode())
                .saved(feedResult.getNumSaved())
                .build();
    }

    public static List<FeedSummary> toFeedSummaryList(final List<FeedResult> feedResultList) {
        return feedResultList.stream()
                .map(FeedSummary::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
