package br.ucsal.vetclinicsystem.controllers.modal.owners;

import br.ucsal.vetclinicsystem.controllers.common.OwnerCommonAttributes;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Address;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.Optional;

public class OwnerEditController extends OwnerCommonAttributes {

    private Long id;
    public void setId(Long id) throws SQLException {
        this.id = id;
        searchAndSet();
    }
    public Long getId(){
        return id;
    }



    @FXML
    public AnchorPane pane;
    @FXML
    public Label editLbl;
    @FXML

    public Button delBtn;
    @FXML
    public Button addBtn;
    private boolean closed = false;
    public boolean isClosed() {
        return closed;
    }

    public OwnerEditController open(Long id) throws IOException, SQLException {
        Stage stage = new Stage();
        var loader  = new FXMLLoader(R.edit_owner_view);
        Parent root = loader.load();
        OwnerEditController ownerEditController = loader.getController();
        ownerEditController.setId(id);
        stage.setResizable(false);
        stage.setTitle("Editar Proprietário");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        return ownerEditController;

    }

    @FXML
    void initialize() throws IOException {
        setStylesAndFonts();
    }

    private void setStylesAndFonts() throws IOException {
        var font_40 = Font.loadFont(R.principal_font.openStream(), 40);
        Font font_16 = Font.loadFont(R.principal_font.openStream(), 16);
        editLbl.setFont(font_40);
        delBtn.setStyle(R.CSS_DELETE_BTN);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        delBtn.setFont(font_16);
        addBtn.setFont(font_16);

    }


    public void add(ActionEvent actionEvent) throws SQLException {
        R.animateBtn(addBtn);
        String name = nameInput.getText();
        String cpf = cpfInput.getText();
        String email = emailInput.getText();
        String phone = phoneInput.getText();
        String state = stateInput.getValue();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String num = numInput.getText().trim();
        if (!validate(id,name, cpf, email, phone, state, street, city, num)){
            return;
        }
        var streetNum = String.format("%s,%s", street, num.trim());
        var owner = new Owner(id, cpf, name, email, phone, new Address(null,streetNum,city,state));

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
            dao.update(owner);
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
    private void searchAndSet() throws SQLException {
        initCheckBox();
        Owner owner = dao.findById(id);
        nameInput.setText(owner.getName());
        phoneInput.setText(owner.getPhone());
        cpfInput.setText(owner.getCpf());
        emailInput.setText(owner.getEmail());
        stateInput.setValue(owner.getAddress().getState());
        cityInput.setText(owner.getAddress().getCity());
        streetInput.setText(owner.getStreet());
        numInput.setText(owner.getNum());

    }
    private final List<String> statesList = List.of(
            "AC", "AL", "AP", "AM", "BA",
            "CE", "DF", "ES", "GO", "MA",
            "MT", "MS", "MG", "PA", "PB",
            "PR", "PE", "PI", "RJ", "RN",
            "RS", "RO", "RR", "SC", "SP",
            "SE", "TO"
    );
    private void initCheckBox() {
        stateInput.setItems(FXCollections.observableList(statesList));
    }


}
