package com.music.resource.service;

import com.music.resource.entity.Resource;

import java.util.List;

public interface ResourceService {
    Resource save(byte[] bytes);
    byte[] getBytesById(long id);
    List<Long> getAllIds();
    List<Long> deleteByIds(List<Long> ids);
}
