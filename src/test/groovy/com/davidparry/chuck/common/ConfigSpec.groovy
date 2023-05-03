package com.davidparry.chuck.common

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import spock.lang.Specification

class ConfigSpec extends Specification {

    def "WebClient filter isError log do not tie chuck api -> service -> client"() {
        given:
        Config config = new Config()
        ClientResponse response = Mock(ClientResponse.class)
        Mono<ApiErrorResponse> apiErrorResponseMono = Mono.just(new ApiErrorResponse("Status",
                "Error", "Message", "/path/message/bad"))

        when:
        Mono<ClientResponse> result = config.renderApiErrorResponse(response)
        result.block()

        then:
        1 * response.statusCode() >> HttpStatusCode.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value())
        1 * response.bodyToMono(ApiErrorResponse.class) >> apiErrorResponseMono
        thrown(ResponseStatusException.class)
    }

    def "WebClient filter happy path no error passthrough untouched response"() {
        given:
        Config config = new Config()
        ClientResponse response = Mock(ClientResponse.class)

        when:
        Mono<ClientResponse> result = config.renderApiErrorResponse(response)

        then:
        1 * response.statusCode() >> HttpStatusCode.valueOf(HttpStatus.OK.value())
        result.block() == response
    }
}
