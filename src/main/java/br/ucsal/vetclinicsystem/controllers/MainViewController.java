package br.ucsal.vetclinicsystem.controllers;


import br.ucsal.vetclinicsystem.model.mock.Consulta;
import br.ucsal.vetclinicsystem.model.mock.Mock;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class MainViewController {
    private final String[] columnsName = {
            "id da consulta",
            "animal",
            "veterinario responsavel",
            "data/hora",
            "diagnostico",
            "valor",
            "cpf do dono"};
    private final String[] columnsValue = {
            "consulId",
            "animalId",
            "vetelId",
            "dataHora",
            "diagnostico",
            "valor",
            "consultaPorCpf"
    };

    private final ObservableList<Consulta> list = FXCollections.observableList(Mock.consultas);

    @FXML
    public ImageView logo;

    @FXML
    public AnchorPane main;
    @FXML
    public TextField searchArea;
    @FXML
    public TableView<Consulta> table;
    @FXML
    public Label consult_label;

    @FXML
    public void initialize() {
        loadColumns();
        setFonts();
        searchEvent();
    }

    private void loadColumns() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-fixed-cell-size: 40px;");
        for (int i = 0; i <3 ; i++) {
            TableColumn<Consulta, Integer> column = new TableColumn<>(columnsName[i]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
            column.setCellValueFactory(new PropertyValueFactory<>(columnsValue[i]));
            column.setStyle("-fx-alignment: CENTER;");
            column.setResizable(false);
            table.getColumns().add(column);
        }
        for (int i = 3; i <7 ; i++) {
            TableColumn<Consulta, String> column = new TableColumn<>(columnsName[i]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
            column.setCellValueFactory(new PropertyValueFactory<>(columnsValue[i]));
            if (i == 6){
                column.setStyle("-fx-alignment: CENTER;");
            }

            column.setResizable(false);
            table.getColumns().add(column);
        }
        addEditButton();
        table.setItems(list);
    }

    private void addEditButton() {
        TableColumn<Consulta, Void> column = new TableColumn<>("Editar");
        column.setResizable(false);
        column.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        column.setStyle("-fx-alignment: CENTER;");
        column.setCellFactory(fac -> new TableCell<>() {
            private final Button edit = new Button("Editar");

            {
                edit.setPrefWidth(110);
                edit.setPrefHeight(30);

                edit.setStyle(
                        "-fx-background-color: #6eafcc; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-weight: bold; " +
                                "-fx-cursor: hand; " +
                                "-fx-border-radius: 5px; " +
                                "-fx-background-radius: 5px;"
                );

                edit.setOnAction(e -> {
                    animarBotao(edit);
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
    private void animarBotao(Button btn) {
        btn.setStyle("-fx-background-color: #5d899c;" +
                "-fx-text-fill: #2fadbd");
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), btn);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        scaleTransition.setOnFinished(e -> {
            btn.setStyle("-fx-background-color: #6eafcc; -fx-text-fill: white;");
        });
    }



    private void setFonts(){
        Font font = Font.loadFont(R.principal_font, 42);
        consult_label.setFont(font);
    }
    private void searchEvent() {
        searchArea.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                System.out.println(searchArea.getText());
            }
        });
    }


}
