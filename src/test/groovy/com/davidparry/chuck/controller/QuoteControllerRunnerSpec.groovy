package com.davidparry.chuck.controller

import com.davidparry.chuck.service.QuoteService
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

@ExtendWith(SpringExtension)
@WebFluxTest(controllers = QuoteController.class)
class QuoteControllerRunnerSpec extends Specification {

    QuoteService nameReplaceQuoteGenerator = Mock(QuoteService.class)

    def webTestClient = WebTestClient.bindToController(new QuoteController(nameReplaceQuoteGenerator)).build()

    @Unroll("get quote for name #name returns a quote with text #expectedQuoteText")
    void "getQuote should return expected quote"() {

        when:
        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri("/quote/$name")
                .exchange()

        then:
        1 * nameReplaceQuoteGenerator.generateQuote(_) >> Mono.just(expectedQuoteText)
        response.expectStatus().isOk()
        response.expectBody(Quote.class).returnResult().responseBody.value() == expectedQuoteText

        where:
        name      | expectedQuoteText
        "Alice"   | "Alice you are a chuck quote"
        "Bob"     | "Bob you rock like chuck"
        "Charlie" | "Charlie can do what chuck does"
    }


}
