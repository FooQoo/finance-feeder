package com.fooqoo56.dev.financefeeder.presentation.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * PubSubから受信するメッセージ
 */
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PubSubBody {

    /**
     * message
     */
    @NotNull
    @JsonProperty
    private final PubSubMessage message;

    /**
     * 証券コード
     *
     * @return 証券コード
     */
    public SecurityCode getSecurityCode() {
        return message.getSecurityCode();
    }

    /**
     * メッセージの内容を文字列化する
     *
     * @return メッセージの文字列化
     */
    public String toStringMessage() {
        return message.toString();
    }
}

