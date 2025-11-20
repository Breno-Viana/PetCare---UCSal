package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OwnerDAO {
    private final Connection connection;

    public OwnerDAO(){
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Owner findById(long idDono) {
        var sql = "select * from proprietarios where id = ?;";
        var owner = new Owner();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, idDono);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                owner.setId(resultSet.getLong("id"));
                owner.setName(resultSet.getString("nome"));
                owner.setCpf(resultSet.getString("cpf"));
                owner.setEmail(resultSet.getString("email"));
                owner.setPhone(resultSet.getString("telefone"));
//                owner.setAddress(new AddressDAO().findById(resultSet.getLong("id_endereco")));
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }

        return owner;
    }
}
