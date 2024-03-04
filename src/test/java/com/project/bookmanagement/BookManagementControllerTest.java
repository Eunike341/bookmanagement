package com.project.bookmanagement;

import com.project.bookmanagement.model.BookDto;
import com.project.bookmanagement.service.WebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookManagementControllerTest {

    @Mock
    WebClient.Builder webClientBuilderMock;

    @Mock
    WebClient webClientMock;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    WebClient.RequestBodySpec requestBodySpec;

    @Mock
    WebClient.ResponseSpec responseSpec;

    WebService webService;

    @BeforeEach
    public void setUp() {
        // Define the behavior of the mock WebClient.Builder

    }

    @Test
    public void testUpdateSuccess() {

        BookDto mockResponse = new BookDto(1l, "", "", 0);

        when(webClientMock.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        /*when(requestBodySpec.header(any(),any())).thenReturn(requestBodySpec);
        when(requestHeadersSpec.header(any(),any())).thenReturn(requestHeadersSpec);
        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);*/
        when(requestBodySpec.body(any(), eq(BookDto.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ArgumentMatchers.<Class<BookDto>>notNull()))
                .thenReturn(Mono.just(mockResponse));


        when(webClientBuilderMock.baseUrl("http://localhost:8080")).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientMock);
        webService = new WebService(webClientBuilderMock);

        Mono<BookDto> result = webService.updateBook(1l, new BookDto(1l, "","",0));
        BookDto streamedResult = result.block();

        assertEquals(mockResponse, streamedResult);
    }
}
