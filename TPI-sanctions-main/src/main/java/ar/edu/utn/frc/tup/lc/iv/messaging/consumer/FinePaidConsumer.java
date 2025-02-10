package ar.edu.utn.frc.tup.lc.iv.messaging.consumer;

import ar.edu.utn.frc.tup.lc.iv.services.implementations.SanctionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


/**
 * Consumer for handling fine paid messages.
 */
@Component
@RequiredArgsConstructor
public class FinePaidConsumer {

/**
 * Consumer for handling fine paid messages.
 */
private final SanctionServiceImpl service;

    /**
     * Receives a message from the queue and processes it.
     * @param dto The message to process.
     */
    @RabbitListener(queues = "#{@durableQueue.name}")
    public void receive(@Payload FinePaidDto dto) {
        try {
            service.changeFineToPaid(dto);
            System.out.println("Mensaje consumido: " + dto);
        } catch (Exception e) {
            System.out.println("Error al consumir mensajeria: " + e);
        }

    }
}
