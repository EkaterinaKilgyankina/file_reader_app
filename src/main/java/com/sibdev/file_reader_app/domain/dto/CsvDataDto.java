package com.sibdev.file_reader_app.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CsvDataDto {
    private String fileName;
    private long lineNumber;
    private String value;
}
