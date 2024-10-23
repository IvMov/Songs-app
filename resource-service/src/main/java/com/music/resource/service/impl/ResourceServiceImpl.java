package com.music.resource.service.impl;

import com.music.resource.dto.SongMetadataDto;
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
        List<Long> removedSongs = songServiceClient.deleteSongs(ids);
        log.info("Metadata deleted from song-service with ids {}", removedSongs);
        return ids.stream()
                .map(this::deleteById)
                .filter(id -> !repository.existsById(id))
                .toList();
    }


    private void saveMetadata(Long resourceId, byte[] bytes) {
        try {
            Map<String, String> metadata = fileParserService.getFileMetadata(bytes);
            SongMetadataDto songMetadataDto = new SongMetadataDto();
            songMetadataDto.setResourceId(resourceId);
            songMetadataDto.setMetadata(metadata);
            log.info("Metadata saved to song-service with id {}", songServiceClient.saveSong(songMetadataDto));
        } catch (TikaException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private Long deleteById(Long id) {
        repository.deleteById(id);

        return id;
    }

}
