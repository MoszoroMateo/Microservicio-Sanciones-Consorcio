package ar.edu.utn.frc.tup.lc.iv.messaging.consumer;

import ar.edu.utn.frc.tup.lc.iv.services.implementations.SanctionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FinePaidConsumerTest {
    @Mock
    private SanctionServiceImpl sanctionService;

    @InjectMocks
    private FinePaidConsumer consumer;

    private FinePaidDto finePaidDto;

    @BeforeEach
    void setUp() {
        finePaidDto = new FinePaidDto(
                "2024-01-01",
                "2024-01-31",
                1,
                123,
                "PAID",
                456
        );
    }

    @Test
    void receive_SuccessfulProcessing() {
        doNothing().when(sanctionService).changeFineToPaid(finePaidDto);
        consumer.receive(finePaidDto);

        verify(sanctionService, times(1)).changeFineToPaid(finePaidDto);
    }

    @Test
    void receive_HandlesException() {
        doThrow(new RuntimeException("Error processing fine payment"))
                .when(sanctionService)
                .changeFineToPaid(finePaidDto);

        consumer.receive(finePaidDto);
        verify(sanctionService, times(1)).changeFineToPaid(finePaidDto);
    }

    @Test
    void receive_HandlesNullMessage() {
        consumer.receive(null);
        verify(sanctionService, times(1)).changeFineToPaid(null);
    }

}