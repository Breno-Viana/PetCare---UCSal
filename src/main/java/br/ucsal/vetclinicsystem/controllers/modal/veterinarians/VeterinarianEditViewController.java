package br.ucsal.vetclinicsystem.controllers.modal.veterinarians;

import br.ucsal.vetclinicsystem.controllers.common.VeterinarianCommonAttributes;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
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
import java.util.Optional;

public class VeterinarianEditViewController extends VeterinarianCommonAttributes {
    public Button delBtn;
    public Button addBtn;
    public Label editLbl;
    private Long id;

    public AnchorPane pane;


    @FXML
    void initialize() throws IOException {
        setFontsAndStyles();
        initChoiceBox();
    }

    private boolean closed;
    public boolean isClosed() {
        return closed;
    }


    void setFontsAndStyles() throws IOException {
        var font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        editLbl.setFont(font_42);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        delBtn.setStyle(R.CSS_DELETE_BTN);
    }

    public void setId(Long id) {
        this.id = id;
        searchAndSet();
    }

    private void searchAndSet() {
        Veterinarian veterinarian = dao.findById(id);
        String crmv = veterinarian.getCrmv();
        String name = veterinarian.getName();
        String phone = veterinarian.getPhone();
        String speciality = veterinarian.getSpeciality();
        specialityChoice.setValue(speciality);
        crmvInput.setText(crmv);
        nameInput.setText(name);
        phoneInput.setText(phone);
    }

    public VeterinarianEditViewController open(Long id) throws IOException {
        var stage = new Stage();
        var loader = new FXMLLoader(R.veterinarian_edit_view);
        Parent root = loader.load();
        VeterinarianEditViewController controller = loader.getController();
        controller.setId(id);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Editar Veterinário");
        stage.showAndWait();
        return controller;
    }

    public void add(ActionEvent actionEvent) throws SQLException {
        R.animateBtn(addBtn);
        String name = nameInput.getText();
        String crmv = crmvInput.getText().toUpperCase();
        String phone = phoneInput.getText();
        String speciality = specialityChoice.getValue();
        if (!validAll(id,name, crmv, phone, speciality)) {
            return;
        }
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
        if (buttonType.isPresent() && buttonType.get() == yes ){
            var stage = (Stage)pane.getScene().getWindow();
            dao.update(new Veterinarian(id, crmv, name, speciality, phone));
            stage.close();
            closed = true;
        }
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        R.animateDeleteBtn(delBtn);
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
            dao.deleteById(id);
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


}
