package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
/**
 * Configures REST-related beans for the application.
 */
@Configuration
public class RestConfig {

    /**
     * Provides a RestTemplate with custom timeouts.
     *
     * @param builder Used to build the RestTemplate.
     * @return RestTemplate with 5s connection and 10s read timeouts.
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

}
