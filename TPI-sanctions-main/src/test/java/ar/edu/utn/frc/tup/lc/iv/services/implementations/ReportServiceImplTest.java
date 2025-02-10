package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.ComplaintClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportReasonRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class ReportServiceImplTest {
    @SpyBean
    private ReportService reportService;

    @MockBean
    private ReportRepository reportRepository;

    @MockBean
    private ReportReasonRepository reportReasonRepository;

    @MockBean
    private ComplaintClient complaintClient;

    ReportEntity reportEntity;
    ReportEntity reportEntity2;
    GetReducedReportDto reducedReport;
    GetReducedReportDto reducedReport2;

    private PostReportDto postReportDto;
    private PutReportDto putReportDto;
    private ReportEntity reportEntity3;
    private Random random = new Random();

    @BeforeEach
    void setUp() {
        reportEntity = new ReportEntity(1, ReportState.OPEN, "Hola como estas", new ReportReasonEntity(), 1, "description", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        reportEntity2 = new ReportEntity(2, ReportState.CLOSED, "cerrado", new ReportReasonEntity(), 1, "description2", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        reducedReport = new GetReducedReportDto();
        reducedReport2 = new GetReducedReportDto();
        putReportDto = new PutReportDto(
                random.nextInt(),
                random.nextInt(),
                "description",
                new ArrayList<>()
        );
        reportEntity3 = new ReportEntity(
                random.nextInt(),
                ReportState.values()[random.nextInt(ReportState.values().length)], // Random ReportState
                "open",
                new ReportReasonEntity(),
                random.nextInt(),
                "description",
                LocalDateTime.now(),
                random.nextInt(),
                LocalDateTime.now(),
                random.nextInt()
        );

        reducedReport.setReportState(ReportState.OPEN.getValue());
        reducedReport.setPlotId(1);
        reducedReport.setDescription("description");
        reducedReport.setCreatedDate(LocalDateTime.now());

        reducedReport2.setReportState(ReportState.OPEN.getValue());
        reducedReport2.setPlotId(2);
        reducedReport2.setDescription("description2");
        reducedReport2.setCreatedDate(LocalDateTime.now());

    }

    @Test
    void getAllReports(){
        List<ReportEntity> reportEntities = Arrays.asList(reportEntity, reportEntity2);

        when(reportRepository.findAll()).thenReturn(reportEntities);

        List<GetReducedReportDto> result = reportService.getAllReducedReports();

        assertEquals(2, result.size());

        assertEquals("description", result.get(0).getDescription());
        assertEquals("Abierto", result.get(0).getReportState());
        assertEquals("description2", result.get(1).getDescription());
        assertEquals("Cerrado", result.get(1).getReportState());
    }

    @Test
    void getAllReportsIsEmpty(){
        when(reportRepository.findAll()).thenReturn(Collections.emptyList());

        List<GetReducedReportDto> result = reportService.getAllReducedReports();

        assertEquals(result, Collections.emptyList());
        verify(reportRepository, times(1)).findAll();
    }

    @Test
    void putReport_SuccessIT() {
        when(reportRepository.findById(anyInt())).thenReturn(Optional.of(reportEntity3));
        when(reportReasonRepository.findById(anyInt())).thenReturn(Optional.of(new ReportReasonEntity()));
        reportService.putReport(putReportDto);
        verify(reportRepository, times(1)).save(any(ReportEntity.class));
    }

    @Test
    void putReport_ReportNotFoundIT() {
        when(reportRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> reportService.putReport(putReportDto));
    }

    @Test
    void postReport_Success() {
        postReportDto = new PostReportDto(
                1,
                1,
                "description",
                new ArrayList<>(),
                1
        );

        when(reportRepository.save(any(ReportEntity.class))).thenAnswer(invocation -> {
            ReportEntity reportEntity = invocation.getArgument(0);
            reportEntity.setId(1);
            return reportEntity;
        });
        when(reportReasonRepository.findById(anyInt())).thenReturn(Optional.of(new ReportReasonEntity()));
        when(complaintClient.getComplaintsByReportId(anyInt())).thenReturn(Collections.emptyList());

        GetReportDto result = reportService.postReport(postReportDto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(ReportState.PENDING.getValue(), result.getReportState());
        assertEquals("Nuevo informe", result.getStateReason());
        assertEquals(postReportDto.getDescription(), result.getDescription());
    }

    @Test
    void postReport_Failure_ReportReasonNotFound() {
        PostReportDto postReportDto = new PostReportDto(
                999,
                1,
                "Descripción de prueba",
                new ArrayList<>(),
                1
        );

        when(reportReasonRepository.findById(postReportDto.getReportReasonId()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            reportService.postReport(postReportDto);
        });
    }

    @Test
    void putReportState_Success() {
        Integer reportId = 1;
        PutReportStateDto putReportStateDto = new PutReportStateDto();
        putReportStateDto.setId(reportId);
        putReportStateDto.setReportState(ReportState.CLOSED);
        putReportStateDto.setStateReason("Informe completado");
        putReportStateDto.setUserId(1);

        ReportEntity existingReport = new ReportEntity();
        existingReport.setId(reportId);
        existingReport.setReportState(ReportState.PENDING);
        existingReport.setStateReason("Nuevo informe");

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(existingReport));
        when(reportRepository.save(any(ReportEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(complaintClient.getComplaintsByReportId(reportId)).thenReturn(Collections.emptyList());

        GetReportDto result = reportService.putReportState(putReportStateDto);

        assertNotNull(result);
        assertEquals(reportId, result.getId());
        assertEquals(ReportState.CLOSED.getValue(), result.getReportState());
        assertEquals("Informe completado", result.getStateReason());
        assertEquals(putReportStateDto.getUserId(), result.getLastUpdatedUser());
        assertNotNull(result.getLastUpdatedDate());
    }

    @Test
    void putReportState_Failure_ReportNotFound() {
        Integer reportId = 999;
        PutReportStateDto putReportStateDto = new PutReportStateDto();
        putReportStateDto.setId(reportId);
        putReportStateDto.setReportState(ReportState.CLOSED);
        putReportStateDto.setStateReason("Informe completado");
        putReportStateDto.setUserId(1);

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        assertNull(reportService.putReportState(putReportStateDto));
    }

    @Test
    void getById_Success() {
        Integer reportId = 1;
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setId(reportId);
        reportEntity.setReportState(ReportState.PENDING);
        reportEntity.setStateReason("Nuevo informe");
        reportEntity.setDescription("Descripción del reporte");

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(reportEntity));
        when(complaintClient.getComplaintsByReportId(reportId)).thenReturn(Collections.emptyList());

        GetReportDto result = reportService.getById(reportId);

        assertNotNull(result);
        assertEquals(reportId, result.getId());
        assertEquals(ReportState.PENDING.getValue(), result.getReportState());
        assertEquals("Nuevo informe", result.getStateReason());
        assertEquals("Descripción del reporte", result.getDescription());
        assertTrue(result.getComplaints().isEmpty());
    }

    @Test
    void getById_Failure_ReportNotFound() {
        Integer reportId = 999;

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        GetReportDto result = reportService.getById(reportId);

        assertNull(result);
    }

}