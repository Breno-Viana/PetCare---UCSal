package br.ucsal.vetclinicsystem.controllers.common;

import br.ucsal.vetclinicsystem.model.dao.AnimalDAO;
import br.ucsal.vetclinicsystem.model.dao.VeterinarianDAO;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public abstract class ConsulteCommonAttributes {
    protected final String regex = "^\\d+(,\\d{0,2})?$";

    @FXML
    protected AnchorPane pane;

    protected String[] hours = {
            "08:00",
            "09:00",
            "10:00",
            "11:00",
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00"
    };

    protected ObservableList<Veterinarian> veterinarians = FXCollections.observableList(new VeterinarianDAO().findAll());
    protected ObservableList<Animal> animals = FXCollections.observableList(new AnimalDAO().findAll());

    @FXML
    protected ChoiceBox<String> hour;
    @FXML
    protected DatePicker datePick;
    @FXML
    protected ChoiceBox<Animal> animaiChoice;
    @FXML
    protected ChoiceBox<Veterinarian> vetChoice;
    @FXML
    protected TextField diagText;
    @FXML
    protected TextField valueText;
}
