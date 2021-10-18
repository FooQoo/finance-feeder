package com.fooqoo56.dev.financefeeder.presentation.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Google Cloud PubsubからPublishされるメッセージ.
 */
@RequiredArgsConstructor
public class PubSubMessage implements Serializable {

    private static final long serialVersionUID = -8974036523793712602L;

    /**
     * pub/subのペイロード.
     */
    private final String data;

    /**
     * attributes.
     */
    private final Map<String, String> attributes;

    /**
     * メッセージID.
     */
    private final String messageId;

    /**
     * publishされた時刻.
     */
    private final String publishTime;
}