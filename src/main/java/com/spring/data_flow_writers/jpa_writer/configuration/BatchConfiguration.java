package com.spring.data_flow_writers.jpa_writer.configuration;

import com.spring.data_flow_writers.jpa_writer.models.RefreshTokenResponse;
import com.spring.data_flow_writers.jpa_writer.processor.TokenProcessor;
import com.spring.data_flow_writers.jpa_writer.reader.ObjectReader;
import com.spring.data_flow_writers.jpa_writer.repositories.IRefreshTokenResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;


@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfiguration<T> {


    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    private Gson gson;
    @Autowired
    private TokenProcessor tokenProcessor;

    @Autowired
    private RefreshTokenResponse refreshTokenResponse;

    @Autowired
    private IRefreshTokenResponse refreshTokenResponserepo;



    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public ItemReader<T> singleObjectReader() {

            LOGGER.info("refreshTokenResponse ============ {}", refreshTokenResponse);

            @SuppressWarnings("unchecked")
            T yourSingleObject = (T) refreshTokenResponse;
            return new ObjectReader<>(yourSingleObject);
    }


    @Bean
    public ItemWriter<T> jpaItemWriter() {
        LOGGER.info("JPA_ItemWriter called====================");

        return items -> {
            LOGGER.info("items = {}", items);
            LOGGER.info("items SIZE= {}", items.size());

            RefreshTokenResponse res = gson.fromJson(items.get(0).toString(),RefreshTokenResponse.class);
            refreshTokenResponserepo.save(res);
            refreshTokenResponserepo.flush();


        };

    }

    @Bean
    public Step jpaWriterStep(ItemReader<T> reader, ItemWriter<T> writer,
                              ItemProcessor<T, T> tokenProcessor) {
        return stepBuilderFactory.get("jpaWriterStep")
                .<T, T>chunk(1) // Process one object at a time
                .reader(reader)
                .processor(tokenProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job jpaWriterJob(Step jpaWriterStep) {
        return jobBuilderFactory.get("jpaWriterJob").incrementer(new RunIdIncrementer()).flow(jpaWriterStep).end().build();
    }
}
