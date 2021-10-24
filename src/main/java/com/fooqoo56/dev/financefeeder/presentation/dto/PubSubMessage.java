package com.fooqoo56.dev.financefeeder.presentation.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooqoo56.dev.financefeeder.exception.presentation.dto.BadRequestException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * Google Cloud PubsubからPublishされるメッセージ.
 */
@RequiredArgsConstructor
public class PubSubMessage implements Serializable {

    private static final long serialVersionUID = -8974036523793712602L;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

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

    /**
     * メッセージデータをクラスにマッピングする.
     *
     * @return MessageDataインスタンス
     */
    public MessageData getMessageData() {
        final var decodedData = getDecodedData();
        try {
            return OBJECT_MAPPER.readValue(decodedData, MessageData.class);
        } catch (final JsonProcessingException exception) {
            throw new BadRequestException("メッセージがJSON形式でありません。" + decodedData, exception);
        }
    }

    /**
     * メッセージをデコードする.
     *
     * @return デコードされたdataパラメータ
     */
    @NonNull
    private String getDecodedData() {
        return new String(BASE64_DECODER.decode(data), StandardCharsets.UTF_8);
    }

}