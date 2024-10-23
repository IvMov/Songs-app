package com.music.resource.webclient;

import com.music.resource.dto.SongMetadataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    public List<Long> deleteSongs(List<Long> ids) {
        String url = buildDeleteUrl(ids);

        ResponseEntity<Long[]> response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Long[].class);

        return response.getStatusCode().is2xxSuccessful()
                ? Arrays.stream(response.getBody()).toList()
                : Collections.emptyList();
    }

    private String buildDeleteUrl(List<Long> ids) {
        return UriComponentsBuilder.fromHttpUrl(songServiceUrl + "/songs")
                .queryParam("ids", ids)
                .toUriString();
    }
}
