package com.sibdev.file_reader_app.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingService {

    void processFile(MultipartFile file);
}
