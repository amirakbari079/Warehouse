package com.example.warehouse.manager;
import com.example.warehouse.dto.ItBookResponseDto;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;


public class ItBooksProxy {
    public void getBookDataFromApi(String isbn13) {
        WebClient webClient = WebClient.create();
        ClientResponse response = webClient.get()
                .uri("https://api.itbook.store/1.0/books/"+isbn13)
                .exchange()
                .block();
        ItBookResponseDto responseDto = response.bodyToMono(ItBookResponseDto.class).block();

        System.out.println("title = " + responseDto);

    }

}
