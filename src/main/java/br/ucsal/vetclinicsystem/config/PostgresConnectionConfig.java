package br.ucsal.vetclinicsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/vet_clinic_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    public static Connection getConnectionInstance() {
        if (connection == null) {
            try{
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("error connecting to database");
            }
        }
        return connection;
    }


}
