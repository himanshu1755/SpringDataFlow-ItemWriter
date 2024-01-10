package com.spring.data_flow_writers.jpa_writer.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Component
@Entity(name = "REFRESH_TOKEN")
public class RefreshTokenResponse {
    @Id
    @Value("${refresh-token-response.refresh-token}")
    private String refresh_token;

    @Value("${refresh-token-response.token-type}")
    private String token_type;

    @Value("${refresh-token-response.access-token}")
    private String access_token;

    @Value("${refresh-token-response.expires-in}")
    private String expires_in;
}
