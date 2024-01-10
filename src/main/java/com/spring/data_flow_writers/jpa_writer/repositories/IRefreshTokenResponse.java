package com.spring.data_flow_writers.jpa_writer.repositories;

import com.spring.data_flow_writers.jpa_writer.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefreshTokenResponse extends JpaRepository<RefreshTokenResponse, String> {
}
