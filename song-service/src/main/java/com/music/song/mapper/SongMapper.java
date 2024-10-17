package com.music.song.mapper;

import com.music.song.dto.SongDto;
import com.music.song.entity.Song;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public Song fromDto(SongDto songDto) {
        Song song = new Song();
        song.setResourceId(songDto.getResourceId());
        song.setMetadata(songDto.getMetadata());

        return song;
    }

    public SongDto toDto(Song song) {
        SongDto dto = new SongDto();
        dto.setMetadata(song.getMetadata());
        dto.setResourceId(song.getResourceId());

        return dto;
    }
}
