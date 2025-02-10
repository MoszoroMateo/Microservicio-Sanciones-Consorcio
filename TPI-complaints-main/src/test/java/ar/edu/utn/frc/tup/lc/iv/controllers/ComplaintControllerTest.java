package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostComplaintDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintReportDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutComplaintStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ComplaintState;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.ComplaintService;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.PictureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComplaintController.class)
@AutoConfigureWebMvc
@TestPropertySource(locations = "classpath:application.properties")
class ComplaintControllerTest {

    private static final String BASE_PATH = "/api/complaint";
    private static final String COMPLAINT_ALL = BASE_PATH + "/all";
    private static final String COMPLAINT_PLOT = BASE_PATH + "/plot/{plot}";
    private static final String COMPLAINT_STATE = BASE_PATH + "/state/{state}";
    private static final String COMPLAINT_ID = BASE_PATH + "/{id}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComplaintService complaintService;

    @MockBean
    private PictureService pictureService;


    @Autowired
    private ObjectMapper objectMapper;


    private GetReducedComplaintDto mockReducedComplaint;
    private GetReducedComplaintDto mockReducedComplaint2;
    private GetReducedComplaintDto mockReducedComplaint3;
    private GetReducedComplaintDto mockReducedComplaint4;
    private GetReducedComplaintDto mockReducedComplaint5;
    private GetReducedComplaintDto mockReducedComplaint6;
    private GetReducedComplaintDto mockReducedComplaint7;
    private GetComplaintDto mockComplaintDto;
    private List<GetReducedComplaintDto> mockReducedComplaintList;
    private Map<String, String> mockComplaintTypes;

    @BeforeEach
    void setUp() {
        // Set values in Complaints Dtos
        // Reduced Complaint 1
        mockReducedComplaint = new GetReducedComplaintDto();
        mockReducedComplaint.setUserId(1);
        mockReducedComplaint.setReportId(null);
        mockReducedComplaint.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint.setComplaintState("Nuevo");
        mockReducedComplaint.setDescription("denuncia al lote 1a");
        mockReducedComplaint.setCreatedDate(LocalDateTime.now().minusDays(6));
        // Reduced Complaint 2
        mockReducedComplaint2 = new GetReducedComplaintDto();
        mockReducedComplaint2.setUserId(2);
        mockReducedComplaint2.setReportId(null);
        mockReducedComplaint2.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint2.setComplaintState("Nuevo");
        mockReducedComplaint2.setDescription("denuncia al lote 2a");
        mockReducedComplaint2.setCreatedDate(LocalDateTime.now().minusDays(5));
        // Reduced Complaint 3
        mockReducedComplaint3 = new GetReducedComplaintDto();
        mockReducedComplaint3.setUserId(3);
        mockReducedComplaint3.setReportId(null);
        mockReducedComplaint3.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint3.setComplaintState("Pendiente");
        mockReducedComplaint3.setDescription("denuncia al lote 3a");
        mockReducedComplaint3.setCreatedDate(LocalDateTime.now().minusDays(4));
        // Reduced Complaint 4
        mockReducedComplaint4 = new GetReducedComplaintDto();
        mockReducedComplaint4.setUserId(4);
        mockReducedComplaint4.setReportId(null);
        mockReducedComplaint4.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint4.setComplaintState("Pendiente");
        mockReducedComplaint4.setDescription("denuncia al lote 4a");
        mockReducedComplaint4.setCreatedDate(LocalDateTime.now().minusDays(4));
        // Reduced Complaint 5
        mockReducedComplaint5 = new GetReducedComplaintDto();
        mockReducedComplaint5.setUserId(5);
        mockReducedComplaint5.setReportId(1);
        mockReducedComplaint5.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint5.setComplaintState("Anexada");
        mockReducedComplaint5.setDescription("denuncia al lote 1b");
        mockReducedComplaint5.setCreatedDate(LocalDateTime.now().minusDays(2));
        // Reduced Complaint 6
        mockReducedComplaint6 = new GetReducedComplaintDto();
        mockReducedComplaint6.setUserId(6);
        mockReducedComplaint6.setReportId(1);
        mockReducedComplaint6.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint6.setComplaintState("Anexada");
        mockReducedComplaint6.setDescription("denuncia al lote 1b");
        mockReducedComplaint6.setCreatedDate(LocalDateTime.now().minusDays(1));
        // Reduced Complaint 7
        mockReducedComplaint7 = new GetReducedComplaintDto();
        mockReducedComplaint7.setUserId(7);
        mockReducedComplaint7.setReportId(null);
        mockReducedComplaint7.setComplaintReason("Uso inapropiado de la parcela");
        mockReducedComplaint7.setComplaintState("Rechazada");
        mockReducedComplaint7.setDescription("denuncia al lote 1b");
        mockReducedComplaint7.setCreatedDate(LocalDateTime.now().minusDays(1));

        // Added complaints to mockReducedComplaintList
        mockReducedComplaintList = new ArrayList<>();
        mockReducedComplaintList.add(mockReducedComplaint);
        mockReducedComplaintList.add(mockReducedComplaint2);
        mockReducedComplaintList.add(mockReducedComplaint3);
        mockReducedComplaintList.add(mockReducedComplaint4);
        mockReducedComplaintList.add(mockReducedComplaint5);
        mockReducedComplaintList.add(mockReducedComplaint6);
        mockReducedComplaintList.add(mockReducedComplaint7);

        // Complaint
        mockComplaintDto = new GetComplaintDto();
        mockComplaintDto.setId(1);
        mockComplaintDto.setUserId(1);
        mockComplaintDto.setReportId(null);
        mockComplaintDto.setComplaintReason("Uso inapropiado de la parcela");
        mockComplaintDto.setComplaintState("Pendiente");
        mockComplaintDto.setDescription("Denuncia sobre lote 2c");
        mockComplaintDto.setStateReason(null);
        mockComplaintDto.setCreatedDate(LocalDateTime.now().minusDays(1));
        mockComplaintDto.setCreatedUser(1);
        mockComplaintDto.setLastUpdatedDate(LocalDateTime.now().minusDays(1));
        mockComplaintDto.setLastUpdatedUser(1);

    }


    @Test
    void postComplaint_Success() throws Exception {
        //given
        GetComplaintDto getDto = new GetComplaintDto(100, 1, 1, "tipo",null, "estado", "descripcion", "razon", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        PostComplaintDto postDto = new PostComplaintDto(1, "Uso inapropiado de la parcela", null, "descripcion", new ArrayList<>());

        //when
        when(complaintService.createComplaint(any(PostComplaintDto.class))).thenReturn(getDto);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.complaintReason").value("tipo"))
                .andExpect(jsonPath("$.description").value("descripcion"));
        verify(complaintService, times(1)).createComplaint(any(PostComplaintDto.class));
    }
    @Test
    void postComplaint_BadRequestIT() throws Exception {
        List<MultipartFile> files = new ArrayList<>();
        //given
        PostComplaintDto postDto = new PostComplaintDto(999, "Uso inapropiado de la parcela",null, "descripcion", files);

        //when
        when(complaintService.createComplaint(any(PostComplaintDto.class))).thenThrow(new IllegalArgumentException());

        //then
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest());
        verify(complaintService, times(1)).createComplaint(any(PostComplaintDto.class));
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getAllComplaintStates_Success() throws Exception {
        //given
        Map<String, String> lstComplaintStates = new HashMap<>() {{put("NEW", "Nueva"); put("PENDING", "Pendiente");}};

        //when
        when(complaintService.getAllComplaintStates()).thenReturn(lstComplaintStates);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/states")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.NEW").value("Nueva"))
                .andExpect(jsonPath("$.PENDING").value("Pendiente"));
        verify(complaintService, times(1)).getAllComplaintStates();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getAllReducedComplaints_Success() throws Exception {
        //when
        when(complaintService.getAllReducedComplaints()).thenReturn(mockReducedComplaintList);

        //then
        mockMvc.perform(get(COMPLAINT_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].description").value("denuncia al lote 1a"))
                        .andExpect(jsonPath("$.size()").value(7));

        verify(complaintService, times(1)).getAllReducedComplaints();
    }
    @Test
    void getAllReducedComplaints_EntityNotFound() throws Exception {
        //when
        when(complaintService.getAllReducedComplaints()).thenThrow(new EntityNotFoundException());

        //then
        mockMvc.perform(get(COMPLAINT_ALL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(complaintService, times(1)).getAllReducedComplaints();
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getComplaintsById_Success() throws Exception {
        //when
        when(complaintService.getComplaintById(1)).thenReturn(mockComplaintDto);

        mockMvc.perform(get(COMPLAINT_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(complaintService, times(1)).getComplaintById(1);
    }
    @Test
    void getComplaintsById_EntityNotFound() throws Exception {
        //when
        when(complaintService.getComplaintById(55)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(get(COMPLAINT_ID, 55)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(complaintService, times(1)).getComplaintById(55);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void PutComplaintState_Success() throws Exception{
        //given
        Integer id = 1;
        PutComplaintStateDto putComplaintStateDto = new PutComplaintStateDto();
        putComplaintStateDto.setId(id);
        putComplaintStateDto.setUserId(100);
        putComplaintStateDto.setComplaintState(ComplaintState.PENDING);
        putComplaintStateDto.setStateReason("Reason");

        GetComplaintDto returnedComplaint = new GetComplaintDto();
        returnedComplaint.setUserId(100);
        returnedComplaint.setComplaintState("Pendiente");
        returnedComplaint.setReportId(50);

        //when
        when(complaintService.updateComplaintState(id, putComplaintStateDto)).thenReturn(returnedComplaint);

        //then
        mockMvc.perform(put(BASE_PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putComplaintStateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(100));
        verify(complaintService, times(1)).updateComplaintState(id, putComplaintStateDto);
    }
    @Test
    void PutComplaintState_EntityNotFound() throws Exception {
        //given
        Integer id = 1;
        PutComplaintStateDto putComplaintStateDto = new PutComplaintStateDto();
        putComplaintStateDto.setId(id);
        putComplaintStateDto.setUserId(100);
        putComplaintStateDto.setComplaintState(ComplaintState.PENDING);
        putComplaintStateDto.setStateReason("Reason");

        //when
        when(complaintService.updateComplaintState(id, putComplaintStateDto)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(put(BASE_PATH + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putComplaintStateDto)))
                .andExpect(status().isNotFound());
        verify(complaintService, times(1)).updateComplaintState(id, putComplaintStateDto);
    }



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void getComplaintsByReport_Success() throws Exception {
        //given
        GetComplaintDto getDto = new GetComplaintDto(100, 1, 1, "tipo",null, "estado", "descripcion", "razon", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        List<GetComplaintDto> complaintsDtos = Arrays.asList(getDto, getDto, getDto);

        //when
        when(complaintService.getComplaintsByReport(1)).thenReturn(complaintsDtos);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/report/" + getDto.getReportId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
        verify(complaintService, times(1)).getComplaintsByReport(1);
    }
    @Test
    void getComplaintsByReport_EntityNotFound() throws Exception {
        //given
        GetComplaintDto getDto = new GetComplaintDto(100, 1, 1, "tipo",null, "estado", "descripcion", "razon", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        List<GetComplaintDto> complaintsDtos = Arrays.asList(getDto, getDto, getDto);

        //when
        when(complaintService.getComplaintsByReport(1)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/report/" + getDto.getReportId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(complaintService, times(1)).getComplaintsByReport(1);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void appendComplaints_Success() throws Exception {
        //given
        PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(1, 11, Arrays.asList(1, 2, 3));
        GetComplaintDto getDto = new GetComplaintDto(100, 1, 1, "tipo",null, "estado", "descripcion", "razon", LocalDateTime.now(), 1, LocalDateTime.now(), 1);
        List<GetComplaintDto> complaintsDtos = Arrays.asList(getDto, getDto, getDto);

        //when
        when(complaintService.updateComplaintReport(putComplaintReportDto)).thenReturn(complaintsDtos);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/append")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putComplaintReportDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$.size()").value(3));
        verify(complaintService, times(1)).updateComplaintReport(putComplaintReportDto);
    }
    @Test
    void appendComplaints_EntityNotFound() throws Exception {
        //given
        PutComplaintReportDto putComplaintReportDto = new PutComplaintReportDto(1, 11, Arrays.asList(1, 2, 3));

        //when
        when(complaintService.updateComplaintReport(putComplaintReportDto)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/append")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putComplaintReportDto)))
                .andExpect(status().isNotFound());
        verify(complaintService, times(1)).updateComplaintReport(putComplaintReportDto);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    void getFiles_Success() throws Exception {
        int complaintId = 1;
        Map<String, String> files = new HashMap<>();
        files.put("data:image/jpeg;base64,testdata1", "file1");
        files.put("data:image/jpeg;base64,testdata2", "file2");

        when(pictureService.getFilesWithNameByComplaintId(complaintId)).thenReturn(files);

        mockMvc.perform(get(BASE_PATH + "/getFiles/" + complaintId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['data:image/jpeg;base64,testdata1']").value("file1"))
                .andExpect(jsonPath("$.['data:image/jpeg;base64,testdata2']").value("file2"));
        verify(pictureService, times(1)).getFilesWithNameByComplaintId(complaintId);
    }

    @Test
    void getFiles_BadRequest() throws Exception {
        int complaintId = 1;

        when(pictureService.getFilesWithNameByComplaintId(complaintId)).thenReturn(new HashMap<>());

        mockMvc.perform(get(BASE_PATH + "/getFiles/" + complaintId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(pictureService, times(1)).getFilesWithNameByComplaintId(complaintId);
    }


}