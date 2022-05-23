package com.sibdev.file_reader_app.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CsvDataResponse {
    private List<CsvDataDto> data;
}
