package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportReasonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportReasonController.class)
@AutoConfigureWebMvc
class ReportReasonControllerTest {
    private static final String BASE_PATH = "/api/report-reason";
    private static final String REASONS_ALL = BASE_PATH + "/all";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportReasonService reportReasonService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportReasonController reportController;

    private GetReportReasonDto reasonDto1;
    private GetReportReasonDto reasonDto2;
    private GetReportReasonDto reasonDto3;

    @BeforeEach
    void setUp(){
        reasonDto1 = new GetReportReasonDto(1, "Ruidos molestos", 100.00);
        reasonDto2 = new GetReportReasonDto(2, "Falta de Mantenimiento", 50.00);
        reasonDto3 = new GetReportReasonDto(3, "Destruccion de propiedad", 800.00);
    }

    @Test
    void getAllReasons() throws Exception {
        List<GetReportReasonDto> reportList = List.of(reasonDto1,reasonDto2,reasonDto3);
        when(reportReasonService.getAllReportReasons()).thenReturn(reportList);

        mockMvc.perform(MockMvcRequestBuilders.get(REASONS_ALL).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].reportReason").value("Ruidos molestos"))
                .andExpect(jsonPath("$.[0].baseAmount").value(100.00))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].reportReason").value("Falta de Mantenimiento"))
                .andExpect(jsonPath("$.[1].baseAmount").value(50.00))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[2].reportReason").value("Destruccion de propiedad"))
                .andExpect(jsonPath("$.[2].baseAmount").value(800.00));
    }

    @Test
    void getReasonById() throws Exception{
        when(reportReasonService.getReportReasonById(1)).thenReturn(reasonDto1);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reportReason").value("Ruidos molestos"))
                .andExpect(jsonPath("$.baseAmount").value(100.00));
    }
}