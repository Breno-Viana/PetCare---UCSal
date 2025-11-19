package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.model.mock.Mock;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.io.IOException;

public class OwnerViewController {

    private ObservableList<Veterinarian> veterinarians = FXCollections.observableList(Mock.veterinarians);

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
        vetChoice.setConverter(new javafx.util.StringConverter<Veterinarian>() {
            public String toString(Veterinarian vet) {
                return vet == null ? "Selecione um veterinário" : vet.getName();
            }
            public Veterinarian fromString(String s) {
                return null;
            }
        });

        vetChoice.setValue(null);

        vetChoice.setItems(veterinarians);
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
    }

    @FXML
    void cancel(Event event){
        R.animateBtn(cancelBtn);
    }
}
