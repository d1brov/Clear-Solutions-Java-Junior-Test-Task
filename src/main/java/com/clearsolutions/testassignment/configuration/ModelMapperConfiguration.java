package com.clearsolutions.testassignment.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration()
        //        .setSkipNullEnabled(true);
                //.setMatchingStrategy(MatchingStrategies.STANDARD)
                //.setFieldMatchingEnabled(true)
                //.setFieldAccessLevel(PRIVATE);
        return modelMapper;
    }
}