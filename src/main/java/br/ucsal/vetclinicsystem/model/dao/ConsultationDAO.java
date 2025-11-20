package br.ucsal.vetclinicsystem.model.dao;

import br.ucsal.vetclinicsystem.config.PostgresConnectionConfig;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDAO  {

    private final Connection connection;

    public ConsultationDAO(){
        this.connection = PostgresConnectionConfig.getConnectionInstance();
    }

    public List<Consultation> findAll(){
        var sql = "select * from consultas;";
        List<Consultation> consultations = new ArrayList<>();
        try {
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                var animal = new AnimalDAO().findById(resultSet.getLong("animal_id"));
                var vet = new VeterinarianDAO().findById(resultSet.getLong("veterinario_id"));
                Timestamp dateTime = resultSet.getTimestamp("data_hora");
                String diagnosis = resultSet.getString("diagnostico");
                var value = resultSet.getBigDecimal("valor");
                var consultation = new Consultation(id, animal, vet, dateTime.toLocalDateTime(), diagnosis, value);
                consultations.add(consultation);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consultations;
    }
    public void save(Consultation consultation){
        var sql = "insert into consultas (animal_id, veterinario_id, data_hora, diagnostico, valor) values (?,?,?,?,?);";
        try(PreparedStatement statement = this.connection.prepareStatement(sql)){
            statement.setLong(1, consultation.getAnimalO().getId());
            statement.setLong(2, consultation.getVetO().getId());
            statement.setTimestamp(3, Timestamp.valueOf(consultation.getDateTime()));
            statement.setString(4, consultation.getDiagnosis());
            statement.setBigDecimal(5, consultation.getValue());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public Consultation findById(long id){
        var sql = "select * from consultas as c where c.id = ?;";
        Consultation consultation = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id1 = resultSet.getLong("id");
                Animal animalId = new AnimalDAO().findById(resultSet.getLong("animal_id"));
                Veterinarian veterinarian = new VeterinarianDAO().findById(resultSet.getLong("veterinario_id"));
                LocalDateTime dataHora = resultSet.getTimestamp("data_hora").toLocalDateTime();
                String diag = resultSet.getString("diagnostico");
                BigDecimal value = resultSet.getBigDecimal("valor");
                consultation = new Consultation(id1, animalId, veterinarian, dataHora, diag, value);

            }
        }catch (SQLException e){
            throw new RuntimeException();
        }
        return consultation;
    }

    public void deleteById(long id){
        var sql = "delete from consultas as c where c.id = ?;";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.execute();
        }catch (SQLException e){
            throw new RuntimeException();
        };
    }

}
