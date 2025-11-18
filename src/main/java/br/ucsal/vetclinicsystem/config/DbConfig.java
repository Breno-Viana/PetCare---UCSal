package br.ucsal.vetclinicsystem.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static br.ucsal.vetclinicsystem.config.PostgresConnectionConfig.getConnectionInstance;

public class DbConfig {
    private static final Connection connection = getConnectionInstance();

    public static void createTables() throws SQLException{
        var sql1 = """
                CREATE TABLE IF NOT EXISTS pessoa(
                id SERIAL PRIMARY KEY,
                nome VARCHAR(255) NOT NULL
                );\s
               \s""";

        try(PreparedStatement statement = connection.prepareStatement(sql1);){
            statement.execute();

            System.out.println("Tables created successfully");
        }catch (SQLException e){
            throw new SQLException("Error creating tables");
        }
    }

}
