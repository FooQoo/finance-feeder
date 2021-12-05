package com.fooqoo56.dev.financefeeder.domain.model.feed;

import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
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
    private final SecurityCode securityCode;

    @NonNull
    private final UnsignedInteger numSavedCode;

    /**
     * 証券コードを取得する
     *
     * @return 証券コード
     */
    public String getCode() {
        return securityCode.getCode();
    }

    public Integer getNumSaved() {
        return numSavedCode.toInt();
    }

    /**
     * ファクトリメソッド.
     *
     * @param count 件数
     * @return FeedResultインスタンス
     */
    public static FeedResult of(final SecurityCode securityCode, final Integer count) {
        return new FeedResult(securityCode, UnsignedInteger.from(count));
    }

    /**
     * 空要素
     *
     * @param securityCode 証券コード
     * @return オブジェクト
     */
    public static FeedResult empty(final SecurityCode securityCode) {
        return FeedResult.of(securityCode, 0);
    }

    /**
     * フィード結果のログ出力.
     */
    public void logFeedResult() {
        log.info("保存された件数:" + numSavedCode.toInt());
    }
}
