package com.sibdev.file_reader_app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class FileReaderAppApplicationTests {
    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresContainer.properties(registry);
    }

    @Test
    void contextLoads() {
    }

}
