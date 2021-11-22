package com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request


import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request.UriParamBuilder
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification
import spock.lang.Unroll

class UriParamBuilderSpec extends Specification {

    @Unroll
    final "パスパラメータが #symbol 、クエリパラメータ #keyA , #keyB の値が #valueA, #valueB の時、URIが #expectedになる"() {
        given:
        final uriComponentsBuilder = UriComponentsBuilder
                .fromUriString("https://localhost")

        when:
        final actual = UriParamBuilder.builder()
                .addPathParam(symbol)
                .addQueryParam(keyA, valueA)
                .addQueryParam(keyB, valueB)
                .build(uriComponentsBuilder)

        then:
        actual.toString() == expected

        where:
        keyA   | valueA   | keyB   | valueB   | symbol  || expected
        "keyA" | "valueA" | "keyB" | "valueB" | "127.T" || "https://localhost/127.T?keyA=valueA&keyB=valueB"
    }
}
