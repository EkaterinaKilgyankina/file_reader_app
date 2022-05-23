package com.sibdev.file_reader_app.repository;

import com.sibdev.file_reader_app.domain.dto.CsvDataDto;
import com.sibdev.file_reader_app.domain.entity.CsvData;
import lombok.NonNull;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CsvDataRepository {

    @Select("select file_name, line_number, data ->> #{key} AS value from csv_data where data -> #{key} is not null;")
    List<CsvDataDto> find(@NonNull @Param("key") String key);

    @Insert("insert into csv_data (file_name, line_number, data) values (#{fileName}, #{lineNumber}, #{data})")
    void save(@NonNull CsvData data);
}
