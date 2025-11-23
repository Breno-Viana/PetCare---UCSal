package br.ucsal.vetclinicsystem.controllers.common;

import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.List;

public class OwnerCommonAttributes {

    protected OwnerDAO dao = new OwnerDAO();
    @FXML
    public TextField nameInput;
    @FXML
    public TextField phoneInput;
    @FXML
    public TextField cpfInput;
    @FXML
    public TextField emailInput;
    @FXML
    public ChoiceBox<String> stateInput;
    @FXML
    public TextField cityInput;
    @FXML
    public TextField streetInput;
    @FXML
    public TextField numInput;

    protected boolean validate(Long id, String name, String cpf, String email, String phone, String state, String street, String city, String num) throws SQLException {
        if (name == null || name.isBlank()) {
            warning("Nome Inválido");
            return false;
        }
        if (cpf == null || cpf.isBlank() || validEntry(cpf)) {
            warning("Cpf Inválido, insira SOMENTE os NÚMEROS");
            return false;
        }
        List<Owner> byCpf = dao.findByCpf(cpf);
        if (!byCpf.isEmpty()) {

            Owner first = byCpf.getFirst();

            if (id == null) {
                warning("Cpf Ja cadastrado");
                return false;
            }

            if (!first.getId().equals(id)) {
                warning("Cpf Ja cadastrado");
                return false;
            }

        }

        if (email == null || email.isBlank()) {
            warning("email inválido");
            return false;
        }
        if (phone == null || phone.isBlank() || validEntry(phone)) {
            warning("telefone inválido, insira SOMENTE os NÚMEROS");
        }
        if (state == null || state.isBlank()) {
            warning("estado inválido");
            return false;
        }
        if (city == null || city.isBlank()) {
            warning("cidade inválida");
            return false;
        }
        if (street == null || street.isBlank()) {
            warning("insira uma rua válida");
            return false;
        }
        if (num == null || num.isBlank() || !isANum(num)) {
            warning("insira um número válido");
            return false;
        }
        ;

        return true;

    }

    protected boolean isANum(String num) {
        return num.matches("^[0-9]+$");
    }

    protected void warning(String msg) {
        var warning = new Alert(Alert.AlertType.WARNING);
        warning.setTitle("Inserção Inválida");
        warning.setContentText(msg);
        warning.initModality(Modality.APPLICATION_MODAL);
        warning.initStyle(StageStyle.UNDECORATED);
        warning.show();
    }

    protected boolean validEntry(String text) {
        String[] split = text.split("");
        if (split.length == 11) {
            for (String s : split) {
                try {
                    Integer.parseInt(s);
                } catch (RuntimeException r) {
                    return true;
                }
            }
            return false;
        } else return true;
    }
}
