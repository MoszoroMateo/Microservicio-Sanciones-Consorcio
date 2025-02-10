package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutReportReasonDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportReasonRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ReportReasonService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

class ReportReasonServiceImplTest {

    @InjectMocks
    private ReportReasonServiceImpl reportReasonService;

    @Mock
    private ReportReasonRepository reportReasonRepository;

    @Spy
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReportReasons_Success() {
        //given
        ReportReasonEntity reportReasonEntity = new ReportReasonEntity(1, "Motivo de informe", 1000.0, LocalDateTime.now(), 101, LocalDateTime.now(), 102);
        List<ReportReasonEntity> reportReasonEntities = Arrays.asList(reportReasonEntity, reportReasonEntity, reportReasonEntity);

        //when
        when(reportReasonRepository.findAll()).thenReturn(reportReasonEntities);

        //then
        List<GetReportReasonDto> result = reportReasonService.getAllReportReasons();

        assertNotNull(result);
        assertEquals(reportReasonEntities.size(), result.size());
        assertEquals(reportReasonEntities.get(0).getReportReason(), result.get(0).getReportReason());
        verify(reportReasonRepository, times(1)).findAll();
    }
    @Test
    void getAllReportReasons_EntityNotFound() {
        //when
        when(reportReasonRepository.findAll()).thenReturn(Collections.emptyList());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            reportReasonService.getAllReportReasons();
        });
        verify(reportReasonRepository, times(1)).findAll();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getReportReasonById_Success() {
        //given
        ReportReasonEntity reportReasonEntity = new ReportReasonEntity(1, "Motivo de informe", 1000.0, LocalDateTime.now(), 101, LocalDateTime.now(), 102);
        GetReportReasonDto response =  new GetReportReasonDto(reportReasonEntity.getId(), reportReasonEntity.getReportReason(), reportReasonEntity.getBaseAmount());

        //when
        when(reportReasonRepository.findById(1)).thenReturn(Optional.of(reportReasonEntity));
        when(modelMapper.map(reportReasonEntity, GetReportReasonDto.class)).thenReturn(response);

        //then
        GetReportReasonDto result = reportReasonService.getReportReasonById(1);

        assertNotNull(result);
        assertEquals(reportReasonEntity.getReportReason(), result.getReportReason());
        verify(reportReasonRepository, times(1)).findById(1);
    }
    @Test
    void getReportReasonById_EntityNotFound() {
        //when
        when(reportReasonRepository.findById(1)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            reportReasonService.getReportReasonById(1);
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void addReportReason_Success() {
        //given
        ReportReasonEntity reportReasonEntity = new ReportReasonEntity(1, "Nuevo motivo de informe", 1000.0, LocalDateTime.now(), 101, LocalDateTime.now(), 102);
        PostReportReasonDto postReportReasonDto = new PostReportReasonDto("Nuevo motivo de informe", 1000.0, 101);

        //when
        when(reportReasonRepository.findReportReasonEntityByReportReason(anyString())).thenReturn(Optional.empty());
        when(reportReasonRepository.save(any())).thenReturn(reportReasonEntity);

        //then
        GetReportReasonDto result = reportReasonService.addReportReason(postReportReasonDto);

        assertNotNull(result);
        assertEquals(reportReasonEntity.getReportReason(), result.getReportReason());
        verify(reportReasonRepository, times(1)).findReportReasonEntityByReportReason(anyString());
        verify(reportReasonRepository, times(1)).save(any());
    }
    @Test
    void addReportReason_EntityAlreadyExists() {
        //given
        ReportReasonEntity reportReasonEntity = new ReportReasonEntity(1, "Nuevo motivo de informe", 1000.0, LocalDateTime.now(), 101, LocalDateTime.now(), 102);
        PostReportReasonDto postReportReasonDto = new PostReportReasonDto("Nuevo motivo de informe", 1000.0, 101);

        //when
        when(reportReasonRepository.findReportReasonEntityByReportReason(anyString())).thenReturn(Optional.of(reportReasonEntity));

        //then
        assertThrows(EntityExistsException.class, () ->
                reportReasonService.addReportReason(postReportReasonDto)
        );
        verify(reportReasonRepository, times(1)).findReportReasonEntityByReportReason(anyString());
        verify(reportReasonRepository, times(0)).save(any());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateReportReason_Success() {
        //given
        ReportReasonEntity reportReasonEntity = new ReportReasonEntity(1, "Viejo motivo de informe", 1000.0, LocalDateTime.now(), 101, LocalDateTime.now(), 100);
        PutReportReasonDto putReportReasonDto = new PutReportReasonDto( 1,"Nuevo motivo de informe", 1000.0, 101);

        //when
        when(reportReasonRepository.findById(1)).thenReturn(Optional.of(reportReasonEntity));

        //then
        GetReportReasonDto result = reportReasonService.updateReportReason(putReportReasonDto);

        assertNotNull(result);
        assertEquals(putReportReasonDto.getReportReason(), result.getReportReason());
        verify(reportReasonRepository, times(1)).findById(anyInt());
        verify(reportReasonRepository, times(1)).save(any());
    }
    @Test
    void updateReportReason_EntityNotFound() {
        //given
        PutReportReasonDto putReportReasonDto = new PutReportReasonDto( 1,"Nuevo motivo de informe", 1000.0, 101);

        //when
        when(reportReasonRepository.findById(1)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () ->
                reportReasonService.updateReportReason(putReportReasonDto)
        );
        verify(reportReasonRepository, times(1)).findById(anyInt());
        verify(reportReasonRepository, times(0)).save(any());
    }

}