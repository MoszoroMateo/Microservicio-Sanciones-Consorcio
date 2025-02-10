package ar.edu.utn.frc.tup.lc.iv.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper and ObjectMapper configuration class.
 */
@Configuration
public class MappersConfig {

    /**
     * The ModelMapper bean by default.
     * @return the ModelMapper by default.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    /**
     * The ModelMapper bean to merge objects.
     * @return the ModelMapper to use in updates.
     */
    @Bean("mergerMapper")
    public ModelMapper mergerMapper() {
        ModelMapper mapper =  new ModelMapper();
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    /**
     * The ObjectMapper bean.
     * @return the ObjectMapper with JavaTimeModule included.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}
