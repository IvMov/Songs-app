package com.music.song.service;

import com.music.song.entity.Song;

import java.util.List;

public interface SongService {

    String save(Song song);

    Song getById(String id);

    List<String> deleteByIds(List<String> ids);

    List<String> getAllIds();
}
