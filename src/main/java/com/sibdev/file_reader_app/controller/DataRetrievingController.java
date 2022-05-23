package com.sibdev.file_reader_app.controller;

import com.sibdev.file_reader_app.domain.dto.CsvDataDto;
import com.sibdev.file_reader_app.domain.dto.CsvDataResponse;
import com.sibdev.file_reader_app.service.DataProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
@AllArgsConstructor
public class DataRetrievingController {
    private final DataProcessingService service;

    @GetMapping
    public CsvDataResponse uploadData(@RequestParam String fileHeader) {
        final List<CsvDataDto> upload = service.upload(fileHeader);
        return new CsvDataResponse().setData(upload);
    }
}
