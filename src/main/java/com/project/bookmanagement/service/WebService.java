package com.project.bookmanagement.service;

import com.project.bookmanagement.model.BookDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebService {

    private final WebClient webClient;

    public WebService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Flux<BookDto> fetchBooks() {
        return webClient.get()
                .uri("/bookmanagement/api/books")
                .retrieve()
                .bodyToFlux(BookDto.class);
    }

    public Mono<Void> deleteBook(Long bookId) {
        return webClient.delete()
                .uri("/bookmanagement/api/books/" + bookId)
                .retrieve().bodyToMono(Void.class);
    }

    public Mono<BookDto> getBookById(Long bookId) {
        return webClient.get()
                .uri("bookmanagement/api/books/" + bookId)
                .retrieve()
                .bodyToMono(BookDto.class);
    }

    public Mono<BookDto> updateBook(Long bookId, BookDto updateValues) {
        return webClient.patch()
                .uri("bookmanagement/api/books/" + bookId)
                .body(Mono.just(updateValues), BookDto.class )
                .retrieve()
                .bodyToMono(BookDto.class);
    }

    public Mono<BookDto> createBook(BookDto newBook) {
        return webClient.post()
                .uri("bookmanagement/api/books")
                .body(Mono.just(newBook), BookDto.class )
                .retrieve()
                .bodyToMono(BookDto.class);
    }
}
