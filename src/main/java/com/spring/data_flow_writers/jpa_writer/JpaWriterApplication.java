package com.spring.data_flow_writers.jpa_writer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
@EntityScan("com.spring.data_flow_writers.jpa_writer.models")
public class JpaWriterApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaWriterApplication.class, args);
    }

}
