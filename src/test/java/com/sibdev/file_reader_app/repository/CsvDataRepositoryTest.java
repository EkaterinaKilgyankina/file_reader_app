package com.sibdev.file_reader_app.repository;

import com.sibdev.file_reader_app.PostgresContainer;
import com.sibdev.file_reader_app.config.MyBatisConfiguration;
import com.sibdev.file_reader_app.domain.dto.CsvDataDto;
import com.sibdev.file_reader_app.domain.entity.CsvData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.*;

import java.util.HashMap;
import java.util.List;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.liquibase.change-log=classpath*:/liquibase-changelog.yaml")
@ContextConfiguration(classes = MyBatisConfiguration.class)
@DirtiesContext
class CsvDataRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CsvDataRepository repository;

    @DynamicPropertySource
    public static void properties(final DynamicPropertyRegistry registry) {
        PostgresContainer.properties(registry);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("delete from csv_data");
    }

    @Test
    void find() {
        //when
        jdbcTemplate.execute(
                "insert into csv_data values(1, 'name', 1, '{\"key\":\"value\"}'::jsonb)"
        );
        jdbcTemplate.execute(
                "insert into csv_data values(2, 'name', 2, '{\"key2\":\"value2\"}'::jsonb)"
        );

        //then
        final List<CsvDataDto> result = repository.find("key");

        //given
        Assertions.assertThat(result)
                .hasSize(1)
                .element(0)
                .matches(e -> e.getFileName().equals("name"))
                .matches(e -> e.getLineNumber() == 1L)
                .matches(e -> e.getValue().equals("value"));
    }

    @Test
    void save() {
        //when
        final String testSql = "select count(*) from csv_data where file_name = 'test_name'";
        final CsvData csvData = new CsvData()
                .setData(new HashMap<>() {{
                    put("key1", "value1");
                    put("key2", "value2");
                }})
                .setFileName("test_name")
                .setLineNumber(1);
        final List<Long> resultBefore = jdbcTemplate.query(testSql, (rs, rowNum) -> rs.getLong(1));
        Assertions.assertThat(resultBefore)
                .hasSize(1)
                .element(0)
                .isEqualTo(0L);

        //then
        repository.save(csvData);

        //given
        final List<Long> result = jdbcTemplate.query(testSql, (rs, rowNum) -> rs.getLong(1));
        Assertions.assertThat(result)
                .hasSize(1)
                .element(0)
                .isEqualTo(1L);
    }
}