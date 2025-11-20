package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
    private final Connection connection;

    public AnimalDAO() {
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Animal findById(long id) {
        var sql = "select * from animais where id = ?;";
        var animal = new Animal();
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animal.setId(resultSet.getLong("id"));
                animal.setName(resultSet.getString("nome"));
                animal.setSpecies(resultSet.getString("especie"));
                animal.setBreed(resultSet.getString("raca"));
                animal.setBirthdate(resultSet.getDate("data_nascimento").toLocalDate());
                animal.setWeight(resultSet.getFloat("peso"));
                animal.setOwner(new OwnerDAO().findById(resultSet.getLong("proprietario_id")));
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
        return animal;
    }

    public List<Animal> findAll(){
        var list = new ArrayList<Animal>();
        var sql = "select * from animais";
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nome");
                String species = resultSet.getString("especie");
                String breed = resultSet.getString("raca");
                long id = resultSet.getLong("id");
                float weight = resultSet.getFloat("peso");
                var birthdate = resultSet.getDate("data_nascimento").toLocalDate();
                var owner = new OwnerDAO().findById(resultSet.getLong("proprietario_id"));
                var animal = new Animal(id, owner, name, species, breed, birthdate, weight);
                list.add(animal);
            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return list;
    }
};
