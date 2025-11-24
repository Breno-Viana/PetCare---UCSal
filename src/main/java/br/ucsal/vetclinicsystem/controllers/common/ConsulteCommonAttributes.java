package br.ucsal.vetclinicsystem.controllers.common;

import br.ucsal.vetclinicsystem.controllers.modal.consultations.AnimalChoiceController;
import br.ucsal.vetclinicsystem.model.dao.AnimalDAO;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.dao.VeterinarianDAO;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public abstract class ConsulteCommonAttributes {
    protected final String regex = "^(0|[1-9]\\d*)([.,]\\d{1,2})?$";
;

    @FXML
    protected AnchorPane pane;

    private final String[] errors = {
            "ESCOLHA UM VETERINÁRIO VÁLIDO",
            "ESCOLHA UM ANIMAL VÁLIDO",
            "ESCOLHA UM HORÁRIO VÁLIDO",
            "ESCOLHA UM DATA VÁLIDA",
            "INSIRA UM DIAGNOSTICO VÁLIDO",
            "INSIRA UM VALOR VÁLIDO"
    };
    protected String[] hours = {
            "08:00", "08:30",
            "09:00", "09:30",
            "10:00", "10:30",
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00"
    };


    protected ObservableList<Veterinarian> veterinarians = FXCollections.observableList(new VeterinarianDAO().findAll());
    protected ObservableList<Animal> animals = FXCollections.observableList(new AnimalDAO().findAll());

    @FXML
    protected ChoiceBox<String> hour;
    @FXML
    protected DatePicker datePick;
    @FXML
    protected ChoiceBox<Veterinarian> vetChoice;
    @FXML
    protected TextField diagText;
    @FXML
    protected TextField valueText;
    @FXML
    protected TextField ownerCpf;
    @FXML
    protected Button searchOwner;

    protected Animal animal;

    protected void setAnimal(Animal animal) {
        this.animal = animal;
    }
    protected AnimalDAO animalDAO = new AnimalDAO();

    protected void warning(String msg) {
        var warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setResizable(false);
        warningAlert.setTitle("SOLICITAÇÃO INVÁLIDA");
        warningAlert.initModality(Modality.APPLICATION_MODAL);
        warningAlert.setContentText(msg);
        warningAlert.showAndWait();
    }

    public boolean validate(Animal animal, Veterinarian vet, String value, String diagnosis, LocalDate date, String hourValue) {
        if (animal == null) {
            warning(errors[1]);
            return false;
        }
        if (vet == null){
            warning(errors[0]);
            return false;
        }
        if (hourValue == null || hourValue.isBlank()){
            warning(errors[2]);
            return false;
        }
        if (value == null || value.isBlank() || !value.matches(regex)){
            warning(errors[5]);
            return false;
        }
        if (date == null){
            warning(errors[3]);
            return false;
        }
        if (diagnosis == null||diagnosis.isBlank()){
            warning(errors[4]);
            return false;
        }

        return true;
    }

}
