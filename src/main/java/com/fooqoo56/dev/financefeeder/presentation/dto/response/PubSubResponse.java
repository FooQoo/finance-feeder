package com.fooqoo56.dev.financefeeder.presentation.dto.response;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PubSubResponse implements Serializable {

    private static final long serialVersionUID = -3864080138750594368L;

    private final String message;

    public static PubSubResponse from(final String message) {
        return new PubSubResponse(message);
    }
}
