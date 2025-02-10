package ar.edu.utn.frc.tup.lc.iv.configs;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Configuration for RabbitMQ.
 */
@Configuration
public class RabbitMqConfig {

    /**
     * Name of the queue.
     */
    @Value("${fine.queue.name}")
    private String message;

    /**
     * Creates a durable queue.
     * @return The queue.
     */
    @Bean
    public Queue durableQueue() {
        System.out.println("Queue name: " + message + " created. (Method durableQueue)");
        return new Queue(message, true);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        System.out.println("Queue name: " + message + " created. (Method jsonMessageConverter)");
        return new Jackson2JsonMessageConverter();
    }

}
