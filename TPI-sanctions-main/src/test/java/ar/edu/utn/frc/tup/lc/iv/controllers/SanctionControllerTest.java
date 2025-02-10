package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetReducedFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetSanctionDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostWarningDto;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.SanctionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SanctionController.class)
@AutoConfigureWebMvc
class SanctionControllerTest {
    private static final String BASE_PATH = "/api/sanction";
    private static final String SANCTION_ALL = BASE_PATH + "/all";
    private static final String POST_FINE = BASE_PATH + "/fine";
    private static final String POST_WARNING = BASE_PATH + "/warning";
    private static final String GET_BY_ID = BASE_PATH + "/fine/{id}";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SanctionService sanctionService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SanctionController sanctionController;

    GetSanctionDto sanction1;
    GetSanctionDto sanction2;
    GetSanctionDto sanction3;
    GetSanctionDto sanction4;
    GetSanctionDto sanction5;
    GetSanctionDto sanction6;
    List<GetSanctionDto> getAllSanctions = new ArrayList<>();
    List<GetSanctionDto> getSanctionsPlot = new ArrayList<>();

    @BeforeEach
    void setUp(){
        sanction1 = new GetSanctionDto(
                1,
                "Pendiente de pago",
                1001,
                "Falta de limpieza en su lote",
                100.50,
                LocalDateTime.now(),
                false,
                1
        );
        sanction2 = new GetSanctionDto(
                2,
                "Pagada",
                1002,
                "Ruidos Molestos",
                200.00,
                LocalDateTime.now(),
                false,
                1
        );
        sanction3 = new GetSanctionDto(
                3,
                null,
                1001,
                "Falta de limpieza en su lote",
                null,
                LocalDateTime.now(),
                false,
                1
        );
        sanction4 = new GetSanctionDto(
                4,
                null,
                1002,
                "Ruidos Molestos",
                null,
                LocalDateTime.now(),
                false,
                1
        );

        getAllSanctions.add(sanction1);
        getAllSanctions.add(sanction2);
        getAllSanctions.add(sanction3);
        getAllSanctions.add(sanction4);

        sanction5 = new GetSanctionDto(
                5,
                "Apelada",
                1005,
                "Falta de limpieza en su lote",
                500.0,
                LocalDateTime.now(),
                false,
                1
        );
        sanction6 = new GetSanctionDto(
                6,
                null,
                1005,
                "Falta de limpieza en su lote",
                null,
                LocalDateTime.now(),
                false,
                1
        );

        getSanctionsPlot.add(sanction5);
        getSanctionsPlot.add(sanction6);
    }

    @Test
    void getAllReducedSanctions_Success_NoParameter() throws Exception{
        //when
        when(sanctionService.getAllReducedSanctions(null)).thenReturn(getAllSanctions);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(SANCTION_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$.[0].description").value("Falta de limpieza en su lote"))
                .andExpect(jsonPath("$.[1].description").value("Ruidos Molestos"))
                .andExpect(jsonPath("$.[2].description").value("Falta de limpieza en su lote"))
                .andExpect(jsonPath("$.[3].description").value("Ruidos Molestos"));
    }

    @Test
    void getAllReducedSanctions_Success_PlotParameter() throws Exception{

        Integer plotId = 1005;

        //when
        when(sanctionService.getAllReducedSanctions(plotId)).thenReturn(getSanctionsPlot);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(SANCTION_ALL).queryParam("plotId", plotId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].description").value("Falta de limpieza en su lote"))
                .andExpect(jsonPath("$.[0].plotId").value(1005))
                .andExpect(jsonPath("$.[1].description").value("Falta de limpieza en su lote"))
                .andExpect(jsonPath("$.[1].plotId").value(1005));
    }

    @Test
    void getAllSanctions_BadRequest_NoSanctionsExists() throws Exception {
        //given
        List<GetSanctionDto> emptySanctions = null;

        //when
        when(sanctionService.getAllReducedSanctions(null)).thenReturn(emptySanctions);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(SANCTION_ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    void putFineState_Success() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        PutFineStateDto putFineStateDto = new PutFineStateDto(1,FineState.APPEALED,"No Fui",2);

        GetFineDto responseDto = new GetFineDto(1,"Apelada","Nueva Multa \n"+sdf.format(new Date())+" - NO FUI",null,
                null,null,.0,null,null,LocalDateTime.now(),2);


        when(sanctionService.changeFineState(any(PutFineStateDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/sanction/changeStateFine")
                        .content(objectMapper.writeValueAsString(putFineStateDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.stateReason").value(responseDto.getStateReason()))
                .andExpect(jsonPath("$.fineState").value(responseDto.getFineState()));
    }
    @Test
    void putFine_BadRequest() throws Exception {
        PutFineStateDto putFineStateDto = new PutFineStateDto(1,FineState.APPEALED,"No Fui",2);

        when(sanctionService.changeFineState(any(PutFineStateDto.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/sanction/changeStateFine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putFineStateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postFine_Success() throws Exception {
        // given
        PostFineDto postFineDto = new PostFineDto(1, 100.0, 101);
        GetFineDto getFineDto = new GetFineDto(
                1, "Pendiente", null, null,null, LocalDate.now(), 100.0,
                LocalDateTime.now(), 101, LocalDateTime.now(), 101
        );

        // when
        when(sanctionService.createFine(any(PostFineDto.class))).thenReturn(getFineDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(POST_FINE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postFineDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.fineState").value("Pendiente"))
                .andExpect(jsonPath("$.createdUser").value(101));
    }

    @Test
    void postWarning_Success() throws Exception {
        // given
        PostWarningDto postWarningDto = new PostWarningDto(1, 101);
        GetWarningDto getWarningDto = new GetWarningDto(
                1, null, true, LocalDateTime.now(), 101, LocalDateTime.now(), 101
        );

        // when
        when(sanctionService.createWarning(any(PostWarningDto.class))).thenReturn(getWarningDto);

        // then
        mockMvc.perform(MockMvcRequestBuilders.post(POST_WARNING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postWarningDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(jsonPath("$.createdUser").value(101));
    }

    @Test
    void getFineById_Success() throws Exception {

        GetFineDto fine1 = new GetFineDto(1,
                "Pendiente de pago","Por pagar",
                new ReportEntity(),
                "reclamo 1",
                LocalDate.now(),
                100.6,
                LocalDateTime.now(),
                1,
                LocalDateTime.now(),
                1);

        //when
        when(sanctionService.getById(1)).thenReturn(fine1);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_BY_ID, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(sanctionService, times(1)).getById(1);
    }
    @Test
    void getFineById_EntityNotFound() throws Exception {
        //when
        when(sanctionService.getById(2010)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(GET_BY_ID, 2010)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(sanctionService, times(1)).getById(2010);
    }
 ////////////////////////////////////////////////////////////////////////////////

    @Test
    void updateFine_Success() throws Exception {
        // Dado
        GetFineDto getFineDto = new GetFineDto(
                1, "Pendiente", null, null,null, LocalDate.now(), 100.0,
                LocalDateTime.now(), 101, LocalDateTime.now(), 101
        );
        PutFineDto putFineDto = new PutFineDto(1, 200.00, 1);

        // Cuando
        when(sanctionService.updateFine(any(PutFineDto.class))).thenReturn(getFineDto);

        // Entonces
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/updateFine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putFineDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(getFineDto.getId()))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(getFineDto.getAmount()));

        verify(sanctionService, times(1)).updateFine(any(PutFineDto.class));
    }

    @Test
    void updateFine_BadRequest() throws Exception {
        // Dado
        PutFineDto putFineDto = new PutFineDto(999, 2999.00, 1);

        // Cuando
        when(sanctionService.updateFine(any(PutFineDto.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        // Entonces
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "/updateFine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putFineDto)))
                .andExpect(status().isBadRequest()); // Espera un código 400 Bad Request

        verify(sanctionService, times(1)).updateFine(any(PutFineDto.class));
    }

    @Test
    void getReducedComplaintsBetweenDates_Success() throws Exception {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        List<GetReducedFineDto> reducedComplaints = List.of(
                new GetReducedFineDto(1, 8, "Ruidos Molestos", 100.00),
                new GetReducedFineDto(2, 9, "Daño de propiedad", 800.00)
        );

        when(sanctionService.getReducedFinesByCreatedDateBetweenDates(
                startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)))
                .thenReturn(reducedComplaints);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/fines")
                        .param("start_date", startDate.toString())
                        .param("end_date", endDate.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].fine_id").value(1))
                .andExpect(jsonPath("$.[0].plot_id").value(8))
                .andExpect(jsonPath("$.[0].description").value("Ruidos Molestos"))
                .andExpect(jsonPath("$[0].amount").value(100.00))
                .andExpect(jsonPath("$.[1].fine_id").value(2))
                .andExpect(jsonPath("$.[1].plot_id").value(9))
                .andExpect(jsonPath("$.[1].description").value("Daño de propiedad"))
                .andExpect(jsonPath("$[1].amount").value(800.00));
    }

    @Test
    void getAllFines_Success() throws Exception {
        LocalDate checkDate = LocalDate.now();
        LocalDateTime checkDateTime = LocalDateTime.now();
        GetFineDto getFineDto1 = new GetFineDto(
                1, "Pendiente", null, null,null, checkDate, 100.0,
                checkDateTime, 101, checkDateTime, 101
        );
        GetFineDto getFineDto2 = new GetFineDto(
                2, "Abierto", null, null,null, checkDate, 800.0,
                checkDateTime, 105, checkDateTime, 105
        );
        List<GetFineDto> fines = List.of(getFineDto1, getFineDto2);

        when(sanctionService.getAllFines()).thenReturn(fines);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/allFines")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].fineState").value("Pendiente"))
                .andExpect(jsonPath("$.[0].amount").value(100.0))
                .andExpect(jsonPath("$.[0].createdUser").value(101))
                .andExpect(jsonPath("$.[0].lastUpdatedUser").value(101))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].fineState").value("Abierto"))
                .andExpect(jsonPath("$.[1].amount").value(800.0))
                .andExpect(jsonPath("$.[1].createdUser").value(105))
                .andExpect(jsonPath("$.[1].lastUpdatedUser").value(105));
    }

    @Test
    void getAllFines_NotFound() throws Exception {
        when(sanctionService.getAllFines()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/allFines")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFinesState_Success() throws Exception {
        Map<String, String> fineStates = Map.of(
                "PENDING", "Pending Payment",
                "PAID", "Paid"
        );

        when(sanctionService.getAllFineStates()).thenReturn(fineStates);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/allFinesState")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.PENDING").value("Pending Payment"))
                .andExpect(jsonPath("$.PAID").value("Paid"));
    }

    @Test
    void getAllFinesState_NotFound() throws Exception {
        when(sanctionService.getAllFineStates()).thenReturn(Map.of());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/allFinesState")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}