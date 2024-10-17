package com.music.resource.controller;

import com.music.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final String INTERNAL_ERROR_MESSAGE = "An internal server error has occurred";
    private final ResourceService resourceService;

    @PostMapping
    ResponseEntity<Map<String, Long>> createNewResource(@RequestBody byte[] bytes) {
        try {
            Long id = resourceService.save(bytes).getId();
            return ResponseEntity.ok(Map.of("id", id));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @GetMapping
    ResponseEntity<List<Long>> findAllIds() {
        List<Long> allIds = resourceService.getAllIds();

        return ResponseEntity.ok(allIds);
    }

    @GetMapping("/{id}")
    ResponseEntity<byte[]> findById(@PathVariable Long id) {
        try {
            byte[] bytes = resourceService.getBytesById(id);

            return ResponseEntity.ok(bytes);
        } catch (RuntimeException e) {
            throw (e instanceof NoSuchElementException)
                    ? new ResponseStatusException(HttpStatus.NOT_FOUND, "The resource with the specified id does not exist", e)
                    : new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @DeleteMapping
    ResponseEntity<Map<String, List<Long>>> deleteByIds(@RequestParam List<Long> ids) {
        try {
            List<Long> deletedIds = resourceService.deleteByIds(ids);

            return ResponseEntity.ok(Map.of("ids", deletedIds));
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

}
