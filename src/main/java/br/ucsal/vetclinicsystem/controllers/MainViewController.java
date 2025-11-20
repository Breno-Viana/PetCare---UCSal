package br.ucsal.vetclinicsystem.controllers;


import br.ucsal.vetclinicsystem.controllers.consultations.ConsultationEditController;
import br.ucsal.vetclinicsystem.controllers.consultations.ConsultationRegisterController;
import br.ucsal.vetclinicsystem.model.dao.ConsultationDAO;
import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class MainViewController {

    private final ConsultationDAO consultationDAO = new ConsultationDAO();

    private final String[] columnsName = {
            "id da consulta",
            "animal",
            "veterinario responsavel",
            "data/hora",
            "diagnostico",
            "valor"};
    private final String[] columnsValue = {
            "id",
            "animal",
            "vet",
            "dateTime",
            "diagnosis",
            "value",
    };

    private ObservableList<Consultation> list = FXCollections.observableList(consultationDAO.findAll());

    @FXML
    private ImageView logo;
    @FXML
    private AnchorPane main;
    @FXML
    private TextField searchArea;
    @FXML
    private TableView<Consultation> table;
    @FXML
    private Label consult_label;
    @FXML
    private Button addBtn;
    @FXML
    private Button vetBtn;
    @FXML
    private Button aniBtn;
    @FXML
    private Button ownBtn;


    @FXML
    public void initialize() throws IOException {
        loadColumns();
        setFontsAndStyles();
    }

    private void loadColumns() {
//        list = FXCollections.observableList(consultationDAO.findAll());
        table.getColumns().clear();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-fixed-cell-size: 40px;");
        {
            TableColumn<Consultation, Integer> column = new TableColumn<>(columnsName[0]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
            column.setCellValueFactory(new PropertyValueFactory<>(columnsValue[0]));
            column.setResizable(false);
            table.getColumns().add(column);
        }

        for (int i = 1; i < 6; i++) {
            TableColumn<Consultation, String> column = new TableColumn<>(columnsName[i]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
            column.setCellValueFactory(new PropertyValueFactory<>(columnsValue[i]));
            if (i == 5 || i == 4 || i == 3) {
                column.setStyle("-fx-alignment: CENTER;");
            }

            column.setResizable(false);
            table.getColumns().add(column);
        }
        addEditButton();
        table.setItems(list);
    }

    private void addEditButton() {
        TableColumn<Consultation, Void> column = new TableColumn<>("Editar");
        column.setResizable(false);
        column.prefWidthProperty().bind(table.widthProperty().multiply(0.13));
        column.setStyle("-fx-alignment: CENTER;");
        column.setCellFactory(fac -> new TableCell<>() {
            private final Button edit = new Button("Editar");

            {
                edit.setPrefWidth(110);
                edit.setPrefHeight(30);

                edit.setStyle(R.CSS_BEFORE_ANIMATE);

                edit.setOnAction(e -> {
                    R.animateBtn(edit);

                    long id = getTableView().getItems().get(getIndex()).getId();
                    try {
                        var controller = new ConsultationEditController().openEdit(id);
                        if (controller.isClosed()) {
                            list = FXCollections.observableList(consultationDAO.findAll());
                            loadColumns();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
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


    private void setFontsAndStyles() throws IOException {
        Font font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        consult_label.setFont(font_42);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        addBtn.setText("Nova Consulta +");

        vetBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        aniBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        ownBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        Tooltip ownToolTip = new Tooltip("Ir para área do proprietário");
        ownBtn.setTooltip(ownToolTip);
        Tooltip vetToolTip = new Tooltip("Ir para área do veterinário");
        vetBtn.setTooltip(vetToolTip);
        Tooltip aniToolTip = new Tooltip("Ir para área do animal");
        aniBtn.setTooltip(aniToolTip);

    }




    @FXML
    void consultEvent(Event event) throws IOException {
        R.animateBtn(addBtn);
        openConsultationRegisterView();
    }

    @FXML
    void search(Event event) throws SQLException {
        var ev = (KeyEvent) event;
        if (ev.getCode() == KeyCode.ENTER) {
            if (validEntry()) {
                list = FXCollections.observableList(consultationDAO.findByOwnerCpf(searchArea.getText()));
                loadColumns();
            }else {
                list = FXCollections.observableList(consultationDAO.findAll());
                loadColumns();
                if (searchArea.getText().isBlank()) return;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("DIGITE O CPF COMPLETO E SOMENTE OS NUMEROS");
                alert.setTitle("CPF INVÁLIDO");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.show();
            }
        }
    }

    private boolean validEntry() {
        String text = searchArea.getText();
        String[] split = text.split("");
        if (split.length == 11){
            for (String s : split) {
                try {
                    Integer.parseInt(s);
                } catch (RuntimeException r) {
                    return false;
                }
            }
            return true;
        }else return false;
    }


    void openConsultationRegisterView() throws IOException {
        var controller = new ConsultationRegisterController().openRegister();
        if (controller.isConfirmed()) {
            list = FXCollections.observableList(consultationDAO.findAll());
            loadColumns();
        }
    }

    void eventoTeste() {
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERTA TESTE");
        alert.setContentText("ALERTA DE TESTE");
        alert.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
            System.out.println("FUNCIONOU");
        }
    }

    @FXML
    void loadVeterinariaArea(Event event){
        R.animateBtn(vetBtn);
    }

    @FXML
    void loadOwnerArea(Event event){
        R.animateBtn(ownBtn);
    }

    @FXML
    void loadAnimalArea(Event event){
        R.animateBtn(aniBtn);
    }


}
