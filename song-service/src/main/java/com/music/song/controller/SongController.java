package com.music.song.controller;

import com.music.song.entity.Song;
import com.music.song.service.SongService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {

    private static final String INTERNAL_ERROR_MESSAGE = "An internal server error has occurred";

    private final SongService service;

    @PostMapping
    Mono<String> saveSong(@RequestBody Song song) {
        try {
            return Mono.just(service.save(song));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @GetMapping
    List<String> findAllIds() {
        return service.getAllIds();
    }

    @GetMapping("/{id}")
    Mono<Song> findSongById(@PathVariable String id) {
        try {
            return Mono.just(service.getById(id));
        } catch (RuntimeException e) {
            throw (e instanceof NoSuchElementException)
                    ? new ResponseStatusException(HttpStatus.NOT_FOUND, "The song metadata with the specified id does not exist", e)
                    : new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @DeleteMapping
    Flux<String> deleteByIds(@Valid @Size(max = 200) @RequestParam List<String> ids) {
        try {
            return Flux.fromIterable(service.deleteByIds(ids));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

}
