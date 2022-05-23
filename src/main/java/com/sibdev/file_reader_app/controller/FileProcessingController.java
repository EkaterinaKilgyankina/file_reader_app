package com.sibdev.file_reader_app.controller;

import com.sibdev.file_reader_app.service.FileProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@AllArgsConstructor
public class FileProcessingController {
    private final FileProcessingService service;

    @PostMapping
    public void downloadFile(@RequestParam MultipartFile file) {
        service.processFile(file);
    }
}
