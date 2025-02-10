package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.*;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostWarningDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.put.PutFineStateDto;
import ar.edu.utn.frc.tup.lc.iv.entities.FineEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ReportReasonEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.WarningEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.FineState;
import ar.edu.utn.frc.tup.lc.iv.entities.enums.ReportState;
import ar.edu.utn.frc.tup.lc.iv.messaging.consumer.FinePaidDto;
import ar.edu.utn.frc.tup.lc.iv.repositories.FineRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.ReportRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.WarningRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class SanctionServiceImplTest {
    @SpyBean
    private SanctionServiceImpl sanctionService;

    @MockBean
    private FineRepository fineRepository;

    @MockBean
    private WarningRepository warningRepository;

    @MockBean
    private ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;

    ReportEntity report1;
    WarningEntity warning;
    FineEntity fine1;
    @BeforeEach
    void setUp(){
        report1 = new ReportEntity(
                1,
                ReportState.OPEN, // Random ReportState
                "open",
                new ReportReasonEntity(),
                2,
                "description",
                LocalDateTime.now(),
                4,
                LocalDateTime.now(),
                4
        );
        warning = new WarningEntity(
                1,
                report1,
                true,
                LocalDateTime.now(),
                7,
                LocalDateTime.now(),
                7
        );
        fine1 = new FineEntity(
                1,
                FineState.PENDING,
                "Pendiente",
                report1,
                null,
                15000.00,
                LocalDateTime.now(),
                7,
                LocalDateTime.now(),
                7
        );
    }

    @Test
    void createFineSuccess() {
        PostFineDto postFineDto = new PostFineDto(1,15000.00,7);
        GetFineDto getFineDto = new GetFineDto(
                1,
                "Pendiente",
                "Pendiente",
                report1,
                "",
                null,
                15000.00,
                LocalDateTime.now(),
                7,
                LocalDateTime.now(),
                7
        );

        // when(modelMapper.map(fine1, GetFineDto.class)).thenReturn(getFineDto);
        when(reportRepository.findById(anyInt())).thenReturn(Optional.of(report1));
        // when(reportRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(fineRepository.save(any(FineEntity.class))).thenReturn(fine1);

        GetFineDto result = sanctionService.createFine(postFineDto);
        assertNotNull(result);
        assertEquals(getFineDto.getId(), result.getId());
        assertEquals(getFineDto.getAmount(), result.getAmount());
        assertEquals(getFineDto.getFineState(), result.getFineState());
        assertEquals(getFineDto.getReport(), result.getReport());
        verify(fineRepository, times(1)).save(any(FineEntity.class));
    }

    @Test
    void createFineReportNotFound() {
        PostFineDto postFineDto = new PostFineDto(1,15000.00,7);

        when(reportRepository.findById(anyInt())).thenReturn(Optional.empty());
         // when(fineRepository.save(any(FineEntity.class))).thenReturn(fine1);
        assertThrows(EntityNotFoundException.class, () -> {
            sanctionService.createFine(postFineDto);
        });
        verify(reportRepository, times(1)).findById(anyInt());
    }

    @Test
    void createFineSaveFineError() {
        PostFineDto postFineDto = new PostFineDto(1,15000.00,7);
        when(reportRepository.findById(anyInt())).thenReturn(Optional.of(report1));
        // Al no definir que debe hacer fineRepository cuando guarda, este va a generar un error
        assertThrows(RuntimeException.class, () -> {
            sanctionService.createFine(postFineDto);
        });
        verify(fineRepository, times(1)).save(any(FineEntity.class));
    }



    @Test
    void createWarningSuccess() {
        PostWarningDto postWarningDto = new PostWarningDto(1,7);
        GetWarningDto getWarningDto = new GetWarningDto(
                1,
                report1,
                true,
                LocalDateTime.now(),
                7,
                LocalDateTime.now(),
                7
        );

        when(reportRepository.findById(anyInt())).thenReturn(Optional.of(report1));
        when(warningRepository.save(any(WarningEntity.class))).thenReturn(warning);

        GetWarningDto result = sanctionService.createWarning(postWarningDto);
        assertNotNull(result);
        assertEquals(getWarningDto.getId(), result.getId());
        assertEquals(getWarningDto.getReport().getDescription(), result.getReport().getDescription());
        assertEquals(getWarningDto.getReport(), result.getReport());
        verify(warningRepository, times(1)).save(any(WarningEntity.class));

    }

    @Test
    void createWarningReportNotFound() {
        PostWarningDto postWarningDto = new PostWarningDto(1,7);

        when(reportRepository.findById(anyInt())).thenReturn(Optional.empty());
        // when(fineRepository.save(any(FineEntity.class))).thenReturn(fine1);
        assertThrows(EntityNotFoundException.class, () -> {
            sanctionService.createWarning(postWarningDto);
        });
        verify(reportRepository, times(1)).findById(anyInt());
    }

    @Test
    void createWarningSaveFineError() {
        PostWarningDto postWarningDto = new PostWarningDto(1,7);
        when(reportRepository.findById(anyInt())).thenReturn(Optional.of(report1));
        // Al no definir que debe hacer fineRepository cuando guarda, este va a generar un error
        assertThrows(RuntimeException.class, () -> {
            sanctionService.createWarning(postWarningDto);
        });
        verify(warningRepository, times(1)).save(any(WarningEntity.class));
    }

    @Test
    void getSanctionsSuccess_NoParameter(){
        List<WarningEntity> warningEntityList = Arrays.asList(warning);
        List<FineEntity> fineEntityList = Arrays.asList(fine1);

        when(fineRepository.findAll()).thenReturn(fineEntityList);
        when(warningRepository.findAll()).thenReturn(warningEntityList);

        List<GetSanctionDto> result = sanctionService.getAllReducedSanctions(null);

        assertEquals(2, result.size());
        assertEquals("description", result.get(0).getDescription());
        assertEquals("Pendiente", result.get(0).getFineState());
        assertEquals("description", result.get(1).getDescription());
        assertNull(result.get(1).getFineState());
        verify(warningRepository, times(1)).findAll();
        verify(fineRepository, times(1)).findAll();
    }

    @Test
    void getSanctionsSuccess_Parameter(){
        ReportEntity report2 = new ReportEntity(
                1,
                ReportState.CLOSED,
                "Se cierra el caso y se genera una multa",
                new ReportReasonEntity(),
                1005,
                "Se genero el reporte por reiteradas denuncias por jardin sucio",
                LocalDateTime.now(),
                4,
                LocalDateTime.now(),
                4
        );
        FineEntity fine2 = new FineEntity(
                1,
                FineState.APPEALED,
                "Se recibio la apelacion del Lote 1005",
                report2,
                null,
                3000.00,
                LocalDateTime.now(),
                7,
                LocalDateTime.now(),
                7
        );
        List<FineEntity> fineEntityList = Arrays.asList(fine2);
        Integer plotId = 1005;

        when(fineRepository.getFineEntitiesByReportPlotId(plotId)).thenReturn(fineEntityList);
        when(warningRepository.getWarningEntitiesByReportPlotId(plotId)).thenReturn(new ArrayList<>());

        List<GetSanctionDto> result = sanctionService.getAllReducedSanctions(1005);

        assertEquals(1, result.size());
        assertEquals("Se genero el reporte por reiteradas denuncias por jardin sucio", result.get(0).getDescription());
        assertEquals("Apelada", result.get(0).getFineState());
        assertEquals(3000.00, result.get(0).getAmount());
        assertEquals(1005, result.get(0).getPlotId());
        verify(warningRepository, times(1)).getWarningEntitiesByReportPlotId(plotId);
        verify(fineRepository, times(1)).getFineEntitiesByReportPlotId(plotId);
    }

    @Test
    void getSanctionsNotFound_NoParameter(){

        when(fineRepository.findAll()).thenReturn(Collections.emptyList());
        when(warningRepository.findAll()).thenReturn(Collections.emptyList());

        List<GetSanctionDto> result = sanctionService.getAllReducedSanctions(null);

        assertEquals(result,Collections.emptyList());
        verify(warningRepository, times(1)).findAll();
        verify(fineRepository, times(1)).findAll();
    }

    @Test
    void getSanctionsNotFound_Parameter(){
        Integer plotId = 999;

        when(fineRepository.getFineEntitiesByReportPlotId(plotId)).thenReturn(Collections.emptyList());
        when(warningRepository.getWarningEntitiesByReportPlotId(plotId)).thenReturn(Collections.emptyList());

        List<GetSanctionDto> result = sanctionService.getAllReducedSanctions(plotId);

        assertEquals(result,Collections.emptyList());
        verify(warningRepository, times(1)).getWarningEntitiesByReportPlotId(plotId);
        verify(fineRepository, times(1)).getFineEntitiesByReportPlotId(plotId);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void putFineState(){
        PutFineStateDto fineDto = new PutFineStateDto(1,FineState.APPEALED,"El Propietario apelo la multa",1);
        FineEntity fine = new FineEntity(1,FineState.PENDING,"Multa Creada",null,null,null,
                LocalDateTime.now().minusDays(Long.valueOf(3)),null,null,2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expectedReason = fine.getStateReason()+"\n"+ sdf.format(new Date())+" - "+fineDto.getStateReason();

        ReflectionTestUtils.invokeMethod(sanctionService,"updateFineState",fine,fineDto);

        assertEquals(fine.getFineState(),FineState.APPEALED);
        assertEquals(fine.getLastUpdatedUser(),1);
        assertEquals(fine.getLastUpdatedDate().withNano(0),LocalDateTime.now().withNano(0));
        assertEquals(fine.getStateReason(),expectedReason);
    }

    @Test
    void changeFineState_FineNotFound(){
        PutFineStateDto fineDto = new PutFineStateDto(1,FineState.APPEALED,"El Propietario apelo la multa",1);

        when(fineRepository.findById(fineDto.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()->sanctionService.changeFineState(fineDto));
        assertEquals("No se encontró una multa relacionado al id: " + fineDto.getId(),exception.getMessage());
    }

    @Test
    void changeFineState_APPEALED(){
        PutFineStateDto fineDto = new PutFineStateDto(1,FineState.APPEALED,"El Propietario apelo la multa",1);
        FineEntity fine = new FineEntity(1,FineState.PENDING,"Multa Creada",null,null,null,
                LocalDateTime.now().minusDays(Long.valueOf(3)),null,null,2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String expectedReason = fine.getStateReason()+"\n"+ sdf.format(new Date())+" - "+fineDto.getStateReason();

        when(fineRepository.findById(fineDto.getId())).thenReturn(Optional.of(fine));
        sanctionService.changeFineState(fineDto);

        verify(fineRepository,times(1)).save(any());
        assertEquals(fine.getFineState(),FineState.APPEALED);
        assertEquals(fine.getLastUpdatedUser(),1);
        assertEquals(fine.getLastUpdatedDate().withNano(0).truncatedTo(ChronoUnit.SECONDS),LocalDateTime.now().withNano(0));
        assertEquals(fine.getStateReason(),expectedReason);
    }

    @Test
    void changeFineState_WrongFineState(){
        PutFineStateDto fineDto = new PutFineStateDto(1,FineState.PAYMENT_PAYMENT,"El Propietario apelo la multa",1);
        FineEntity fine = new FineEntity(1,FineState.ACQUITTED,"Ejemplo de Razon",null,null,null,
                LocalDateTime.now().minusDays(Long.valueOf(3)),null,null,2);

        when(fineRepository.findById(fineDto.getId())).thenReturn(Optional.of(fine));


        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->sanctionService.changeFineState(fineDto));
        assertEquals("Transición de "+fine.getFineState() +" a "+fineDto.getFineState()+" No permitida",exception.getMessage());
    }

    @Test
    void paymetPaymentStateForPendingFinesLogic_Test(){
        FineEntity fine1 = new FineEntity();
        fine1.setFineState(FineState.PENDING);
        fine1.setCreatedDate(LocalDateTime.now().minusDays(7));
        FineEntity fine2 = new FineEntity();
        fine2.setFineState(FineState.PENDING);
        fine2.setCreatedDate(LocalDateTime.now().minusDays(7));
        FineEntity fine3 = new FineEntity();
        fine3.setFineState(FineState.PENDING);
        fine3.setCreatedDate(LocalDateTime.now().minusDays(3));
        FineEntity fine4 = new FineEntity();
        fine4.setFineState(FineState.PENDING);
        fine4.setCreatedDate(LocalDateTime.now().minusDays(4));

        List<FineEntity> pendigFines = new ArrayList<>();
        pendigFines.add(fine1);
        pendigFines.add(fine2);
        pendigFines.add(fine3);
        pendigFines.add(fine4);


        when(fineRepository.getFineEntitiesByFineState(FineState.PENDING)).thenReturn(pendigFines);
        ReflectionTestUtils.invokeMethod(sanctionService,"paymetPaymentStateForPendingFinesLogic");

        assertEquals(FineState.PAYMENT_PAYMENT,pendigFines.get(0).getFineState());
        assertEquals(FineState.PAYMENT_PAYMENT,pendigFines.get(1).getFineState());
        assertEquals(FineState.PENDING,pendigFines.get(2).getFineState());
        assertEquals(FineState.PENDING,pendigFines.get(3).getFineState());
        verify(fineRepository).saveAll(any());
    }

//    @Test
//    void payedStateForpaymentPaymentFinesTest(){
//        //Given
//        LocalDate paymentDay = LocalDate.now();
//        FineEntity fine = new FineEntity();
//        fine.setId(1);
//        fine.setFineState(FineState.PAYMENT_PAYMENT);
//        fine.setStateReason("ALGO");
//
//        //when
//        when(fineRepository.findById(1)).thenReturn(Optional.of(fine));
//        GetFineDto result = sanctionService.payedStateForpaymentPaymentFines(1,paymentDay);
//
//        //then
//        assertEquals(fine.getId(),result.getId());
//        verify(fineRepository).save(any());
//        assertEquals(fine.getStateReason(),result.getStateReason());
//        assertNotEquals(FineState.PAYMENT_PAYMENT,result.getFineState());
//    }
//
//    @Test
//    void changePayedState_WrongFineState(){
//        FineEntity fine = new FineEntity();
//        fine.setId(1);
//        fine.setFineState(FineState.PENDING);
//        fine.setStateReason("ALGO");
//
//        when(fineRepository.findById(1)).thenReturn(Optional.of(fine));
//
//
//        IllegalStateException exception = assertThrows(IllegalStateException.class, ()->sanctionService.payedStateForpaymentPaymentFines(1,LocalDate.now()));
//        assertEquals("Transición de "+fine.getFineState() +" a "+FineState.PAYED+" No permitida",exception.getMessage());
//    }

    @Test
    void getFineById_Success(){
        //given
        FineEntity fine = new FineEntity(1,
                FineState.APPEALED,
                "apelada",
                new ReportEntity(),
                LocalDate.now(),
                10.5,
                LocalDateTime.of(2024, 10, 6, 20, 0),
                15,
                LocalDateTime.now(),
                1);
        //when
        when(fineRepository.findById(fine.getId())).thenReturn(Optional.of(fine));

        //then
        GetFineDto result = sanctionService.getById(fine.getId());

        assertNotNull(result);
        assertEquals(fine.getId(), result.getId());
        assertEquals(fine.getId(), result.getId());
        assertEquals(fine.getStateReason(), result.getStateReason());
    }

    @Test
    void getFineById_ReturnException(){
        //given

        //when
        when(fineRepository.findById(200)).thenReturn(Optional.empty());

        //then
        assertThrows(EntityNotFoundException.class, () -> {
            sanctionService.getById(200);
        });
        verify(fineRepository, times(1)).findById(200);
    }

    @Test
    void getReducedFineByCreatedDateBetweenDatesTest() {

        //given
        List<FineEntity> fines = new ArrayList<>();

        FineEntity fine1 = new FineEntity();
        fine1.setId(1);
        fine1.setAmount(10000.00);
        ReportEntity report1 = new ReportEntity();
        report1.setPlotId(1);
        report1.setDescription("Reporte mockeado");
        fine1.setReport(report1);
        fine1.setCreatedDate(LocalDateTime.now().minusDays(7));
        fines.add(fine1);

        FineEntity fine2 = new FineEntity();
        fine2.setId(2);
        fine2.setAmount(10000.00);
        ReportEntity report2 = new ReportEntity();
        report2.setPlotId(2);
        report2.setDescription("Reporte mockeado 2");
        fine2.setReport(report2);
        fine2.setCreatedDate(LocalDateTime.now().minusDays(10));
        fines.add(fine2);

        FineEntity fine3 = new FineEntity();
        fine3.setId(1);
        fine3.setAmount(10000.00);
        ReportEntity report3 = new ReportEntity();
        report3.setPlotId(3);
        report3.setDescription("Reporte mockeado 3");
        fine3.setReport(report3);
        fine3.setCreatedDate(LocalDateTime.now().minusDays(3));
        fines.add(fine3);

        //when
        when(fineRepository.getFineEntitiesByCreatedDateBetween(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))).thenReturn(fines);
        List<GetReducedFineDto> result = sanctionService.getReducedFinesByCreatedDateBetweenDates(LocalDateTime.now().minusDays(10).truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        //then
        assertEquals(fines.get(0).getId(), result.get(0).getFine_id());
        assertEquals(fines.get(1).getAmount(), result.get(1).getAmount());
        assertEquals(fines.get(2).getReport().getDescription(), result.get(2).getDescription());
        assertEquals(fines.get(2).getReport().getPlotId(), result.get(2).getPlot_id());
    }

    //////////////////////////////////////////////////////////////////////////////

    @Test
    void updateFine_Success() {
        // Arrange
        Integer fineId = 1;
        Double updatedAmount = 150.0;
        Integer userId = 1;
        PutFineDto fineDto = new PutFineDto(fineId, updatedAmount, userId);

        FineEntity fineEntity = new FineEntity(fineId, FineState.PENDING, "Multa Creada", null, null, null,
                LocalDateTime.now().minusDays(3), null, null, 2);

        // Simula el comportamiento del repositorio para que devuelva el fineEntity cuando se busque por ID
        when(fineRepository.findById(fineId)).thenReturn(Optional.of(fineEntity));
        when(fineRepository.save(any(FineEntity.class))).thenReturn(fineEntity);

        // Act
        GetFineDto result = sanctionService.updateFine(fineDto);

        // Assert
        assertEquals(fineEntity.getAmount(), result.getAmount());
        assertEquals(fineEntity.getLastUpdatedUser(), (Integer) userId);
        assertNotNull(fineEntity.getLastUpdatedDate());
        assertEquals(fineEntity.getLastUpdatedDate().withNano(0), LocalDateTime.now().withNano(0)); // Para comparar fechas sin nanosegundos
    }



    @Test
    void updateFine_BadRequest_FineNotFound() {
        // Dado
        PutFineDto putFineDto = new PutFineDto(1, 20000.00, 2);

        // Cuando
        when(fineRepository.findById(putFineDto.getId())).thenReturn(Optional.empty());

        // Entonces
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            sanctionService.updateFine(putFineDto);
        });

        assertEquals("No se encontró una multa con el id: 1", exception.getMessage());
        verify(fineRepository, never()).save(any(FineEntity.class));
    }

    @Test
    void updateFine_BadRequest_SaveError() {
        // Dado
        PutFineDto putFineDto = new PutFineDto(1, 20000.00, 2);

        FineEntity fineEntity = new FineEntity();
        fineEntity.setId(1);
        fineEntity.setAmount(15000.00);

        // Cuando
        when(fineRepository.findById(putFineDto.getId())).thenReturn(Optional.of(fineEntity));
        when(fineRepository.save(any(FineEntity.class))).thenThrow(new RuntimeException("Error al actualizar la multa"));

        // Entonces
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sanctionService.updateFine(putFineDto);
        });

        assertEquals("Se produjo un error al intentar actualizar la multa: Error al actualizar la multa", exception.getMessage());
        verify(fineRepository, times(1)).save(any(FineEntity.class));
    }


    @Test
    void getAllFines_Success() {
        List<FineEntity> fineEntities = Arrays.asList(fine1);
        when(fineRepository.findAll()).thenReturn(fineEntities);

        List<GetFineDto> result = sanctionService.getAllFines();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(fine1.getId(), result.get(0).getId());
        verify(fineRepository, times(1)).findAll();
    }

    @Test
    void getAllFineStates_Success() {
        Map<String, String> expectedStates = new HashMap<>();
        expectedStates.put("PENDING", "Pendiente");
        expectedStates.put("APPEALED", "Apelada");
        expectedStates.put("PAYMENT_PAYMENT", "Pendiente de pago");
        expectedStates.put("PAYED", "Pagada");
        expectedStates.put("ACQUITTED", "Absuelta");
        expectedStates.put("WARNING", "Advertencia");

        Map<String, String> result = sanctionService.getAllFineStates();

        assertNotNull(result);
        assertEquals(expectedStates.size(), result.size());
        assertEquals(expectedStates, result);
    }

    @Test
    void changeFineToPaid_Success() {
        FinePaidDto finePaidDto = new FinePaidDto(1, LocalDate.now(), 1);
        FineEntity fine = new FineEntity(1, FineState.PAYMENT_PAYMENT, "Pendiente de pago", report1, null, 15000.00, LocalDateTime.now(), 7, LocalDateTime.now(), 7);
        ReportEntity report = new ReportEntity(1, ReportState.OPEN, "Abierto", new ReportReasonEntity(), 2, "description", LocalDateTime.now(), 4, LocalDateTime.now(), 4);

        when(fineRepository.findById(finePaidDto.getFineId())).thenReturn(Optional.of(fine));
        when(reportRepository.findById(fine.getReport().getId())).thenReturn(Optional.of(report));

        sanctionService.changeFineToPaid(finePaidDto);

        assertEquals(FineState.PAYED, fine.getFineState());
        assertEquals(ReportState.ENDED, report.getReportState());
        verify(fineRepository, times(1)).save(fine);
        verify(reportRepository, times(1)).save(report);
    }
}