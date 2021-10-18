package com.fooqoo56.dev.financefeeder.domain.model.feed

import com.fooqoo56.dev.financefeeder.domain.model.type.Day
import com.fooqoo56.dev.financefeeder.exception.InvalidTypeParamException
import spock.lang.Specification
import spock.lang.Unroll

class FeedPeriodSpec extends Specification {

    @Unroll
    final "ファクトリメソッドの引数が #range と #interval の場合、インスタンスの生成に成功する"() {
        when:
        final actual = FeedPeriod.of(new Day(range, range + "d"), new Day(interval, interval + "d"))

        then:
        actual == expected

        where:
        range | interval || expected
        7     | 1        || new FeedPeriod(new Day(7, "7d"), new Day(1, "1d"))
    }

    @Unroll
    final "ファクトリメソッドの引数が #range と #interval の場合、 InvalidTypeParamExceptionが発生する"() {
        when:
        FeedPeriod.of(new Day(range, range + "d"), new Day(interval, interval + "d"))

        then:
        final exception = thrown(InvalidTypeParamException)
        exception.getMessage() == message

        where:
        range | interval || message
        31    | 1        || "不正なrangeが指定されてます。range <= 30dで指定してください。"
        4     | 31       || "不正なintervalが指定されてます。interval <= 30dで指定してください。"
        1     | 7        || "不正なrangeが指定されてます。range > intervalで指定してください。"
    }
}
