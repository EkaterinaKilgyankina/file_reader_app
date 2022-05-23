package com.sibdev.file_reader_app.service;

import com.sibdev.file_reader_app.domain.dto.CsvDataDto;
import com.sibdev.file_reader_app.repository.CsvDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataProcessingServiceImpl implements DataProcessingService {
    CsvDataRepository repository;

    @Override
    public List<CsvDataDto> upload(String fileHeader) {
        return repository.find(fileHeader);
    }
}
