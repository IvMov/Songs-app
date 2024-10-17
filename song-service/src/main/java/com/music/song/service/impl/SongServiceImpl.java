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
    public String save(Song song) {
        return repository.save(song).getId();
    }

    @Override
    public Song getById(String id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    public List<String> deleteByIds(List<String> ids) {
        repository.deleteAllById(ids);

        return ids;
    }

    @Override
    public List<String> getAllIds() {
        return repository.findAll().stream()
                .map(Song::getId)
                .toList();
    }

}
