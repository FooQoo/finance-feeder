package com.fooqoo56.dev.financefeeder.domain.model.feed;

import com.fooqoo56.dev.financefeeder.domain.model.type.Day;
import com.fooqoo56.dev.financefeeder.exception.InvalidTypeParamException;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * フィード期間.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class FeedPeriod implements Serializable {

    private static final long serialVersionUID = -8108870646694944541L;

    private static final Day MAX_DAY = Day.from("30d");

    @NonNull
    private final Day range;

    @NonNull
    private final Day interval;

    /**
     * ファクトリメソッド.
     *
     * @param range    フィードの日数
     * @param interval フィード間隔
     * @return フィード期間
     */
    public static FeedPeriod of(@NonNull final Day range, final Day interval)
            throws InvalidTypeParamException {

        if (MAX_DAY.isShorterThan(range)) {
            throw new InvalidTypeParamException(
                    "不正なrangeが指定されてます。range <= 30dで指定してください。");
        }

        if (MAX_DAY.isShorterThan(interval)) {
            throw new InvalidTypeParamException(
                    "不正なintervalが指定されてます。interval <= 30dで指定してください。");
        }

        if (range.isShorterThan(interval)) {
            throw new InvalidTypeParamException("不正なrangeが指定されてます。range > intervalで指定してください。");
        }

        return new FeedPeriod(range, interval);
    }

    public String getRangeParam() {
        return range.toString();
    }

    public String getIntervalParam() {
        return interval.toString();
    }
}
