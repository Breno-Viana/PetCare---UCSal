package br.ucsal.vetclinicsystem.controllers;


import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.model.mock.Mock;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MainViewController {


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

    private final ObservableList<Consultation> list = FXCollections.observableList(Mock.consultations);

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
            if (i == 5 || i == 4) {
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
        Tooltip ownToolTip = new Tooltip("Cadastrar Novo Proprietario");
        ownBtn.setTooltip(ownToolTip);
        Tooltip vetToolTip = new Tooltip("Cadastrar Novo Veterinario");
        vetBtn.setTooltip(vetToolTip);
        Tooltip aniToolTip = new Tooltip("Cadastrar Novo Animal");
        aniBtn.setTooltip(aniToolTip);

    }



    @FXML
    void ownEvent(Event event) {
        R.animateBtn(ownBtn);
        eventoTeste();
    }

    @FXML
    void aniEvent(Event event) {
        R.animateBtn(aniBtn);
        eventoTeste();
    }

    @FXML
    void vetEvent(Event event){
        R.animateBtn(vetBtn);
        eventoTeste();
    }

    @FXML
    void consultEvent(Event event) throws IOException {
        R.animateBtn(addBtn);
        openConsultationRegisterView();
    }

    @FXML
    void search(Event event){
        var ev = (KeyEvent)event;
        if (ev.getCode() == KeyCode.ENTER){
            System.out.println(searchArea.getText());
        }
    }


    void openConsultationRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(R.owner_view);
        Parent root = loader.load();

        var ownerController =(OwnerViewController) loader.getController();
        Stage stage = new Stage();
        stage.setTitle("Nova Consulta");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 930, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    void eventoTeste(){
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERTA TESTE");
        alert.setContentText("ALERTA DE TESTE");
        alert.initModality(Modality.APPLICATION_MODAL);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == ButtonType.OK){
            System.out.println("FUNCIONOU");
        }
    }


}
