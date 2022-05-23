package com.sibdev.file_reader_app;

import com.sibdev.file_reader_app.config.CsvReaderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CsvReaderProperties.class)
public class FileReaderAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileReaderAppApplication.class, args);
    }
}
