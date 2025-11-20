package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianDAO {

    private final Connection connection;

    public VeterinarianDAO(){
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Veterinarian findById(long id){
        var sql = "select * from veterinarios where id = ?;";
        var veterinarian = new Veterinarian();
        try {
            var statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                veterinarian.setId(resultSet.getLong("id"));
                veterinarian.setName(resultSet.getString("nome"));
                veterinarian.setCrmv(resultSet.getString("crmv"));
                veterinarian.setSpecialty(resultSet.getString("especialidade"));

            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return veterinarian;
    }

    public List<Veterinarian> findAll(){
        var sql = "select * from veterinarios;";
        List<Veterinarian> list = new ArrayList<>();
        try{
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("nome");
                String crmv = resultSet.getString("crmv");
                String specialty = resultSet.getString("especialidade");
                String phone = resultSet.getString("telefone");
                Veterinarian veterinarian = new Veterinarian(id, crmv, name, specialty, phone);
                list.add(veterinarian);

            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return list;
    }
}
