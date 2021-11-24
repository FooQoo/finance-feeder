package com.fooqoo56.dev.financefeeder.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.lang.NonNull;

@EqualsAndHashCode
@ToString
@Builder
public class PubSubErrorResponse implements Serializable {

    private static final long serialVersionUID = -8918934283153158851L;

    @JsonProperty
    @NonNull
    private final String title;
}
