package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing).
 * This configuration enables the application to handle cross-origin requests
 * by allowing all origins, methods, and headers.
 * Implements the WebMvcConfigurer interface to customize the CORS settings.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry The CorsRegistry object used to define the CORS configuration.
     *                 It allows all origins, methods (GET, POST, PUT), and headers.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT")
                .allowedHeaders("*");
    }

}
