package com.spring.data_flow_writers.jpa_writer.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TokenProcessor<T> implements ItemProcessor<T, T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProcessor.class);

    @Override
    public T process(T refreshTokenResponse) {
        // Your processing logic here if needed
        LOGGER.info("JPA_writer_Processor Input ============ {}", refreshTokenResponse);

        return refreshTokenResponse; // Return the processed response
    }
}
