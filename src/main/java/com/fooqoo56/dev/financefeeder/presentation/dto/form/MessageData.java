package com.fooqoo56.dev.financefeeder.presentation.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

/**
 * MQで取得したデータ.
 */
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class MessageData implements Serializable {

    private static final long serialVersionUID = 9142369108979094892L;

    @JsonProperty("code")
    @NonNull
    private final String code;


    /**
     * SecurityCodeを取得する.
     *
     * @return SecurityCodeインスタンス
     */
    public SecurityCode getSecurityCode() {
        return SecurityCode.from(code);
    }
}