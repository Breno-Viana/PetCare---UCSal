package br.ucsal.vetclinicsystem.controllers.modal.animal;

import br.ucsal.vetclinicsystem.controllers.common.AnimalCommonAttributes;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AnimalEditViewController extends AnimalCommonAttributes {
    public Button delete;
    public Button addBtn;
    public Label editLbl;

    public AnchorPane pane;

    private boolean closed;
    public boolean isClosed(){
        return this.closed;
    }

    private Long id;
    public void setId(Long id ){
        this.id = id;
        searchAndSet();
    }
    @FXML
    void initialize() throws IOException {
        setFontsAndStyles();
    }

    private void setFontsAndStyles() throws IOException {
        var font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        editLbl.setFont(font_42);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        delete.setStyle(R.CSS_DELETE_BTN);
    }

    private void searchAndSet() {
        Animal animal = dao.findById(this.id);
        ownInput.setText(animal.getOwnerO().getCpf());
        nameInput.setText(animal.getName());
        breedInput.setText(animal.getBreed());
        speciesInput.setText(animal.getSpecies());
        weightInput.setText(String.valueOf(animal.getWeight()));
        datePicker.setValue(animal.getBirthdate());
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        R.animateDeleteBtn(delete);
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmar");
        confirmation.initStyle(StageStyle.UNDECORATED);
        confirmation.initModality(Modality.APPLICATION_MODAL);
        confirmation.setContentText(String.format("Deseja Mesmo Deletar %s?", nameInput.getText()));
        confirmation.getButtonTypes().clear();
        ButtonType yes = new ButtonType("Sim");
        ButtonType not = new ButtonType("Não");
        confirmation.getButtonTypes().addAll(yes, not);
        Optional<ButtonType> buttonType = confirmation.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == yes){
            dao.delete(id);
            Alert information = new Alert(Alert.AlertType.INFORMATION);
            information.setTitle("DELETADO");
            information.setContentText(String.format("%s deletado", nameInput.getText()));
            information.initOwner(pane.getScene().getWindow());
            information.initStyle(StageStyle.UNDECORATED);
            information.showAndWait();
            closed = true;
            var stage = (Stage)pane.getScene().getWindow();
            stage.close();
        }
    }

    public void update(ActionEvent actionEvent) throws SQLException {
        R.animateBtn(addBtn);
        String weight = weightInput.getText();
        String breed = breedInput.getText();
        String species = speciesInput.getText();
        LocalDate birth = datePicker.getValue();
        String name = nameInput.getText();
        String owner = ownInput.getText();
        if (!validate(name, species, breed, weight, owner, birth)) {
            return;
        }
        List<Owner> byCpf = ownerDAO.findByCpf(owner);
        float v = Float.parseFloat(weight.trim().replace(",", "."));
        var animal = new Animal(id, byCpf.getFirst(), name, species, breed, birth, v);

        var confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initModality(Modality.APPLICATION_MODAL);
        confirmation.initStyle(StageStyle.UNDECORATED);
        confirmation.setTitle("Atualizar");
        confirmation.setContentText("Salvar Mudanças?");

        confirmation.getButtonTypes().clear();

        ButtonType yes = new ButtonType("Sim");
        ButtonType not = new ButtonType("Não");
        confirmation.getButtonTypes().addAll(yes, not);

        Optional<ButtonType> buttonType = confirmation.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == yes){
            dao.update(animal);
            var alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            alert.initOwner(pane.getScene().getWindow());
            alert.setContentText(String.format("%s de %s atualizado", animal.getName(), animal.getOwner()));
            alert.showAndWait();
            this.closed = true;
            var stage = (Stage)pane.getScene().getWindow();
            stage.close();
        }

    }

    public AnimalEditViewController open(long id) throws IOException {
        Stage stage = new Stage();
        var loader = new FXMLLoader(R.animal_edit_view);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        AnimalEditViewController controller = loader.getController();
        controller.setId(id);
        stage.showAndWait();


        return controller;

    }
}
