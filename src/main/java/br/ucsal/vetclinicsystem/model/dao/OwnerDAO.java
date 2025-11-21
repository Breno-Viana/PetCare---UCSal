package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Address;
import br.ucsal.vetclinicsystem.model.entities.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {
    private final Connection connection;

    public OwnerDAO(){
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Owner findById(long idDono) throws SQLException {
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
                owner.setAddress(new AddressDAO().findById(owner.getId()));
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }

        return owner;
    }

    public List<Owner> findAll() {
        var sql = """
                select * from proprietarios;
                """;

        var list = new ArrayList<Owner>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                Address byId = new AddressDAO().findById(id);
                list.add(new Owner(id,cpf, nome, email, telefone ,byId));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
}
