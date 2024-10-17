package com.music.song.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Data
@Document("songs")
public class Song {

    @MongoId
    private String id;

    private Long resourceId;

    private Map<String, String> metadata;
}
