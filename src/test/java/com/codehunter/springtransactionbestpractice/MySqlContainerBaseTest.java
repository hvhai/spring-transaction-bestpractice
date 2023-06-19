package com.codehunter.springtransactionbestpractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@SpringBootTest
public class MySqlContainerBaseTest {
    @Autowired
    protected DataSource dataSource;

    @ServiceConnection
    protected static final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testcontainer")
            .withUsername("root")
            .withPassword("root")
            .withReuse(true); //to use this, enable testcontainers.reuse.enable in testcontainers properties
    // see https://www.testcontainers.org/features/reuse/

    static {
        mySQLContainer.start();
    }

}
