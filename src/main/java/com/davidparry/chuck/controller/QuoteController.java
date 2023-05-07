package com.davidparry.chuck.controller;
import com.davidparry.chuck.service.QuoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/")
public class QuoteController {
    private final QuoteService nameReplaceQuoteGenerator;
    public QuoteController(QuoteService nameReplaceQuoteGenerator) {
        this.nameReplaceQuoteGenerator = nameReplaceQuoteGenerator;
    }
    @GetMapping("/quote/{name}")
    public Mono<Quote> getQuote(@PathVariable String name) {
        return nameReplaceQuoteGenerator.generateQuote(name).map(Quote::new);
    }
}
