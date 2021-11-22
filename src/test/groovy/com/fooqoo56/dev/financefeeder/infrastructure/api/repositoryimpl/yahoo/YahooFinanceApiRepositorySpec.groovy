package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl.yahoo

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode
import com.fooqoo56.dev.financefeeder.domain.model.type.Day
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.request.yahoo.YahooApiRequestParam
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo.AdjClose
import com.fooqoo56.dev.financefeeder.infrastructure.api.dto.response.yahoo.Quote
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification
import spock.lang.Unroll

class YahooFinanceApiRepositorySpec extends Specification {

    private MockWebServer mockWebServer
    private WebClient webClient

    final setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        webClient = WebClient.create(mockWebServer.url("/").toString())
    }

    final cleanup() {
        mockWebServer.shutdown()
    }

    @Unroll
    final "fetchStockPrice(#securityCode, #feedPeriod)の結果が#expectedJsonが返却された場合の出力と一致してる"() {
        given:
        // テスト対象クラスのインスタンス作成
        final sut = new YahooFinanceApiRepository(webClient)

        // mockサーバを作成する
        final mockResponse = new FileReader("src/test/resources/json/YahooApi/" + expectedJson).text
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setBody(mockResponse))

        final yahooApiRequestParam = YahooApiRequestParam.of(securityCode, feedPeriod)

        when:
        final actual = sut.getChart(yahooApiRequestParam).block()

        then:
        final result = actual.chart.results[0]
        final indicators = result.indicators
        verifyAll {
            // 証券コードが一致している
            result.meta.symbol == "7647.T"
            // タイムスタンプが一致している
            result.timestamps == [1634860800, 1634883301]
            // quoteが一致してる
            indicators.quotes[0] == Quote.builder()
                    .closes([new BigDecimal("25.0"), new BigDecimal("25.0")])
                    .highs([new BigDecimal("26.0"), new BigDecimal("26.0")])
                    .opens([new BigDecimal("25.0"), new BigDecimal("25.0")])
                    .volumes([93017800, 93017800])
                    .lows([new BigDecimal("25.0"), new BigDecimal("25.0")])
                    .build()
            // adjClosesが一致している
            indicators.adjCloses[0] == new AdjClose([new BigDecimal("25.0"), new BigDecimal("25.0")])
        }


        where:
        securityCode             | feedPeriod                                         || expectedJson
        new SecurityCode("7647") | new FeedPeriod(new Day(7, "1d"), new Day(1, "1d")) || "200.json"
    }

}
