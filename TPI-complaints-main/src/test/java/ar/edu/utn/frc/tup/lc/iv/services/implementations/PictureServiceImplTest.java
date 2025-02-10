package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.clients.FileManagerClient;
import ar.edu.utn.frc.tup.lc.iv.entities.ComplaintEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.PictureEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import ar.edu.utn.frc.tup.lc.iv.repositories.ComplaintRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.PictureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class PictureServiceImplTest {


    @MockBean
    ComplaintRepository complaintRepository;

    @MockBean
    FileManagerClient fileManagerClient;

    @MockBean
    private PictureRepository pictureRepository;

    @SpyBean
    PictureServiceImpl pictureService;

    ComplaintEntity complaint1;

    @BeforeEach
    void setUp() {
        complaint1 = new ComplaintEntity(
                1,
                101,
                201,
                "Uso inapropiado de la parcela",
                null,
                ComplaintState.NEW,
                "Quiero denunciar al lote3A",
                "Nueva Denuncia",
                new ArrayList<>(),
                LocalDateTime.of(2024, 10, 6, 19, 0),
                10,
                LocalDateTime.of(2024, 10, 6, 20, 0),
                15
        );

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void detectMimeType_JPEG() {
        byte[] fileData = {(byte) 0xFF, (byte) 0xD8, 0, 0};
        String mimeType = pictureService.detectMimeType(fileData);
        assertEquals("image/jpeg", mimeType);
    }

    @Test
    void detectMimeType_Unknown() {
        byte[] fileData = {0, 0, 0, 0};
        String mimeType = pictureService.detectMimeType(fileData);
        assertEquals("unknown", mimeType);
    }

    @Test
    void getUUIDsByComplaintId_Success() {
        int complaintId = 1;
        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setPictureUrl("fileUUID");
        pictureEntity.setComplaint(complaint1);
        List<PictureEntity> pictures = Collections.singletonList(pictureEntity);
        when(pictureRepository.findAll()).thenReturn(pictures);

        List<String> result = pictureService.getUUIDsByComplaintId(complaintId);

        assertEquals(1, result.size());
        assertEquals("fileUUID", result.get(0));
        verify(pictureRepository, times(1)).findAll();
    }

    @Test
    void getUUIDsByComplaintId_NoResults() {
        int complaintId = 1;
        when(pictureRepository.findAll()).thenReturn(Collections.emptyList());

        List<String> result = pictureService.getUUIDsByComplaintId(complaintId);

        assertTrue(result.isEmpty());
        verify(pictureRepository, times(1)).findAll();
    }

    @Test
    void getFilesWithNameByComplaintId_Success() {
        int complaintId = 1;
        String uuid1 = "{\"uuid\":\"fileUUID1\"}";
        String uuid2 = "{\"uuid\":\"fileUUID2\"}";
        String extractedUuid1 = "fileUUID1";
        String extractedUuid2 = "fileUUID2";
        String base64File1 = "data:image/jpeg;base64,testdata1";
        String fileName1 = "file1";
        String base64File2 = "data:image/jpeg;base64,testdata2";
        String fileName2 = "file2";
        Map<String, String> fileWithName1 = Map.of(base64File1, fileName1);
        Map<String, String> fileWithName2 = Map.of(base64File2, fileName2);

        PictureEntity pictureEntity1 = new PictureEntity();
        pictureEntity1.setPictureUrl(uuid1);
        pictureEntity1.setComplaint(complaint1);

        PictureEntity pictureEntity2 = new PictureEntity();
        pictureEntity2.setPictureUrl(uuid2);
        pictureEntity2.setComplaint(complaint1);

        List<PictureEntity> pictures = List.of(pictureEntity1, pictureEntity2);

        when(pictureRepository.findAll()).thenReturn(pictures);
        when(pictureService.getUUIDsByComplaintId(complaintId)).thenReturn(List.of(uuid1, uuid2));

        ResponseEntity<byte[]> response1 = mock(ResponseEntity.class);
        HttpHeaders headers1 = new HttpHeaders();
        headers1.set("fileName", fileName1);
        when(response1.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response1.getBody()).thenReturn(base64File1.getBytes());
        when(response1.getHeaders()).thenReturn(headers1);

        ResponseEntity<byte[]> response2 = mock(ResponseEntity.class);
        HttpHeaders headers2 = new HttpHeaders();
        headers2.set("fileName", fileName2);
        when(response2.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response2.getBody()).thenReturn(base64File2.getBytes());
        when(response2.getHeaders()).thenReturn(headers2);

        when(fileManagerClient.getFileWithNameByUUID(extractedUuid1)).thenReturn(response1);
        when(fileManagerClient.getFileWithNameByUUID(extractedUuid2)).thenReturn(response2);

        when(pictureService.getFileWithNameByUUID(extractedUuid1)).thenReturn(fileWithName1);
        when(pictureService.getFileWithNameByUUID(extractedUuid2)).thenReturn(fileWithName2);

        Map<String, String> result = pictureService.getFilesWithNameByComplaintId(complaintId);

        assertEquals(2, result.size());
        assertEquals(fileName1, result.get(base64File1));
        assertEquals(fileName2, result.get(base64File2));

        verify(pictureRepository, times(1)).findAll();
        verify(pictureService, times(1)).getUUIDsByComplaintId(complaintId);
        verify(fileManagerClient, times(1)).getFileWithNameByUUID(extractedUuid1);
        verify(fileManagerClient, times(1)).getFileWithNameByUUID(extractedUuid2);
    }

    @Test
    void getFilesWithNameByComplaintId_NoResults() {
        int complaintId = 1;
        when(pictureService.getUUIDsByComplaintId(complaintId)).thenReturn(Collections.emptyList());

        Map<String, String> result = pictureService.getFilesWithNameByComplaintId(complaintId);

        assertTrue(result.isEmpty());
        verify(pictureService, times(1)).getUUIDsByComplaintId(complaintId);
    }

}