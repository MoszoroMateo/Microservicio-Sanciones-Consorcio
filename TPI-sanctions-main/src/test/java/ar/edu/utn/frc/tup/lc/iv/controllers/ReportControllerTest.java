package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cglib.core.Local;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
@AutoConfigureWebMvc
class ReportControllerTest {

    private static final String BASE_PATH = "/api/report";
    private static final String REPORT_ALL = BASE_PATH + "/all";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReportService reportService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReportController reportController;

    private GetReducedReportDto reducedReport;
    private GetReducedReportDto reducedReport2;
    private GetReducedReportDto reducedReport3;
    private GetReducedReportDto reducedReport4;
    private PutReportDto putReportDto;
    private GetReportDto getReportDto;
    private List<GetReducedReportDto> allReducedReports;

    @BeforeEach
    void setUp() {
        reducedReport = new GetReducedReportDto();
        reducedReport2 = new GetReducedReportDto();
        reducedReport3 = new GetReducedReportDto();
        reducedReport4 = new GetReducedReportDto();
        putReportDto = new PutReportDto();
        getReportDto = new GetReportDto();

        reducedReport.setReportState(ReportState.OPEN.getValue());
        reducedReport.setPlotId(1);
        reducedReport.setDescription("description");
        reducedReport.setCreatedDate(LocalDateTime.now());

        reducedReport2.setReportState(ReportState.OPEN.getValue());
        reducedReport2.setPlotId(2);
        reducedReport2.setDescription("description2");
        reducedReport2.setCreatedDate(LocalDateTime.now());

        reducedReport3.setReportState(ReportState.OPEN.getValue());
        reducedReport3.setPlotId(3);
        reducedReport3.setDescription("description3");
        reducedReport3.setCreatedDate(LocalDateTime.now());

        reducedReport4.setReportState(ReportState.OPEN.getValue());
        reducedReport4.setPlotId(4);
        reducedReport4.setDescription("description4");
        reducedReport4.setCreatedDate(LocalDateTime.now());

        allReducedReports = new ArrayList<>();
        allReducedReports.add(reducedReport);
        allReducedReports.add(reducedReport2);
        allReducedReports.add(reducedReport3);
        allReducedReports.add(reducedReport4);

    }

    @Test
    void getAllReports_SuccessIT() throws Exception {

        //when
        when(reportService.getAllReducedReports()).thenReturn(allReducedReports);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(REPORT_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$.[0].description").value("description"))
                .andExpect(jsonPath("$.[1].description").value("description2"))
                .andExpect(jsonPath("$.[2].description").value("description3"))
                .andExpect(jsonPath("$.[3].description").value("description4"));
    }

    @Test
    void getAllReports_BadRequestIT() throws Exception {
        //given
        List<GetReducedReportDto> reducedReportDtos = null;

        //when
        when(reportService.getAllReducedReports()).thenReturn(reducedReportDtos);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(REPORT_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putReport_SuccessIT() throws Exception {
        //given
        when(reportService.putReport(putReportDto)).thenReturn(getReportDto);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH)
                        .content(objectMapper.writeValueAsString(putReportDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getReportDto.getId()));
    }

    @Test
    void putReport_BadRequestIT() throws Exception {
        //given
        when(reportService.putReport(putReportDto)).thenReturn(null);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH)
                        .content(objectMapper.writeValueAsString(putReportDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postReportSuccess() throws Exception {
        // Preparar datos de prueba
        GetReportReasonDto reasonDto = new GetReportReasonDto(1, "Ruidos molestos", 100.00);
        PostReportDto postReport = new PostReportDto(5, 1, "Ruidos molestos", null, 3);
        LocalDateTime time = LocalDateTime.now();
        GetReportDto getReport = new GetReportDto(1, "Pendiente", "Reporte pendiente", reasonDto,
                1, "Ruidos molestos", null, time, 3, time, 3);

        when(reportService.postReport(any(PostReportDto.class))).thenReturn(getReport);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postReport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reportState").value("Pendiente"))
                .andExpect(jsonPath("$.stateReason").value("Reporte pendiente"))
                .andExpect(jsonPath("$.reportReason.id").value(1))
                .andExpect(jsonPath("$.reportReason.reportReason").value("Ruidos molestos"))
                .andExpect(jsonPath("$.plotId").value(1))
                .andExpect(jsonPath("$.description").value("Ruidos molestos"))
                .andExpect(jsonPath("$.complaints").isEmpty())
                .andExpect(jsonPath("$.createdUser").value(3))
                .andExpect(jsonPath("$.lastUpdatedUser").value(3));
    }

    @Test
    void updateReportStateSuccess() throws Exception {
        PutReportStateDto reportStateDto = new PutReportStateDto(1, ReportState.OPEN, "Reporte abierto", 5);
        GetReportReasonDto reasonDto = new GetReportReasonDto(1, "Ruidos molestos", 100.00);
        GetReportDto getReport = new GetReportDto(1, "Pendiente", "Reporte pendiente", reasonDto,
                1, "Ruidos molestos", null, null, 3, null, 3);

        when(reportService.putReportState(any(PutReportStateDto.class))).thenReturn(getReport);

        mockMvc.perform(put(BASE_PATH+"/state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportStateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reportState").value("Pendiente"))
                .andExpect(jsonPath("$.stateReason").value("Reporte pendiente"))
                .andExpect(jsonPath("$.reportReason.id").value(1))
                .andExpect(jsonPath("$.reportReason.reportReason").value("Ruidos molestos"))
                .andExpect(jsonPath("$.plotId").value(1))
                .andExpect(jsonPath("$.description").value("Ruidos molestos"))
                .andExpect(jsonPath("$.complaints").isEmpty())
                .andExpect(jsonPath("$.createdUser").value(3))
                .andExpect(jsonPath("$.lastUpdatedUser").value(3));
    }

    @Test
    void getReportByIdSuccess() throws Exception {
        GetReportReasonDto reasonDto = new GetReportReasonDto(1, "Ruidos molestos", 100.00);
        GetReportDto getReport = new GetReportDto(1, "Pendiente", "Reporte pendiente", reasonDto,
                1, "Ruidos molestos", null, null, 3, null, 3);

        when(reportService.getById(1)).thenReturn(getReport);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reportState").value("Pendiente"))
                .andExpect(jsonPath("$.stateReason").value("Reporte pendiente"))
                .andExpect(jsonPath("$.reportReason.id").value(1))
                .andExpect(jsonPath("$.reportReason.reportReason").value("Ruidos molestos"))
                .andExpect(jsonPath("$.plotId").value(1))
                .andExpect(jsonPath("$.description").value("Ruidos molestos"))
                .andExpect(jsonPath("$.complaints").isEmpty())
                .andExpect(jsonPath("$.createdUser").value(3))
                .andExpect(jsonPath("$.lastUpdatedUser").value(3));
    }

}