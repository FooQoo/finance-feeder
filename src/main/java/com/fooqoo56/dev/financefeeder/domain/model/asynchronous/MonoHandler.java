package com.fooqoo56.dev.financefeeder.domain.model.asynchronous;

import com.fooqoo56.dev.financefeeder.exception.domain.model.FailedBlockMonoException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Monoを扱うハンドラー.
 *
 * @param <T> 型
 */
@RequiredArgsConstructor(staticName = "from")
public class MonoHandler<T> {

    private final Mono<T> mono;

    /**
     * Monoのブロック処理.
     *
     * @return 型インスタンス
     */
    public T block() {
        final var object = mono.block();

        if (Objects.isNull(object)) {
            throw new FailedBlockMonoException("blokingに失敗しました。");
        }

        return object;
    }
}
