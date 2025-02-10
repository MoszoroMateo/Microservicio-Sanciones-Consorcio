package ar.edu.utn.frc.tup.lc.iv.clients;

import static org.junit.jupiter.api.Assertions.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ComplaintClientTest {


    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ComplaintClient complaintClient;

    private static final String BASE_URL = "http://complaint-service";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(complaintClient, "url", BASE_URL);
    }

    @Test
    void getComplaintById_Success() {
        Integer complaintId = 1;
        GetComplaintDto expectedComplaint = new GetComplaintDto();
        ResponseEntity<GetComplaintDto> responseEntity = new ResponseEntity<>(expectedComplaint, HttpStatus.OK);

        when(restTemplate.getForEntity(
                BASE_URL + "/api/complaint/" + complaintId,
                GetComplaintDto.class
        )).thenReturn(responseEntity);

        GetComplaintDto result = complaintClient.getComplaintById(complaintId);
        assertNotNull(result);
        assertEquals(expectedComplaint, result);
    }

    @Test
    void getComplaintById_Error() {
        Integer complaintId = 1;
        ResponseEntity<GetComplaintDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        when(restTemplate.getForEntity(
                BASE_URL + "/api/complaint/" + complaintId,
                GetComplaintDto.class
        )).thenReturn(responseEntity);

        assertThrows(ResponseStatusException.class, () -> complaintClient.getComplaintById(complaintId));
    }

    @Test
    void getComplaintsByReportId_Success() {
        Integer reportId = 1;
        GetComplaintDto[] complaints = new GetComplaintDto[]{new GetComplaintDto(), new GetComplaintDto()};
        ResponseEntity<GetComplaintDto[]> responseEntity = new ResponseEntity<>(complaints, HttpStatus.OK);

        when(restTemplate.getForEntity(
                BASE_URL + "/api/complaint/report/" + reportId,
                GetComplaintDto[].class
        )).thenReturn(responseEntity);

        List<GetComplaintDto> result = complaintClient.getComplaintsByReportId(reportId);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void appendComplaint_Success() {
        PutComplaintReportDto putDto = new PutComplaintReportDto();
        GetComplaintDto[] complaints = new GetComplaintDto[]{new GetComplaintDto()};

        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<GetComplaintDto[]> responseEntity = new ResponseEntity<>(complaints, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(BASE_URL + "/api/complaint/append"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(GetComplaintDto[].class)
        )).thenReturn(responseEntity);

        List<GetComplaintDto> result = complaintClient.appendComplaint(putDto);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void appendComplaint_Error() {
        PutComplaintReportDto putDto = new PutComplaintReportDto();
        ResponseEntity<GetComplaintDto[]> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(
                eq(BASE_URL + "/api/complaint/append"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(GetComplaintDto[].class)
        )).thenReturn(responseEntity);
        List<GetComplaintDto> result = complaintClient.appendComplaint(putDto);

        assertTrue(result.isEmpty());
    }

}