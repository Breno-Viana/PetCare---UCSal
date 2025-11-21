package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerViewController {

    private final String[] tableLabels = {
            "id",
            "nome",
            "cpf",
            "email",
            "telefone",
            "endereço"
    };
    public Button back;
    public Label ownLbl;
    private OwnerDAO dao = new OwnerDAO();

    private ObservableList<Owner> owners = FXCollections.observableList(dao.findAll());

    @FXML
    public void initialize() throws IOException {
        loadColumns();
        setFontsAndStyles();
    }

    private void loadColumns() {
    }

    private void setFontsAndStyles() throws IOException {
        Font font_16 = Font.loadFont(R.principal_font.openStream(), 16);
        Font font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        back.setStyle(R.CSS_BEFORE_ANIMATE);
        back.setFont(font_16);
        back.setText("Voltar");
        ownLbl.setFont(font_42);
    }

    @FXML
    void back(Event event) throws IOException {
        R.animateBtn(back);
        var stage = (Stage) back.getScene().getWindow();

        double width = stage.getWidth();
        double height = stage.getHeight();
        double y = stage.getY();
        double x = stage.getX();

        FXMLLoader loader = new FXMLLoader(R.main_view);
        boolean maximized = stage.isMaximized();

        if (maximized){
            stage.setMaximized(true);
        }else {
            stage.setMinWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);
        }
        stage.setScene(new Scene(loader.load()));
    }
}
