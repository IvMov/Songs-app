package com.music.resource.service.impl;

import com.music.resource.service.FileParserService;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class Mp3ParserServiceImpl implements FileParserService {

    @Override
    public Map<String, String> getFileMetadata(byte[] bytes) throws TikaException, IOException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        Mp3Parser Mp3Parser = new Mp3Parser();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        Mp3Parser.parse(inputStream, handler, metadata, parseContext);

        return Arrays.stream(metadata.names())
                .collect(Collectors.toMap(Function.identity(), metadata::get));
    }
}
