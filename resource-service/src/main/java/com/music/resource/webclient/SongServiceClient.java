package com.music.resource.webclient;

import com.music.resource.dto.Song;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SongServiceClient {

    private final WebClient webClient;

    public Mono<String> saveSong(Song song) {
        return webClient.post()
                .uri("/songs")
                .bodyValue(song)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> {
                    log.error("Error during WebClient call: " + e.getMessage());
                    return Mono.empty();
                });
    }
}
