package com.music.song.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SongDto {

    private Long resourceId;

    private Map<String, String> metadata;
}
