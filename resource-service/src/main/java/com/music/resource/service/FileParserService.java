package com.music.resource.service;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

public interface FileParserService {

    Map<String, String> getFileMetadata(byte[] bytes) throws TikaException, IOException, SAXException;
}
