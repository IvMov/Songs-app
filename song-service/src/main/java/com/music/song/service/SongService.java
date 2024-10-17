package com.music.song.service;

import com.music.song.entity.Song;

import java.util.List;

public interface SongService {

    Song save(Song song);

    Song getById(Long id);

    List<Long> deleteByIds(List<Long> ids);

    List<Long> getAllIds();
}
