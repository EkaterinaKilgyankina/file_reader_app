package com.sibdev.file_reader_app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@ConfigurationProperties(prefix = "service.csv")
public class CsvReaderProperties {
    @NotBlank
    private String separator;
}
