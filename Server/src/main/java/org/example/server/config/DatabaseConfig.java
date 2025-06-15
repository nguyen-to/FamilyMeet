package org.example.server.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // kết nối với cơ sở dữ liệu PostGreSQL
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/familymeet")
                .username("postgres")
                .password("leto200124")
                .build();
    }
}
