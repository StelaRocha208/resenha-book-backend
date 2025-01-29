package br.com.ifba.infrastructure.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperUtil {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
