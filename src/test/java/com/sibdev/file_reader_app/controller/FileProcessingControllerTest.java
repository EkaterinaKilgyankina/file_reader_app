package com.sibdev.file_reader_app.controller;

import com.sibdev.file_reader_app.service.FileProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;

import java.io.File;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FileProcessingController.class)
class FileProcessingControllerTest {
    @Autowired
    private FileProcessingController controller;
    private MockMvc mockMvc;

    @MockBean
    private FileProcessingService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void whenDownloadFileThanOk() throws Exception {
        //given
        File sourceFile = new File("src/test/resources/testData.csv");
        MockMultipartFile mockedToMultipart = new MockMultipartFile("file", FileCopyUtils.copyToByteArray(sourceFile));

        //then
        mockMvc.perform(multipart("/api/v1/files")
                .file(mockedToMultipart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(200));

        verify(service, times(1)).processFile(mockedToMultipart);
    }
}