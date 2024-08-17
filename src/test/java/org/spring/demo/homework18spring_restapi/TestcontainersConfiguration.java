package org.spring.demo.homework18spring_restapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

//    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.0")
//            .withDatabaseName("postgres")
//            .withUsername("postgres")
//            .withPassword("password");
//
//    static {
//        postgreSQLContainer.start();
//    }
//
//    @Bean
//    public PostgreSQLContainer<?> postgreSQLContainer() {
//
//        return postgreSQLContainer;
//    }



//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public PostgreSQLContainer<?> postgreSQLContainer() {
//       return new PostgreSQLContainer<>("postgres:16-alpine");
//    }
//
//    @Bean
//    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
//        var hikariDataSource = new HikariDataSource();
//        hikariDataSource.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
//        hikariDataSource.setUsername(postgreSQLContainer.getUsername());
//        hikariDataSource.setPassword(postgreSQLContainer.getPassword());
//        return hikariDataSource;
//    }
//
//    @Bean
//    public Flyway flyway(DataSource dataSource) {
//        Flyway flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .load();
//        flyway.migrate();
//        return flyway;
//    }
}
