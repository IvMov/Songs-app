package com.music.resource.controller;

import com.music.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final String INTERNAL_ERROR_MESSAGE = "An internal server error has occurred";
    private final ResourceService resourceService;

    @PostMapping
    Long createNewResource(@RequestBody byte[] bytes) {
        try {
            return resourceService.save(bytes).getId();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @GetMapping
    List<Long> findAll() {
        return resourceService.getAllIds();
    }

    @GetMapping("/{id}")
    byte[] findById(@PathVariable Long id) {
        try {
            return resourceService.getBytesById(id);
        } catch (RuntimeException e) {
            throw (e instanceof NoSuchElementException)
                    ? new ResponseStatusException(HttpStatus.NOT_FOUND, "The resource with the specified id does not exist", e)
                    : new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

    @DeleteMapping
    List<Long> deleteByIds(@RequestParam List<Long> ids) {
        try {
            return resourceService.deleteByIds(ids);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_MESSAGE, e);
        }
    }

}
