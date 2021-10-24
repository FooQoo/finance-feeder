package com.fooqoo56.dev.financefeeder.infrastructure.api.repositoryimpl

import com.fooqoo56.dev.financefeeder.domain.model.feed.FeedPeriod
import com.fooqoo56.dev.financefeeder.domain.model.finance.SecurityCode
import com.fooqoo56.dev.financefeeder.domain.model.type.Day
import com.fooqoo56.dev.financefeeder.domain.model.type.HistoryDate
import com.fooqoo56.dev.financefeeder.domain.model.type.premitive.UnsignedBigDecimal
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification

import java.time.LocalDateTime

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

    final "fetchStockPriceメソッド"() {
        given:
        // テスト対象クラスのインスタンス作成
        final sut = new YahooFinanceApiRepository(webClient)

        // mockサーバを作成する
        final mockResponse = new FileReader("src/test/resources/json/YahooApi/200.json").text
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setBody(mockResponse))

        final securityCode = new SecurityCode("7647")
        final feedPeriod = new FeedPeriod(new Day(7, "7d"), new Day(1, "1d"))

        when:
        final actual = sut.fetchStockPrice(securityCode, feedPeriod).block()

        then:
        // セキュリティコードが一致している
        actual.securityCodeString == "7647"

        // 日付ごとの株価指標を取り出す
        final stockPriceIndexMap = actual.stockPriceIndices.stockPriceIndexMap

        // 2021年10月7日の指標データの比較
        final historyDate1 = new HistoryDate(
                LocalDateTime.of(2021, 10, 7, 9, 0))

        verifyAll(stockPriceIndexMap.get(historyDate1)) {
            high == new UnsignedBigDecimal(new BigDecimal("27.0"))
            open == new UnsignedBigDecimal(new BigDecimal("26.0"))
            low == new UnsignedBigDecimal(new BigDecimal("25.0"))
            close == new UnsignedBigDecimal(new BigDecimal("27.0"))
            adjClose == new UnsignedBigDecimal(new BigDecimal("27.0"))
            volumeNumber == 86179100
        }
    }

}
