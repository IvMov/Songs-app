package com.music.resource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] data;
}
