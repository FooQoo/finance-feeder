package com.fooqoo56.dev.financefeeder.domain.model.type;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 数量を示す値オブジェクト.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Count implements Serializable {

    private static final long serialVersionUID = -6339765510626755001L;

    @NonNull
    private final Integer value;

    public static Count from(final Integer value) {
        return new Count(value);
    }

    public int toInt() {
        return value;
    }
}
