package com.music.song.service.impl;

import com.music.song.entity.Song;
import com.music.song.repository.SongRepository;
import com.music.song.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository repository;

    @Override
    public Song save(Song song) {
        return repository.save(song);
    }

    @Override
    public Song getById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<Long> deleteByIds(List<Long> ids) {
        return ids.stream()
                .map(this::deleteById)
                .filter(id -> !repository.existsById(id))
                .toList();
    }

    @Override
    public List<Long> getAllIds() {
        return repository.findAllIds();
    }


    private Long deleteById(Long id) {
        repository.deleteById(id);

        return id;
    }

}
