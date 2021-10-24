package com.fooqoo56.dev.financefeeder.config;

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod;
import com.fooqoo56.dev.financefeeder.domain.model.type.Day;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * フィード設定.
 */
@ConfigurationProperties(prefix = "setting.feed")
@ConstructorBinding
@RequiredArgsConstructor
public class FeederSetting implements Serializable {

    private static final long serialVersionUID = -8108870646694944541L;

    //@Pattern(regexp = "^([1-9][0-9])*d$")
    private final String range;

    //@Pattern(regexp = "^([1-9][0-9])*d$")
    private final String interval;

    /**
     * フィード期間を取得する.
     *
     * @return フィード期間のインスタンス
     */
    public FeedPeriod toFeedPeriod() {
        return FeedPeriod.of(Day.from(range), Day.from(interval));
    }
}
