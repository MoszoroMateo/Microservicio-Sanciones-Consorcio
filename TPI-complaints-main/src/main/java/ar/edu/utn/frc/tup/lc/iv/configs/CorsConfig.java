package ar.edu.utn.frc.tup.lc.iv.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configura los permisos CORS para todas las rutas.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Habilita CORS para todas las rutas con métodos GET, POST y PUT.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Habilita CORS para todas las rutas.
                .allowedOrigins("*") // Permite cualquier origen.
                .allowedMethods("GET", "POST", "PUT") // Permite los métodos GET, POST y PUT.
                .allowedHeaders("*"); // Permite cualquier header.
    }
}
