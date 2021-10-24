package com.fooqoo56.dev.financefeeder.domain.model.feed;

import com.fooqoo56.dev.financefeeder.domain.model.type.premitive.UnsignedInteger;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * フィード処理の実行結果.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class FeedResult implements Serializable {

    private static final long serialVersionUID = -6832021008523481085L;

    @NonNull
    private final UnsignedInteger numSavedCode;

    /**
     * ファクトリメソッド.
     *
     * @param count 件数
     * @return FeedResultインスタンス
     */
    public static FeedResult from(final Integer count) {
        return new FeedResult(UnsignedInteger.from(count));
    }

    /**
     * フィード結果のログ出力.
     */
    public void logFeedResult() {
        log.info("保存された件数:" + numSavedCode.toInt());
    }
}
