package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO {
    private final Connection connection;

    public AddressDAO(){
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Address findById(long id) throws SQLException {
        Address address = null;
        var sql = """
                select * from enderecos where proprietario_id = ?;
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String street = resultSet.getString("logradouro");
                String city = resultSet.getString("cidade");
                String state = resultSet.getString("uf");
                address = new Address(id, street, city, state);
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }

        return address;
    }
}
