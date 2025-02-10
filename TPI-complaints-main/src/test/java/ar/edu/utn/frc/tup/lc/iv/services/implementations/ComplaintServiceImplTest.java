package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ComplaintEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import ar.edu.utn.frc.tup.lc.iv.repositories.ComplaintRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ComplaintServiceImplTest {

    @InjectMocks
    ComplaintServiceImpl complaintServiceSpy;

    @Mock
    ComplaintRepository complaintRepository;

    @Spy
    ModelMapper modelMapper;

    @Mock
    FileManagerClient fileManagerClient;

    ComplaintEntity complaint1;
    ComplaintEntity complaint2;
    GetReducedComplaintDto dto1;
    GetReducedComplaintDto dto2;
    LocalDateTime fecha1 = LocalDateTime.of(2024, 10, 6, 19, 0);
    LocalDateTime fecha2 = LocalDateTime.of(2024, 10, 6, 21, 30);

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);
        complaint1 = new ComplaintEntity(
                1,
                101,
                201,
                "Uso inapropiado de la parcela",
                null,
                ComplaintState.NEW,
                "Quiero denunciar al lote3A",
                "Nueva",
                new ArrayList<>(),
                LocalDateTime.of(2024, 10, 6, 19, 0),
                10,
                LocalDateTime.of(2024, 10, 6, 20, 0),
                15
        );

        complaint2 = new ComplaintEntity(
                1,
                102,
                1,
                "Uso inapropiado de la parcela",
                null,
                ComplaintState.PENDING,
                "Quiero denunciar al lote5G",
                "Pendiente de Revision",
                new ArrayList<>(),
                LocalDateTime.of(2024, 10, 6, 19, 0),
                10,
                LocalDateTime.of(2024, 10, 6, 20, 0),
                15
        );

        dto1 = new GetReducedComplaintDto();
        dto1.setId(1);
        dto1.setUserId(101);
        dto1.setReportId(201);
        dto1.setComplaintReason("Uso inapropiado de la parcela");
        dto1.setComplaintState("Nueva");
        dto1.setDescription("Quiero denunciar al lote3A");
        dto1.setCreatedDate(LocalDateTime.of(2024, 10, 6, 19, 0));

        dto2 = new GetReducedComplaintDto();
        dto2.setId(1);
        dto2.setUserId(102);
        dto2.setReportId(1);
        dto2.setComplaintReason("Uso inapropiado de la parcela");
        dto2.setComplaintState("Pendiente");
        dto2.setDescription("Quiero denunciar al lote5G");
        dto2.setCreatedDate(LocalDateTime.of(2024, 10, 6, 19, 0));

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void createComplaint_NoFiles_Success() {
        //Given
        PostComplaintDto postComplaintDto = new PostComplaintDto(123456, "Uso inapropiado de la parcela", null, "Descripcion", new ArrayList<>());

        //When
        when(fileManagerClient.uploadFile(any(MultipartFile.class))).thenReturn("113857");
        GetComplaintDto result = complaintServiceSpy.createComplaint(postComplaintDto);

        //Then
        assertNotNull(result);
        assertEquals(postComplaintDto.getUserId(), result.getUserId());
        assertEquals(postComplaintDto.getDescription(), result.getDescription());
        assertEquals("Nueva denuncia", result.getComplaintState());
        verify(complaintRepository, times(1)).save(any());
        verify(fileManagerClient, times(0)).uploadFile(any(MultipartFile.class));
    }
    @Test
    void createComplaint_WithFiles_Success() {
        //Given
        MockMultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "content".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        PostComplaintDto postComplaintDto = new PostComplaintDto(123456, "Uso inapropiado de la parcela",null, "Descripcion", files);

        //When
        when(fileManagerClient.uploadFile(any(MultipartFile.class))).thenReturn("113857");
        GetComplaintDto result = complaintServiceSpy.createComplaint(postComplaintDto);

        //Then
        assertNotNull(result);
        assertEquals(postComplaintDto.getUserId(), result.getUserId());
        assertEquals(postComplaintDto.getDescription(), result.getDescription());
        assertEquals("Nueva denuncia", result.getComplaintState());
        verify(complaintRepository, times(1)).save(any());
        verify(fileManagerClient, times(1)).uploadFile(any(MultipartFile.class));
    }
    @Test
    void createComplaint_WithFiles_Error() {
        //Given
        MockMultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "content".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(file);
        PostComplaintDto postComplaintDto = new PostComplaintDto(123456, "Uso inapropiado de la parcela", null, "Descripcion", files);

        //When
        when(fileManagerClient.uploadFile(any(MultipartFile.class))).thenThrow(new IllegalArgumentException("Error al crear y guardar la imagen"));

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            complaintServiceSpy.createComplaint(postComplaintDto);
        });
        verify(complaintRepository, times(0)).save(any());
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getAllReducedComplaints_Success(){
        //given
        List<ComplaintEntity> complaintEntities = Arrays.asList(complaint1, complaint2);

        //when
        when(complaintRepository.findAll()).thenReturn(complaintEntities);

        //then
        List<GetReducedComplaintDto> result = complaintServiceSpy.getAllReducedComplaints();

        assertEquals(2, result.size());
        assertEquals("Quiero denunciar al lote3A", result.get(0).getDescription());
        assertEquals("Nueva", result.get(0).getComplaintState());
        assertEquals("Quiero denunciar al lote5G", result.get(1).getDescription());
        assertEquals("Pendiente de Revision", result.get(1).getComplaintState());
    }
    @Test
    void getAllReducedComplaints_EntityNotFound(){
        //when
        when(complaintRepository.findAll()).thenReturn(Collections.emptyList());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            complaintServiceSpy.getAllReducedComplaints();
        });
        verify(complaintRepository, times(1)).findAll();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateComplaintState_Success() {
        //given
        Integer complaintId = 1;
        PutComplaintStateDto putComplaintStateDto = new PutComplaintStateDto();
        putComplaintStateDto.setComplaintState(ComplaintState.ATTACHED);
        putComplaintStateDto.setStateReason("Test reason");
        putComplaintStateDto.setUserId(123456);

        ComplaintEntity complaintEntity = new ComplaintEntity();
        complaintEntity.setId(complaintId);
        complaintEntity.setComplaintState(ComplaintState.ATTACHED);
        complaintEntity.setStateReason("Old reason");
        complaintEntity.setUserId(123456);

        //when
        when(complaintRepository.findById(complaintId)).thenReturn(Optional.of(complaintEntity));
        when(complaintRepository.save(any(ComplaintEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        //then
        GetComplaintDto result = complaintServiceSpy.updateComplaintState(complaintId, putComplaintStateDto);

        assertNotNull(result);
        assertEquals(putComplaintStateDto.getComplaintState().getValue(), result.getComplaintState());
        assertEquals(putComplaintStateDto.getStateReason(), result.getStateReason());
        assertEquals(putComplaintStateDto.getUserId(), result.getLastUpdatedUser());
        verify(complaintRepository, times(1)).findById(1);
        verify(complaintRepository, times(1)).save(any(ComplaintEntity.class));
    }
    @Test
    void updateComplaintState_EntityNotFound() {
        //given
        Integer complaintId = 1;
        PutComplaintStateDto putComplaintStateDto = new PutComplaintStateDto();
        putComplaintStateDto.setId(1);

        //when
        when(complaintRepository.findById(1)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            complaintServiceSpy.updateComplaintState(complaintId, putComplaintStateDto);
        });
        verify(complaintRepository, times(1)).findById(1);
        verify(complaintRepository, times(0)).save(any(ComplaintEntity.class));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getComplaintsByReport_Success() {
        //given
        ComplaintEntity complaint = new ComplaintEntity(1, 101, 11, "Uso inapropiado de la parcela",null, ComplaintState.NEW, "Quiero denunciar al lote3A", "Nueva", new ArrayList<>(), LocalDateTime.of(2024, 10, 6, 19, 0), 10, LocalDateTime.of(2024, 10, 6, 20, 0), 15);
        List<ComplaintEntity> complaintEntities = Arrays.asList(complaint, complaint, complaint);

        //when
        when(complaintRepository.findAllByReportId(anyInt())).thenReturn(complaintEntities);

        //then
        List<GetComplaintDto> result = complaintServiceSpy.getComplaintsByReport(complaint.getId());

        assertEquals(3, result.size());
        assertEquals(11, result.get(0).getReportId());

        verify(complaintRepository, times(1)).findAllByReportId(anyInt());
    }
    @Test
    void getComplaintsByReport_EntityNotFound() {
        //when
        when(complaintRepository.findAllByReportId(anyInt())).thenReturn(Collections.emptyList());

        //then
        assertEquals(Collections.emptyList(),complaintServiceSpy.getComplaintsByReport(1));
        verify(complaintRepository, times(1)).findAllByReportId(1);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateComplaintReport_Success() {
        //given
        PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(416, 10, Arrays.asList(1, 2, 3));
        ComplaintEntity complaintNew = new ComplaintEntity(1, 404, null, "Uso inapropiado de la parcela",null, ComplaintState.NEW, "Quiero denunciar al lote3A", "Nueva", new ArrayList<>(), LocalDateTime.of(2024, 10, 6, 19, 0), 10, LocalDateTime.of(2024, 10, 6, 20, 0), 15);
        ComplaintEntity complaintOld = new ComplaintEntity(1, 404, 1, "Uso inapropiado de la parcela",null, ComplaintState.NEW, "Quiero denunciar al lote3A", "Nueva", new ArrayList<>(), LocalDateTime.of(2024, 10, 6, 19, 0), 10, LocalDateTime.of(2024, 10, 6, 20, 0), 15);
        List<ComplaintEntity> complaintOldEntities = Arrays.asList(complaintOld, complaintOld);

        int expectedSaves = putComplaintReportDto.getComplaintIds().size() + complaintOldEntities.size();

        //when
        when(complaintRepository.findAllByReportId(putComplaintReportDto.getReportId())).thenReturn(complaintOldEntities);
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.of(complaintNew));

        //then
        List<GetComplaintDto> result = complaintServiceSpy.updateComplaintReport(putComplaintReportDto);
        assertEquals(putComplaintReportDto.getComplaintIds().size(), result.size());
        assertEquals(416, result.get(0).getReportId());
        verify(complaintRepository, times(1)).findAllByReportId(anyInt());
        verify(complaintRepository, times(putComplaintReportDto.getComplaintIds().size())).findById(anyInt());
        verify(complaintRepository, times(expectedSaves)).save(any(ComplaintEntity.class));
    }
    @Test
    void updateComplaintReport_EmptyComplaintList_EntityNotFound() {
        //given
        PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(1, 10, Collections.emptyList());

        //when
        when(complaintRepository.findAllByReportId(putComplaintReportDto.getReportId())).thenReturn(Collections.emptyList());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            complaintServiceSpy.updateComplaintReport(putComplaintReportDto);
        });
        verify(complaintRepository, times(0)).findAllByReportId(anyInt());
        verify(complaintRepository, times(0)).findById(anyInt());
        verify(complaintRepository, times(0)).save(any(ComplaintEntity.class));
    }
    @Test
    void updateComplaintReport_NoMatchingId_EntityNotFound() {
        //given
        PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(1, 10, Arrays.asList(1, 2, 3));

        //when
        when(complaintRepository.findAllByReportId(putComplaintReportDto.getReportId())).thenReturn(Collections.emptyList());
        when(complaintRepository.findById(anyInt())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            complaintServiceSpy.updateComplaintReport(putComplaintReportDto);
        });
        verify(complaintRepository, times(1)).findAllByReportId(anyInt());
        verify(complaintRepository, times(1)).findById(anyInt());
        verify(complaintRepository, times(0)).save(any(ComplaintEntity.class));
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getComplaintById_Success(){
        //given
        ComplaintEntity complaint = new ComplaintEntity(1, 101, 11, "Uso inapropiado de la parcela",null, ComplaintState.NEW, "Quiero denunciar al lote3A", "Nueva", new ArrayList<>(), LocalDateTime.of(2024, 10, 6, 19, 0), 10, LocalDateTime.of(2024, 10, 6, 20, 0), 15);

        //when
        when(complaintRepository.findById(complaint.getId())).thenReturn(Optional.of(complaint));

        //then
        GetComplaintDto result = complaintServiceSpy.getComplaintById(complaint.getId());

        assertNotNull(result);
        assertEquals(complaint.getId(), result.getId());
        assertEquals(complaint.getUserId(), result.getUserId());
        assertEquals(complaint.getDescription(), result.getDescription());
    }
    @Test
    void getComplaintById_ReturnNull(){
        //given
        ComplaintEntity complaint = new ComplaintEntity(1, 101, 11, "Uso inapropiado de la parcela",null, ComplaintState.NEW, "Quiero denunciar al lote3A", "Nueva", new ArrayList<>(), LocalDateTime.of(2024, 10, 6, 19, 0), 10, LocalDateTime.of(2024, 10, 6, 20, 0), 15);

        //when
        when(complaintRepository.findById(complaint.getId())).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            complaintServiceSpy.getComplaintById(complaint.getId());
        });
        verify(complaintRepository, times(1)).findById(complaint.getId());
    }


}