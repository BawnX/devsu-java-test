package com.hexagonal.account.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    @Autowired
    private DatabaseConfig databaseConfig;

    public Connection getConnection() throws SQLException {
        String url = databaseConfig.getDatabaseUrl();
        String user = databaseConfig.getUserName();
        String password = databaseConfig.getPassword();
        return DriverManager.getConnection(url, user, password);
    }
}
