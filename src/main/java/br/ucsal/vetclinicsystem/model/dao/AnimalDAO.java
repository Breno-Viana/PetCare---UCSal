package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Animal;

import java.sql.*;
import java.time.LocalDate;
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animal;
    }

    public List<Animal> findAll() {
        var list = new ArrayList<Animal>();
        var sql = "select * from animais order by id";
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
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return list;
    }

    public void deleteByOwnerId(Long id) throws SQLException {
        var sql = """
                delete from animais where proprietario_id = ?;
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();

//            new ConsultationDAO().deleteByAnimalId();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private List<Animal> findByOwnerId(Long id) throws SQLException {
        var list = new ArrayList<Animal>();
        var sql = """
                select * from animais where proprietario_id = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nome");
                String breed = resultSet.getString("raca");
                String species = resultSet.getString("especie");
                float weight = resultSet.getFloat("peso");
                var owner = new OwnerDAO().findById(id);
                Date birth = resultSet.getDate("data_nascimento");
                long id1 = resultSet.getLong("id");
                list.add(new Animal(id, owner, name, species, breed, LocalDate.parse(birth.toString()), weight));

            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return list;
    }

    public List<Long> findAnimalIdByOwnerId(long id) throws SQLException {
        var sql = """
                select id from animais where proprietario_id = ?;
                """;
        var list = new ArrayList<Long>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return list;
    }

    public List<Animal> findByOwnerCpf(String search) throws SQLException {
        var sql = """
                select a.id,
                       a.nome,
                       a.raca,
                       a.especie,
                       a.proprietario_id,
                       a.peso,
                       a.data_nascimento
                from animais a
                join proprietarios p
                on p.id = a.proprietario_id
                where p.cpf = ?;
                """;

        var list = new ArrayList<Animal>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, search);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("nome");
                String species = resultSet.getString("especie");
                String breed = resultSet.getString("raca");
                long id = resultSet.getLong("id");
                long id1 = resultSet.getLong("proprietario_id");
                LocalDate birth = resultSet.getDate("data_nascimento").toLocalDate();
                float weight = resultSet.getFloat("peso");
                var owner = new OwnerDAO().findById(id1);
                list.add(new Animal(id, owner, name, species, breed, birth, weight));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return list;
    }

    public void save(Animal animal) throws SQLException {
        var sql = """
                insert into animais (nome, raca, data_nascimento, especie, proprietario_id, peso) values(?,?,?,?,?,?);
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, animal.getName());
            statement.setString(2, animal.getBreed());
            statement.setDate(3, Date.valueOf(animal.getBirthdate()));
            statement.setString(4, animal.getSpecies());
            statement.setLong(5, animal.getOwnerO().getId());
            statement.setFloat(6, animal.getWeight());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        var sql = """
                delete from animais where id = ?;
                """;
        new ConsultationDAO().deleteByAnimalId(id);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void update(Animal animal) throws SQLException {
        var sql = """
                update animais
                set nome = ?,
                raca = ?,
                especie = ?,
                proprietario_id = ?,
                peso = ?,
                data_nascimento = ?
                where id = ?; 
                """;


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, animal.getName());
            statement.setString(2, animal.getBreed());
            statement.setString(3, animal.getSpecies());
            statement.setLong(4, animal.getOwnerO().getId());
            statement.setFloat(5, animal.getWeight());
            statement.setDate(6, Date.valueOf(animal.getBirthdate()));
            statement.setLong(7, animal.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
};
