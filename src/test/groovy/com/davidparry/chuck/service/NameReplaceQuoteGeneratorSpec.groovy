package com.davidparry.chuck.service


import com.davidparry.chuck.service.Joke
import com.davidparry.chuck.service.NameReplaceQuoteGenerator
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import spock.lang.Specification

class NameReplaceQuoteGeneratorSpec extends Specification {

    def "Testing service for replacement "() {

        given:
        WebClient webClient = Mock(WebClient.class)
        String QUOTE_RESPONSE = "Chuck Norris Quote"
        WebClient.RequestHeadersUriSpec headersUriSpec = Mock(WebClient.RequestHeadersUriSpec.class)
        WebClient.ResponseSpec responseSpec = Mock(WebClient.ResponseSpec.class)
        Mono<Joke> mono = Mono.just(new Joke(QUOTE_RESPONSE))

        NameReplaceQuoteGenerator service = new NameReplaceQuoteGenerator(webClient)

        when:
        Mono<String> monoResponse = service.generateQuote("Name Last")
        String quote = monoResponse.block()


        then:
        1 * webClient.get() >> headersUriSpec
        1 * headersUriSpec.retrieve() >> responseSpec
        1 * responseSpec.bodyToMono(Joke.class) >> mono
        quote == "Name Last Quote"

    }
}
