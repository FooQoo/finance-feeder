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
import spock.lang.Unroll

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

        when:
        final actual = sut.fetchStockPrice(securityCode, feedPeriod).block()

        then:
        // セキュリティコードが一致している
        actual.securityCodeString == "7647"

        // 日付ごとの株価指標を取り出す
        final stockPriceIndexMap = actual.stockPriceIndices.stockPriceIndexMap

        println(stockPriceIndexMap)

        // 2021年10月22日09時00分00秒の指標データの比較
        final historyDate1 = new HistoryDate(
                LocalDateTime.of(2021, 10, 22, 9, 0, 0))

        verifyAll(stockPriceIndexMap.get(historyDate1)) {
            high == new UnsignedBigDecimal(new BigDecimal("26.0"))
            open == new UnsignedBigDecimal(new BigDecimal("25.0"))
            low == new UnsignedBigDecimal(new BigDecimal("25.0"))
            close == new UnsignedBigDecimal(new BigDecimal("25.0"))
            adjClose == new UnsignedBigDecimal(new BigDecimal("25.0"))
            volumeNumber == 93017800
        }

        // 2021年10月22日15時15分01秒の指標データの比較
        final historyDate2 = new HistoryDate(
                LocalDateTime.of(2021, 10, 22, 15, 15, 1))

        verifyAll(stockPriceIndexMap.get(historyDate2)) {
            high == new UnsignedBigDecimal(new BigDecimal("26.0"))
            open == new UnsignedBigDecimal(new BigDecimal("25.0"))
            low == new UnsignedBigDecimal(new BigDecimal("25.0"))
            close == new UnsignedBigDecimal(new BigDecimal("25.0"))
            adjClose == new UnsignedBigDecimal(new BigDecimal("25.0"))
            volumeNumber == 93017800
        }

        where:
        securityCode             | feedPeriod                                         || expectedJson
        new SecurityCode("7647") | new FeedPeriod(new Day(7, "1d"), new Day(1, "1d")) || "200.json"
    }

}
