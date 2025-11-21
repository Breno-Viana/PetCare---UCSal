package br.ucsal.vetclinicsystem.controllers.modal.consultations;

import br.ucsal.vetclinicsystem.controllers.common.ConsulteCommonAttributes;
import br.ucsal.vetclinicsystem.model.dao.ConsultationDAO;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ConsultationRegisterController extends ConsulteCommonAttributes {

    @FXML
    public Label consulLabel;
    @FXML
    public Button addBtn;
    @FXML
    public Button cancelBtn;
    private boolean confirmed = false;


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
        this.hour.setStyle(R.CSS_CHOICEBOX);
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

        if (!validate(animal, vet, text, text1, value, hourValue)) return;
        String replace = text.replace(",", ".");
        LocalTime time = LocalTime.parse(hourValue);
        LocalDateTime dateTime = LocalDateTime.of(value, time);
        var consult = new Consultation(null, animal, vet,dateTime, text1, new BigDecimal(replace));
        new ConsultationDAO().save(consult);
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

    public ConsultationRegisterController openRegister() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(R.consult_add_view);
        Parent root = loader.load();
        ConsultationRegisterController ownerController = loader.getController();
        stage.setTitle("Nova Consulta");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 930, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return ownerController;
    }




    @FXML
    void close(Event event){
        KeyEvent keyEvent = (KeyEvent) event;
        if (keyEvent.getCode() == KeyCode.ESCAPE){
            var stage = (Stage)pane.getScene().getWindow();
            stage.close();
        }
    }
}
