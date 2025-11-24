package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.controllers.common.OwnerCommonAttributes;
import br.ucsal.vetclinicsystem.controllers.modal.owners.OwnerEditController;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Address;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OwnerViewController extends OwnerCommonAttributes {

    private final String[] tableLabels = {
            "id",
            "nome",
            "cpf",
            "email",
            "telefone",
            "estado",
            "cidade",
            "logradouro",
            "numero"
    };
    private final List<String> statesList = List.of(
            "AC", "AL", "AP", "AM", "BA",
            "CE", "DF", "ES", "GO", "MA",
            "MT", "MS", "MG", "PA", "PB",
            "PR", "PE", "PI", "RJ", "RN",
            "RS", "RO", "RR", "SC", "SP",
            "SE", "TO"
    );
    private final ObservableList<String> observableStateList = FXCollections.observableList(statesList);

    private final String[] cosumedString = {
            "id",
            "name",
            "cpf",
            "email",
            "formatedPhone",
            "state",
            "city",
            "street",
            "num"
    };
    public Button back;
    public Label ownLbl;
    public TableView<Owner> table;
    public TextField textSearch;
    public Label addLbl;
    public Button addBtn;
    public Button clear;

    private ObservableList<Owner> owners = FXCollections.observableList(dao.findAll());

    @FXML
    public void initialize() throws IOException {
        loadColumns();
        initChoice();
        setFontsAndStyles();
    }

    private void initChoice() {
        stateInput.getSelectionModel().clearSelection();

        stateInput.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String s) {
                return s == null? "Escolha a UF: ":s;
            }

            @Override
            public String fromString(String s) {
                return "";
            }
        });
        stateInput.setItems(observableStateList);
    }

    private void loadColumns() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.getColumns().clear();
        table.setStyle("-fx-fixed-cell-size: 40px;");
        {
            TableColumn<Owner, Long> column = new TableColumn<>(tableLabels[0]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
            column.setCellValueFactory(new PropertyValueFactory<>(cosumedString[0]));
            column.setResizable(false);
            column.setStyle("-fx-alignment: CENTER;");
            table.getColumns().add(column);
        }
        for (int i = 1; i < tableLabels.length; i++) {
            TableColumn<Owner, String> column = new TableColumn<>(tableLabels[i]);
            if (i == 5 || i == 8) {
                column.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
            } else if (i == 7) {
                column.prefWidthProperty().bind(table.widthProperty().multiply(0.10));
            } else {
                column.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
            }
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(cosumedString[i]));
            column.setResizable(false);
            table.getColumns().add(column);

        }

        addEditButton();

        table.setItems(owners);
    }

    private void addEditButton() {
        TableColumn<Owner, Void> column = new TableColumn<>("Editar");
        column.setResizable(false);
        column.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
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
                        var controller = new OwnerEditController().open(id);
                        if (controller.isClosed()){
                            owners = FXCollections.observableList(dao.findAll());
                            loadColumns();
                        }
                    } catch (IOException | SQLException e) {
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


    private void setFontsAndStyles() throws IOException {
        Font font_16 = Font.loadFont(R.principal_font.openStream(), 16);
        Font font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        Font font_40 = Font.loadFont(R.principal_font.openStream(), 40);
        back.setStyle(R.CSS_BEFORE_ANIMATE);
        back.setFont(font_16);
        addBtn.setFont(font_16);
        back.setText("Voltar");
        ownLbl.setFont(font_42);
        addLbl.setFont(font_40);
        clear.setStyle(R.CSS_BEFORE_ANIMATE);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
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

    @FXML
    public void search(Event event) throws SQLException {
        KeyEvent keyEvent = (KeyEvent) event;
        var search = textSearch.getText();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!validEntry(search)){
                List<Owner> byCpf = dao.findByCpf(search);
                if (byCpf.isEmpty()){
                owners = FXCollections.observableList(byCpf);
                loadColumns();
                }else {
                    owners = FXCollections.observableList(byCpf);
                    loadColumns();
                }
            }else {
                owners = FXCollections.observableList(dao.findAll());
                loadColumns();
                if (search.isBlank()) return;
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("DIGITE O CPF COMPLETO E SOMENTE OS NUMEROS");
                alert.setTitle("CPF INVÁLIDO");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.show();
            }
        }
    }

    @FXML
    void clear(Event event) {
        R.animateBtn(clear);
        nameInput.clear();
        cpfInput.clear();
        emailInput.clear();
        phoneInput.clear();
        stateInput.getSelectionModel().clearSelection();
        cityInput.clear();
        streetInput.clear();
        numInput.clear();
    }

    @FXML
    void add(Event event) throws SQLException {
        R.animateBtn(addBtn);
        String name = nameInput.getText();
        String cpf = cpfInput.getText();
        String email = emailInput.getText();
        String phone = phoneInput.getText();
        String state = stateInput.getValue();
        String city = cityInput.getText();
        String street = streetInput.getText();
        String num = numInput.getText();
        if (!validate(null, name, cpf, email, phone, state, street, city, num)){
            return;
        }
        var streetNum = String.format("%s,%s", street, num.trim());
        var owner = new Owner(null, cpf, name, email, phone, new Address(null,streetNum,city,state));
        dao.save(owner);

        var confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initModality(Modality.APPLICATION_MODAL);
        confirmation.initStyle(StageStyle.UNDECORATED);
        confirmation.setTitle("Proprietário Cadastrado");
        confirmation.setContentText("Proprietário cadastrado com sucesso!");
        confirmation.show();
        clear(null);
        owners = FXCollections.observableList(dao.findAll());
        loadColumns();
    }



}
