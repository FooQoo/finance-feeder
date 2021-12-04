package com.fooqoo56.dev.financefeeder.presentation.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

/**
 * Google Cloud PubsubからPublishされるメッセージ.
 */
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@ToString
@NotNull
class PubSubMessage implements Serializable {

    private static final long serialVersionUID = -8974036523793712602L;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    /**
     * pub/subのペイロード.
     */
    @JsonProperty
    @NotNull
    private final String data;

    /**
     * メッセージID.
     */
    @JsonProperty
    @NotNull
    private final String messageId;

    /**
     * publishされた時刻.
     */
    @JsonProperty
    @NotNull
    private final String publishTime;

    /**
     * 証券コードを取得する
     *
     * @return 証券コード
     * @throws ValidationException メッセージデータが不正なデータの場合の例外
     */
    @NonNull
    public SecurityCode getSecurityCode() throws ValidationException {
        try {
            return OBJECT_MAPPER.readValue(decodeBase64message(data), MessageData.class)
                    .toSecurityCode();
        } catch (final Throwable exception) {
            throw new ValidationException("メッセージデータは不正なフォーマットです。", exception);
        }
    }

    /**
     * base64エンコードされたメッセージをデコードする
     *
     * @param message base64エンコードされたメッセージ
     * @return デコード済みのメッセージ
     * @throws IllegalArgumentException null・空文字・空白文字が与えられた場合の例外
     */
    @NonNull
    private static String decodeBase64message(final String message)
            throws IllegalArgumentException {
        // null・空文字・空白文字の場合はデコードせずに例外をthrowする
        if (StringUtils.isBlank(message)) {
            throw new IllegalArgumentException("null・空文字・空白文字は不正です。" + message);
        }

        return new String(BASE64_DECODER.decode(message), StandardCharsets.UTF_8);
    }
}