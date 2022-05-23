package com.sibdev.file_reader_app.service;

import com.sibdev.file_reader_app.domain.dto.CsvDataDto;

import java.util.List;

public interface DataProcessingService {

    List<CsvDataDto> upload(String fileHeader);

}
