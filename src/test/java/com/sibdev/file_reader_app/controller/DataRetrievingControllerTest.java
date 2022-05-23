package com.sibdev.file_reader_app.controller;

import com.sibdev.file_reader_app.domain.dto.CsvDataDto;
import com.sibdev.file_reader_app.service.DataProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        DataRetrievingController.class
})
class DataRetrievingControllerTest {
    @Autowired
    private DataRetrievingController controller;
    private MockMvc mockMvc;

    @MockBean
    private DataProcessingService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void whenUploadDataThanOk() throws Exception {
        //given
        final CsvDataDto csvData = new CsvDataDto()
                .setFileName("name")
                .setLineNumber(1)
                .setValue("data");

        //when
        when(service.upload("key")).thenReturn(Collections.singletonList(csvData));

        //then
        mockMvc.perform(get("/api/v1/content?fileHeader=key"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].fileName").value("name"))
                .andExpect(jsonPath("$.data[0].lineNumber").value(1))
                .andExpect(jsonPath("$.data[0].value").value("data"));
    }
}