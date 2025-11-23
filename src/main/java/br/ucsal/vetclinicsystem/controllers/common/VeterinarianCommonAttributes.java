package br.ucsal.vetclinicsystem.controllers.common;

import br.ucsal.vetclinicsystem.model.dao.VeterinarianDAO;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.util.List;

public abstract class VeterinarianCommonAttributes {
    public TextField nameInput;
    public TextField crmvInput;
    public TextField phoneInput;
    public ChoiceBox<String> specialityChoice;
    protected final VeterinarianDAO dao = new VeterinarianDAO();
    public String[] specialties = {
            "Clínico Geral",
            "Cirurgia Geral",
            "Ortopedia Veterinária",
            "Dermatologia Veterinária",
            "Oftalmologia Veterinária",
            "Odontologia Veterinária",
            "Cardiologia Veterinária",
            "Oncologia Veterinária",
            "Neurologia Veterinária",
            "Nefrologia e Urologia Veterinária",
            "Endocrinologia Veterinária",
            "Anestesiologia Veterinária",
            "Gastroenterologia Veterinária",
            "Pneumologia Veterinária",
            "Infectologia Veterinária",
            "Nutrição Animal",
            "Comportamento Animal",
            "Fisioterapia e Reabilitação Animal",
            "Hematologia Veterinária",
            "Patologia Veterinária",
            "Medicina de Animais Silvestres",
            "Medicina de Animais Exóticos",
            "Medicina Felina",
            "Medicina Canina",
            "Medicina de Aves",
            "Medicina de Animais de Grande Porte",
            "Ginecologia e Obstetrícia Veterinária",
            "Neonatologia Veterinária",
            "Radiologia e Diagnóstico por Imagem"
    };
    protected void initChoiceBox() {
        specialityChoice.getSelectionModel().clearSelection();
        specialityChoice.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String string) {
                return string == null ? "Escolha a especialidade" : string;
            }

            @Override
            public String fromString(String s) {
                return "";
            }
        });
        specialityChoice.setItems(FXCollections.observableList(List.of(specialties)));
    }
    protected boolean validAll(String text) {
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
    protected boolean validAll(Long id, String name, String crmv, String phone, String speciality) throws SQLException {
        if (name == null || name.isEmpty()) {
            warning("Insira um nome valido");
            return false;
        }
        if (crmv == null || crmv.isEmpty() || !isCrmv(crmv)) {
            warning("Insira um crmv valido");
            return false;
        }

        if (phone == null || phone.isEmpty() || validAll(phone)) {
            warning("Insira um telefone valido");
            return false;
        }
        if (speciality == null || speciality.isEmpty()) {
            warning("Insira um especialidade valido");
            return false;
        }
        return validateCrmv(id,crmv);

    }

    protected boolean isCrmv(String crmv) {
        if (crmv == null) return false;
        crmv = crmv.toUpperCase().trim();
        return crmv.matches("^CRMV\\d{3,6}$");
    }
    protected boolean validateCrmv(Long id, String crmv) throws SQLException {

        if (crmv == null || crmv.isBlank() || !isCrmv(crmv)) {
            warning("CRMV inválido (use o formato CRMV1234)");
            return false;
        }

        crmv = crmv.toUpperCase();
        Veterinarian vet = dao.findByCrmv(crmv);

        if (vet == null) return true;


        if (vet.getId().equals(id)) {
            return true;
        }

        warning("CRMV já cadastrado");
        return false;
    }


    void warning(String msg) {
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.setTitle("AVISO");
        alert.setResizable(false);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();

    }


}
