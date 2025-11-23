package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianDAO {

    private final Connection connection;

    public VeterinarianDAO() {
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Veterinarian findById(long id) {
        var sql = "select * from veterinarios where id = ?;";
        var veterinarian = new Veterinarian();
        try {
            var statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                veterinarian.setId(resultSet.getLong("id"));
                veterinarian.setName(resultSet.getString("nome"));
                veterinarian.setCrmv(resultSet.getString("crmv"));
                veterinarian.setSpeciality(resultSet.getString("especialidade"));
                veterinarian.setPhone(resultSet.getString("telefone"));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return veterinarian;
    }

    public List<Veterinarian> findAll() {
        var sql = "select * from veterinarios order by  id;";
        List<Veterinarian> list = new ArrayList<>();
        try {
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("nome");
                String crmv = resultSet.getString("crmv");
                String specialty = resultSet.getString("especialidade");
                String phone = resultSet.getString("telefone");
                Veterinarian veterinarian = new Veterinarian(id, crmv, name, specialty, phone);
                list.add(veterinarian);

            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return list;
    }

    public void save(Veterinarian veterinarian) throws SQLException {
        var sql = """
                insert into veterinarios (nome, crmv, telefone, especialidade) values (?, ?, ?, ?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, veterinarian.getName());
            statement.setString(2, veterinarian.getCrmv().toUpperCase());
            statement.setString(3, veterinarian.getPhone());
            statement.setString(4, veterinarian.getSpeciality());
            statement.execute();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Veterinarian findByCrmv(String crmv) {
        var sql = """
                select * from veterinarios where crmv = ?;
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, crmv);
            var resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            long id = resultSet.getLong("id");
            String name = resultSet.getString("nome");
            String speciality = resultSet.getString("especialidade");
            String phone = resultSet.getString("telefone");
            String crmvEntity = resultSet.getString("crmv");
            return new Veterinarian(id, crmv, name, speciality, phone);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void deleteById(Long id) throws SQLException {
        var sql = """
                delete from consultas where veterinario_id = ?;
                """;
        var sql2 = """
                delete from veterinarios where id = ?;
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.execute();
            try(PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                statement2.setLong(1, id);
                statement2.execute();
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }

    public void update(Veterinarian veterinarian) {
        var sql = """
                update veterinarios 
                set nome = ?,
                crmv = ?,
                especialidade = ?,
                telefone = ?
                where id = ?;
                """;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, veterinarian.getName());
            statement.setString(2, veterinarian.getCrmv().toUpperCase());
            statement.setString(3, veterinarian.getSpeciality());
            statement.setString(4, veterinarian.getPhone());
            statement.setLong(5, veterinarian.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
