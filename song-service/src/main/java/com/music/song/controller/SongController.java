package com.music.song.controller;

import com.music.song.dto.SongDto;
import com.music.song.entity.Song;
import com.music.song.mapper.SongMapper;
import com.music.song.service.SongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private static final String INTERNAL_ERROR_MESSAGE = "An internal server error has occurred";

    private final SongMapper mapper;
    private final SongService service;

    @PostMapping
    ResponseEntity<Map<String, Long>> saveSong(@RequestBody SongDto dto) {
        try {
            Song song = mapper.fromDto(dto);
            Long id = service.save(song).getId();

            return ResponseEntity.ok(Map.of("id", id));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @GetMapping
    ResponseEntity<List<Long>> findAllIds() {
        return ResponseEntity.ok(service.getAllIds());
    }

    @GetMapping("/{id}")
    ResponseEntity<SongDto> findSongById(@PathVariable Long id) {
        try {
            Song song = service.getById(id);
            SongDto songDto = mapper.toDto(song);

            return ResponseEntity.ok(songDto);
        } catch (RuntimeException e) {
            throw (e instanceof NoSuchElementException)
                    ? new ResponseStatusException(HttpStatus.NOT_FOUND, "The song metadata with the specified id does not exist", e)
                    : new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @DeleteMapping
    ResponseEntity<List<Long>> deleteByIds(@Valid @Size(max = 200) String ids) {
        try {
            List<Long> idValues = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .toList();
            return ResponseEntity.ok(service.deleteByIds(idValues));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

}
