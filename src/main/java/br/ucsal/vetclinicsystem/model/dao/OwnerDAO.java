package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Address;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {
    private final Connection connection;

    public OwnerDAO() {
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public Owner findById(long idDono) throws SQLException {
        var sql = "select * from proprietarios where id = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, idDono);
            var resultSet = statement.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("id");
            var name = resultSet.getString("nome");
            var cpf = resultSet.getString("cpf");
            var email = resultSet.getString("email");
            var phone = resultSet.getString("telefone");
            var address = new AddressDAO().findById(id);
            return new Owner(id, cpf, name, email, phone, address);

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Owner> findAll() {
        var sql = """
                select * from proprietarios order by id;;
                """;

        var list = new ArrayList<Owner>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                Address byId = new AddressDAO().findById(id);
                list.add(new Owner(id, cpf, nome, email, telefone, byId));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public void save(Owner owner) throws SQLException {
        var sql = """
                insert into proprietarios (cpf, nome, telefone,email) values(?,?,?,?);
                """;
        var sql2 = """
                insert into enderecos (proprietario_id, logradouro, uf, cidade) values(?,?,?,?);
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, owner.getCpf());
            statement.setString(2, owner.getName());
            statement.setString(3, owner.getPhone());
            statement.setString(4, owner.getEmail());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                statement2.setLong(1, generatedKeys.getLong("id"));
                statement2.setString(2, owner.getAddress().getStreet());
                statement2.setString(3, owner.getState());
                statement2.setString(4, owner.getCity());
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Owner> findByCpf(String cpf) throws SQLException {
        var sql = """
                select * from proprietarios where cpf = ?;
                """;

        var list = new ArrayList<Owner>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");
                Address byId = new AddressDAO().findById(id);
                list.add(new Owner(id, cpf, nome, email, telefone, byId));
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return list;
    }


    public void update(Owner owner) throws SQLException {
        var sql = """
                update enderecos 
                set logradouro = ?,
                uf = ?,
                cidade = ?
                where proprietario_id = ?; 
                """;
        var sql2 = """
                update proprietarios 
                set nome = ?,
                cpf = ?,
                email = ?,
                telefone = ?
                where id = ?; 
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, owner.getAddress().getStreet());
            statement.setString(2, owner.getState());
            statement.setString(3, owner.getCity());
            statement.setLong(4, owner.getId());
            statement.executeUpdate();
            try (PreparedStatement statement1 = connection.prepareStatement(sql2)) {
                statement1.setString(1, owner.getName());
                statement1.setString(2, owner.getCpf());
                statement1.setString(3, owner.getEmail());
                statement1.setString(4, owner.getPhone());
                statement1.setLong(5, owner.getId());
                statement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteById(Long id) throws SQLException {
        var sql = """
                delete from enderecos where proprietario_id = ?;
                """;
        var sql2 = """
                delete from proprietarios where id = ?;
                """;

        var aniDao = new AnimalDAO();
        List<Long> animalIdByOwnerId = aniDao.findAnimalIdByOwnerId(id);
        for (Long animalId : animalIdByOwnerId){
            new ConsultationDAO().deleteByAnimalId(animalId);
        }
        aniDao.deleteByOwnerId(id);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                statement2.setLong(1, id);
                statement2.executeUpdate();
            }
            new AnimalDAO().deleteByOwnerId(id);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
