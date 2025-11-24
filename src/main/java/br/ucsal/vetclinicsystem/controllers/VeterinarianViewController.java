package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.controllers.common.VeterinarianCommonAttributes;
import br.ucsal.vetclinicsystem.controllers.modal.veterinarians.VeterinarianEditViewController;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianViewController extends VeterinarianCommonAttributes {
    public Button back;
    public Label vetLbl;
    public TableView<Veterinarian> table;
    public TextField textSearch;
    public Label addLbl;

    public Button addBtn;
    public Button clear;


    private ObservableList<Veterinarian> observableStateList = FXCollections.observableArrayList(dao.findAll());

    private final String[] columnsLabels = {
            "id",
            "nome",
            "crmv",
            "especialidade",
            "telefone",
    };

    private final String[] consumedColumns = {
            "id",
            "name",
            "crmv",
            "speciality",
            "phone"
    };


    @FXML
    void initialize() throws IOException {
        initChoiceBox();
        loadColumns();
        setFontsAndStyles();
    }

    private void loadColumns() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.getColumns().clear();

        for (int i = 0; i < columnsLabels.length; i++) {
            TableColumn<Veterinarian, String> column = new TableColumn<>(columnsLabels[i]);
            column.setResizable(false);
            if (i == 0) {
                column.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
            } else {
                column.prefWidthProperty().bind(table.widthProperty().multiply(0.19));
            }
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(consumedColumns[i]));
            table.getColumns().add(column);
        }
        addEditButton();
        table.setItems(observableStateList);
    }

    private void addEditButton() {
        TableColumn<Veterinarian, Void> column = new TableColumn<>("Editar");
        column.setResizable(false);
        column.prefWidthProperty().bind(table.widthProperty().multiply(0.165));
        column.setStyle("-fx-alignment: CENTER;");
        column.setCellFactory(fac -> new TableCell<>() {
            private final Button edit = new Button("Editar");

            {
                edit.setPrefWidth(110);
                edit.setPrefHeight(30);
                edit.setStyle(R.CSS_BEFORE_ANIMATE);


                edit.setOnAction(ev -> {
                    R.animateBtn(edit);
                    long id = getTableView().getItems().get(getIndex()).getId();
                    try {
                        var controller = new VeterinarianEditViewController().open(id);
                        if (controller.isClosed()) {
                            observableStateList = FXCollections.observableArrayList(dao.findAll());
                            loadColumns();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(edit);
                    setAlignment(Pos.CENTER);
                }
            }

        });

        table.getColumns().add(column);

    }


    void setFontsAndStyles() throws IOException {
        Font font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        Font font_40 = Font.loadFont(R.principal_font.openStream(), 40);
        addLbl.setFont(font_40);
        vetLbl.setFont(font_42);
        back.setStyle(R.CSS_BEFORE_ANIMATE);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
    }

    ;

    public void back(ActionEvent actionEvent) throws IOException {
        R.animateBtn(back);
        var stage = (Stage) back.getScene().getWindow();

        double width = stage.getWidth();
        double height = stage.getHeight();
        double y = stage.getY();
        double x = stage.getX();

        FXMLLoader loader = new FXMLLoader(R.main_view);
        boolean maximized = stage.isMaximized();

        if (maximized) {
            stage.setMaximized(true);
        } else {
            stage.setMinWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);
        }
        stage.setScene(new Scene(loader.load()));
    }

    public void search(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String crmv = textSearch.getText().toUpperCase();
            if (isCrmv(crmv)) {
                Veterinarian vet = dao.findByCrmv(crmv);
                if (vet != null) {
                    observableStateList = FXCollections.observableArrayList(new ArrayList<>(List.of(vet)));
                } else {
                    observableStateList = FXCollections.observableArrayList(new ArrayList<>(dao.findAll()));
                }
            } else {
                observableStateList = FXCollections.observableArrayList(dao.findAll());
            }
            loadColumns();
        }
    }

    public void add(ActionEvent actionEvent) throws SQLException {
        String name = nameInput.getText();
        String crmv = crmvInput.getText();
        String phone = phoneInput.getText();
        String speciality = specialityChoice.getValue();
        if (!validAll(null, name, crmv, phone, speciality)) {
            return;
        }

        dao.save(new Veterinarian(null, crmv, name, speciality, phone));
        var confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(null);
        confirmation.initStyle(StageStyle.UNDECORATED);
        confirmation.initModality(Modality.APPLICATION_MODAL);
        confirmation.setContentText("Veterinário adicionado com sucesso!");
        confirmation.showAndWait();
        clear(null);
        observableStateList = FXCollections.observableArrayList(dao.findAll());
        loadColumns();
    }


    public void clear(ActionEvent actionEvent) {
        R.animateBtn(clear);
        nameInput.clear();
        crmvInput.clear();
        phoneInput.clear();
        specialityChoice.getSelectionModel().clearSelection();

    }

}
