package com.fooqoo56.dev.financefeeder.presentation.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooqoo56.dev.financefeeder.exception.presentation.dto.BadRequestException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Google Cloud PubsubからPublishされるメッセージ.
 */
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Getter
public class PubSubMessage implements Serializable {

    private static final long serialVersionUID = -8974036523793712602L;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /**
     * pub/subのペイロード.
     */
    @NonNull
    private final String data;

    /**
     * メッセージID.
     */
    @NonNull
    private final String messageId;

    /**
     * publishされた時刻.
     */
    @NonNull
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
        // null・空文字・空白文字の場合はデコードせずに空文字で返却する
        if (StringUtils.isBlank(data)) {
            return StringUtils.EMPTY;
        }

        return new String(BASE64_DECODER.decode(data), StandardCharsets.UTF_8);
    }
}