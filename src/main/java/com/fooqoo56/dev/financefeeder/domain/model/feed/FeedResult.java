package com.fooqoo56.dev.financefeeder.domain.model.feed;

import com.fooqoo56.dev.financefeeder.domain.model.type.Count;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * フィード処理の実行結果.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedResult implements Serializable {

    private static final long serialVersionUID = -6832021008523481085L;

    @NonNull
    private final Count numSavedCode;
}
