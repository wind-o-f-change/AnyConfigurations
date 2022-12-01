package ru.ocrv.uad.backend.config.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.ahml.apxd.controller.ReportController;
import ru.ahml.apxd.service.dto.report.Person;
import ru.ahml.apxd.service.dto.report.ShortAgreementDto;
import ru.ahml.apxd.service.kodgenerator.KodGeneratorReportService;
import ru.ahml.apxd.util.ResponseBuilderUtil;
import ru.ahml.apxd.util.enums.ContentDisposition;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.ahml.apxd.util.ResponseBuilderUtil.encodeFileNameToUtf8;


@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ReportController.class})
public class OnlyControllerTest {
    private String urlPrefix = "/kod/report/";
    private byte[] stubBytes = "new some byte[]{}".getBytes();


    MockMvc mockMvc;
    @Mock
    KodGeneratorReportService KodGeneratorReportServiceImpl;
    @InjectMocks
    ReportController reportController;
    ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setUp() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void getFullAgreement_test() throws Exception {
        when(KodGeneratorReportServiceImpl.getFullUserAgreement(any(Person.class)))
                .thenReturn(stubBytes);
        String json = mapper.writeValueAsString(ReportTestUtils.buildFullAgreementDto());
        System.out.println(json);
        mockMvc.perform(post(urlPrefix + "fullUserAgreement/")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().stringValues(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.ATTACHMENT.getCode() + "; filename*=utf-8''" + encodeFileNameToUtf8(ReportController.FULL_PERSON_AGREEMENT_DATA_PROCESSING)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(ResponseBuilderUtil.DOCX_CONTENT_TYPE))
                .andExpect(content().bytes(stubBytes))
        ;
    }
}
