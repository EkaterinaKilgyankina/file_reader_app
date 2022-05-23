package com.sibdev.file_reader_app.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class CsvData {
    private Long id;
    private String fileName;
    private long lineNumber;
    private Map<String, String> data;
}
