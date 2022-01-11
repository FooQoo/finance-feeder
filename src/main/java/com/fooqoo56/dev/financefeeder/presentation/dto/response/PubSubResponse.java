package com.fooqoo56.dev.financefeeder.presentation.dto.response;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedResult;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class PubSubResponse implements Serializable {

    private static final long serialVersionUID = -3864080138750594368L;

    private final List<FeedSummary> feedSummary;

    public static PubSubResponse from(final List<FeedResult> feedResultList) {
        return new PubSubResponse(FeedSummary.toFeedSummaryList(feedResultList));
    }
}
