package com.music.resource.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Song {

    private String id;

    private Long resourceId;

    private Map<String, String> metadata;
}
