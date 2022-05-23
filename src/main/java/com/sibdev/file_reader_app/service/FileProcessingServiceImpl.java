package com.sibdev.file_reader_app.service;

import com.sibdev.file_reader_app.config.CsvReaderProperties;
import com.sibdev.file_reader_app.domain.entity.CsvData;
import com.sibdev.file_reader_app.exception.FileProcessingException;
import com.sibdev.file_reader_app.repository.CsvDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessingServiceImpl implements FileProcessingService {
    private final CsvDataRepository repository;
    private final CsvReaderProperties csvReaderProperties;

    @Override
    public void processFile(MultipartFile file) {
        try {
            final List<String> csvFileLines = IOUtils.readLines(file.getInputStream(), "UTF-8");
            final String[] fieldNames = csvFileLines
                    .stream()
                    .limit(1)
                    .findFirst()
                    .map(e -> e.split(csvReaderProperties.getSeparator()))
                    .orElseThrow(() -> new FileProcessingException("not possible to read file"));

            final AtomicInteger lineCount = new AtomicInteger(2);

            csvFileLines
                    .stream()
                    .skip(1)
                    .forEach(e -> {
                        final String[] split = e.split(csvReaderProperties.getSeparator());
                        final Map<String, String> data = new HashMap<>();

                        for (int i = 0; i < fieldNames.length; i++) {
                            data.put(fieldNames[i], split[i]);
                        }

                        final CsvData csvData = new CsvData()
                                .setFileName(file.getOriginalFilename())
                                .setLineNumber(lineCount.getAndIncrement())
                                .setData(data);
                        repository.save(csvData);
                    });
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new FileProcessingException(ex);
        }
    }
}