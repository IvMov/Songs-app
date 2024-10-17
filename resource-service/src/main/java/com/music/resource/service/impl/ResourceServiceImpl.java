package com.music.resource.service.impl;

import com.music.resource.dto.Song;
import com.music.resource.entity.Resource;
import com.music.resource.repository.ResourceRepository;
import com.music.resource.service.FileParserService;
import com.music.resource.service.ResourceService;
import com.music.resource.webclient.SongServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository repository;
    private final FileParserService fileParserService;
    private final SongServiceClient songServiceClient;

    @Override
    public Resource save(byte[] bytes) {
        Resource resource = new Resource();
        resource.setData(bytes);
        resource = repository.save(resource);
        saveMetadata(resource.getId(), bytes);

        return resource;

    }

    @Override
    public List<Long> getAllIds() {
        return repository.findAllIds();
    }

    @Override
    public byte[] getBytesById(long id) {
        return repository.findById(id)
                .orElseThrow()
                .getData();
    }

    @Override
    public List<Long> deleteByIds(List<Long> ids) {
        repository.deleteAllById(ids);

        return ids;
    }


    private void saveMetadata(Long resourceId, byte[] bytes) {
        try {
            Map<String, String> metadata = fileParserService.getFileMetadata(bytes);
            Song song = new Song();
            song.setResourceId(resourceId);
            song.setMetadata(metadata);

            songServiceClient.saveSong(song)
                    .subscribe(result -> log.info("Metadata saved to song-service with id {}", result.toString()));
        } catch (TikaException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }


}
