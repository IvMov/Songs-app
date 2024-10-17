package com.music.song.repository;

import com.music.song.entity.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {

    @Query("select s.id from Song s")
    List<Long> findAllIds();
}
