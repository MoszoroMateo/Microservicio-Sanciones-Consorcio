package ar.edu.utn.frc.tup.lc.iv.services.implementations;

import ar.edu.utn.frc.tup.lc.iv.dtos.get.GetDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.post.PostDisclaimerDto;
import ar.edu.utn.frc.tup.lc.iv.entities.DisclaimerEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.FineEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.DisclaimerRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.FineRepository;
import ar.edu.utn.frc.tup.lc.iv.services.interfaces.DisclaimerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DisclaimerServiceTest {

    // SpyBean se reemplaza por InjectMocks utilizando la clase Impl
    // MockBean se reemplaza a Mock
    // Se agrega @Spy para ModelMapper (si es que se lo utiliza)

    @InjectMocks
    private DisclaimerServiceImpl disclaimerServiceSpy;

    @Mock
    private DisclaimerRepository disclaimerRepository;

    @Mock
    private FineRepository fineRepository;

    @Spy
    private ModelMapper modelMapper;
    /** URL for the complaints endpoint */

    // Se debe colocar dentro del @BeaforeEach void setuUp(){ MockitoAnnotations.openMocks(this)};
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postDisclimer_Success() {
        //given
        PostDisclaimerDto postDisclaimerDto = new PostDisclaimerDto(1, 1,"Aca va un descargo del por qeu no deberia ir una multa a mi lote");

        FineEntity fineEntity = new FineEntity();
        fineEntity.setId(1);
        fineEntity.setAmount(15000.00);

        DisclaimerEntity disclaimerEntityy = new DisclaimerEntity();
        disclaimerEntityy.setId(1);
        disclaimerEntityy.setDisclaimer(postDisclaimerDto.getDisclaimer());
        disclaimerEntityy.setFine(fineEntity);
        disclaimerEntityy.setCreatedUser(postDisclaimerDto.getUserId());
        disclaimerEntityy.setCreatedDate(LocalDateTime.now());
        disclaimerEntityy.setLastUpdatedDate(LocalDateTime.now());
        disclaimerEntityy.setLastUpdatedUser(postDisclaimerDto.getUserId());

        GetDisclaimerDto getDisclaimerDto = new GetDisclaimerDto(1, 1,
                "Aca va un descargo del por qeu no deberia ir una multa a mi lote",
                LocalDateTime.now(),
                1,
                LocalDateTime.now(),
                1
        );

        //when
        when(fineRepository.findById(postDisclaimerDto.getFineId())).thenReturn(Optional.of(fineEntity));
        when(disclaimerRepository.save(any(DisclaimerEntity.class))).thenReturn(disclaimerEntityy);


        //then
        GetDisclaimerDto result = disclaimerServiceSpy.postDisclimer(postDisclaimerDto);

        assertNotNull(result);
        assertEquals(getDisclaimerDto.getId(), result.getId());
        assertEquals(getDisclaimerDto.getDisclaimer(), result.getDisclaimer());
        assertEquals(getDisclaimerDto.getFineId(), result.getFineId());
        verify(disclaimerRepository, times(1)).save(any(DisclaimerEntity.class));
    }

    @Test
    void postDisclimer_BadRequest_FineNotFound() {
        //given
        PostDisclaimerDto postDisclaimerDto = new PostDisclaimerDto(1, 1,
                "Aca va un descargo del por qeu no deberia ir una multa a mi lote");

        //when
        when(fineRepository.findById(postDisclaimerDto.getFineId())).thenReturn(Optional.empty());

        //then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            disclaimerServiceSpy.postDisclimer(postDisclaimerDto);
        });

        assertEquals("No se encontró una multa relacionada al id: 1", exception.getMessage());
        verify(disclaimerRepository, never()).save(any(DisclaimerEntity.class));
    }

    @Test
    void postDisclimer_BadRequest_SaveError() {
        //given
        PostDisclaimerDto postDisclaimerDto = new PostDisclaimerDto(1, 1,
                "Aca va un descargo del por qeu no deberia ir una multa a mi lote");

        FineEntity fineEntity = new FineEntity();
        fineEntity.setId(1);

        //when
        when(fineRepository.findById(postDisclaimerDto.getFineId())).thenReturn(Optional.of(fineEntity));
        when(disclaimerRepository.save(any(DisclaimerEntity.class))).thenThrow(new RuntimeException("Error en el guardado"));

        //then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            disclaimerServiceSpy.postDisclimer(postDisclaimerDto);
        });

        assertEquals("Se produjo un error al intentar guardar el descargo: Error en el guardado", exception.getMessage());
        verify(disclaimerRepository, times(1)).save(any(DisclaimerEntity.class));
    }
}
