package com.fooqoo56.dev.financefeeder.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * MQで取得したデータ.
 */
@AllArgsConstructor
@NoArgsConstructor
public class MessageData implements Serializable {

    private static final long serialVersionUID = 9142369108979094892L;

    @JsonProperty("code")
    private String code;

    /**
     * SecurityCodeを取得する.
     *
     * @return SecurityCodeインスタンス
     */
    public SecurityCode getSecurityCode() {
        return SecurityCode.from(code);
    }
}
