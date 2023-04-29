package com.davidparry.chuck

import com.davidparry.chuck.controller.Quote
import com.davidparry.chuck.controller.QuoteController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import spock.lang.Specification

class NameReplaceQuoteGeneratorSpec extends Specification {


    def "Testing for client getting called "() {

        given:
        String QUOTE_RESPONSE = "Chuck Norris Quote"
        WebClient.RequestHeadersUriSpec headersUriSpec = Mock(WebClient.RequestHeadersUriSpec.class)
        WebClient.ResponseSpec responseSpec = Mock(WebClient.ResponseSpec.class)
        Mono<Quote> mono = Mono.just(new Quote(QUOTE_RESPONSE))

        QuoteController dannyController = new QuoteController(webClient, dannyFunction())

        when:
        Mono<Quote> monoResponse = dannyController.getQuote()
        Quote quote = monoResponse.block()


        then:
        1 * webClient.get() >> headersUriSpec
        1 * headersUriSpec.retrieve() >> responseSpec
        1 * responseSpec.bodyToMono(Quote.class) >> mono
        quote.value() == "Danny Quote"

    }
}
