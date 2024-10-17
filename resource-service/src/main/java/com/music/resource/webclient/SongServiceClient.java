package com.music.resource.webclient;

import com.music.resource.dto.SongMetadataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class SongServiceClient {

    @Value("${song-service.url}")
    private String songServiceUrl;
    private final RestTemplate restTemplate;

    public Integer saveSong(SongMetadataDto songMetadataDto) {
        return (Integer) restTemplate
                .postForObject(songServiceUrl + "/songs", songMetadataDto, Map.class)
                .get("id");
    }
}
