package br.ucsal.vetclinicsystem.controllers.common;

import br.ucsal.vetclinicsystem.model.dao.AnimalDAO;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public abstract class AnimalCommonAttributes {
    public TextField nameInput;
    public TextField ownInput;
    public TextField breedInput;
    public TextField speciesInput;
    public TextField weightInput;
    public DatePicker datePicker;

    protected final AnimalDAO dao = new AnimalDAO();
    protected final OwnerDAO ownerDAO = new OwnerDAO();

    protected boolean validate(String name, String species, String breed, String weight, String own, LocalDate birthdate) throws SQLException {
        if (name == null || name.isEmpty()) {
            warning("Nome Inválido");
            return false;
        }
        if (species == null || species.isEmpty()) {
            warning("Espécie inválida");
            return false;
        }
        if (breed == null || breed.isEmpty()) {
            warning("Raça inválida");
            return false;
        }
        if (!isValidWeight(weight)) {
            warning("Digite um Peso Válido");
            return false;
        }
        if (own == null || own.isEmpty() || validEntry(own)) {
            warning("Digite Um CPF Válido");
            return false;
        }
        List<Owner> byCpf = ownerDAO.findByCpf(own);
        if (byCpf.isEmpty()) {
            warning("Insira Um Dono Cadastrado");
            return false;
        }
        if (birthdate == null || birthdate.isAfter(LocalDate.now())) {
            warning("Data Futura ou Inválida");
            return false;
        }

        return true;
    }

    public boolean isValidWeight(String text) {
        if (text == null || text.isBlank()) return false;

        try {
            float value = Float.parseFloat(text.replace(",", "."));
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
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
