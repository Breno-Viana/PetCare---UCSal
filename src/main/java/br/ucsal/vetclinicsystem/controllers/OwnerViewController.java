package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.model.mock.Mock;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OwnerViewController {


    private final String regex = "^\\d+(,\\d{0,2})?$";

    private String[] hours = {
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

    private ObservableList<Veterinarian> veterinarians = FXCollections.observableList(Mock.veterinarians);
    private ObservableList<Animal> animals = FXCollections.observableList(Mock.animals);


    @FXML
    public ChoiceBox<String> hour;
    @FXML
    public DatePicker datePick;
    @FXML
    public ChoiceBox<Animal> animaiChoice;
    @FXML
    public TextField diagText;
    @FXML
    public TextField valueText;
    @FXML
    public Label consulLabel;
    @FXML
    public Button addBtn;
    @FXML
    public Button cancelBtn;
    private boolean confirmed = false;

    @FXML
    private ChoiceBox<Veterinarian> vetChoice;

    public boolean isConfirmed(){
        return confirmed;
    }

    @FXML void initialize() throws IOException {
        setStyles();
        initChoiceBox();
    }

    private void initChoiceBox() {
        vetChoice.setValue(null);
        vetChoice.setConverter(new javafx.util.StringConverter<Veterinarian>() {
            public String toString(Veterinarian vet) {
                return vet == null ? "Selecione um veterinário" : vet.getName();
            }
            public Veterinarian fromString(String s) {
                return null;
            }
        });
        vetChoice.setItems(veterinarians);
        animaiChoice.setValue(null);
        animaiChoice.setConverter(new StringConverter<Animal>() {
            public String toString(Animal animal) {
                return animal == null? "Animal para Consulta":String.format("%s | %s",animal.getName() ,animal.getOwner());
            }
            public Animal fromString(String s) {
                return null;
            }
        });
        animaiChoice.setItems(animals);
        hour.setValue(null);
        hour.setConverter(new StringConverter<String>() {

            @Override
            public String toString(String s) {
                return s == null? "Escolha um Horario":s;
            }

            @Override
            public String fromString(String s) {
                return "";
            }
        });
        hour.setItems(FXCollections.observableList(List.of(hours)));
    }

    private void setStyles() throws IOException {
        Font font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        consulLabel.setFont(font_42);
        this.addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        this.cancelBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        this.vetChoice.setStyle(R.CSS_CHOICEBOX);
        this.animaiChoice.setStyle(R.CSS_CHOICEBOX);
    }

    @FXML
    void add(Event event){
        R.animateBtn(addBtn);
        Animal animal = animaiChoice.getValue();
        Veterinarian vet = vetChoice.getValue();
        String text = valueText.getText();
        String text1 = diagText.getText();
        LocalDate value = datePick.getValue();
        String hourValue = hour.getValue();
        if (animal == null || vet == null || text.isBlank()||!text.matches(regex)||text1.isBlank()||value.isBefore(LocalDate.now())||hourValue.isBlank()){ warning();return;}
        var i = Mock.consultations.size() + 1;
        var consult = new Consultation((long)i, animal, vet, String.format("%s / %s", value.toString(), hourValue), text1, new BigDecimal(text));
        Mock.consultations.add(consult);
        confirmed = true;
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancel(Event event){
        R.animateBtn(cancelBtn);
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public OwnerViewController open() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(R.owner_view);
        Parent root = loader.load();
        OwnerViewController ownerController = loader.getController();
        stage.setTitle("Nova Consulta");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 930, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return ownerController;
    }

    private void warning(){
        var warningAlert = new Alert(Alert.AlertType.WARNING);
        warningAlert.setResizable(false);
        warningAlert.setTitle("SOLICITAÇÃO INVÁLIDA");
        warningAlert.initModality(Modality.APPLICATION_MODAL);
        warningAlert.setContentText("INSIRA VALORES VALIDOS E OS CAMPOS OBRIGATÓRIOS");
        warningAlert.showAndWait();
    }
}
