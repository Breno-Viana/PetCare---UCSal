package br.ucsal.vetclinicsystem.controllers;

import br.ucsal.vetclinicsystem.controllers.common.AnimalCommonAttributes;
import br.ucsal.vetclinicsystem.controllers.modal.animal.AnimalEditViewController;
import br.ucsal.vetclinicsystem.controllers.modal.owners.OwnerEditController;
import br.ucsal.vetclinicsystem.model.dao.AnimalDAO;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Owner;
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
import java.time.LocalDate;
import java.util.List;

public class AnimalViewController extends AnimalCommonAttributes {
    public Button back;
    public Label aniLbl;
    public TableView<Animal> table;
    public TextField textSearch;
    public Label addLbl;


    public Button addBtn;
    public Button clear;

    private final String[] consumedValues = {
            "id",
            "name",
            "breed",
            "species",
            "weight",
            "owner",
            "birthdate"
    };

    private final String[] labels = {
            "id",
            "nome",
            "raça",
            "espécie",
            "peso(Kg)",
            "dono",
            "nascimento"
    };

    private ObservableList<Animal> listaAnimals = FXCollections.observableList(dao.findAll());

    @FXML
    void initialize() throws IOException {
        setFontsAndStyles();
        loadColumns();
    }

    private void loadColumns() {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.getColumns().clear();

        for (int i = 0; i < labels.length; i++) {
            TableColumn<Animal, String> column = new TableColumn<>(labels[i]);
            column.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
            column.setStyle("-fx-alignment: CENTER;");
            column.setCellValueFactory(new PropertyValueFactory<>(consumedValues[i]));
            table.getColumns().add(column);
        }

        addEditButton();

        table.setItems(listaAnimals);
    }

    private void addEditButton() {
        TableColumn<Animal, Void> column = new TableColumn<>("Editar");
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
                        var controller = new AnimalEditViewController().open(id);
                        if(controller.isClosed()){
                            reset();
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
        var font_42 = Font.loadFont(R.principal_font.openStream(), 42);
        var font_40 = Font.loadFont(R.principal_font.openStream(), 40);
        aniLbl.setFont(font_42);
        back.setStyle(R.CSS_BEFORE_ANIMATE);
        addBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        addLbl.setFont(font_40);
        clear.setStyle(R.CSS_BEFORE_ANIMATE);
    }

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

    private void reset() {
        listaAnimals = FXCollections.observableList(dao.findAll());
        table.setItems(listaAnimals);
    }

    public void add(ActionEvent actionEvent) throws SQLException {
        R.animateBtn(addBtn);
        String weight = weightInput.getText();
        String breed = breedInput.getText();
        String species = speciesInput.getText();
        LocalDate birth = datePicker.getValue();
        String name = nameInput.getText();
        String owner = ownInput.getText();
        if (!validate(name, species, breed, weight, owner, birth)) {
            return;
        }
        List<Owner> byCpf = ownerDAO.findByCpf(owner);
        float v = Float.parseFloat(weight.trim().replace(",", "."));
        var animal = new Animal(null, byCpf.getFirst(), name, species, breed, birth, v);
        dao.save(animal);
        var alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText(String.format("%s de %s cadastrado", animal.getName(), animal.getOwner()));
        alert.showAndWait();
        reset();
        clear(null);
    }

    public void clear(ActionEvent actionEvent) {
        R.animateBtn(clear);
        breedInput.clear();
        speciesInput.clear();
        weightInput.clear();
        datePicker.setValue(null);
        nameInput.clear();
        ownInput.clear();

    }

    public void search(KeyEvent keyEvent) throws SQLException {
        if (keyEvent.getCode() != KeyCode.ENTER) return;

        var search = textSearch.getText().trim();


        if (search.isBlank()) {
            reset();
            return;
        }

        if (validEntry(search)) {
            reset();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("DIGITE O CPF COMPLETO E SOMENTE OS NUMEROS");
            alert.setTitle("CPF INVÁLIDO");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();


            return;
        }

        List<Animal> byOwnerCpf = dao.findByOwnerCpf(search);

        if (byOwnerCpf.isEmpty()) {
            listaAnimals = FXCollections.observableList(dao.findAll());

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("ESSE PROPRIETÁRIO NÃO POSSUI ANIMAIS");
            alert.setTitle("AVISO");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.show();
        } else {
            listaAnimals = FXCollections.observableList(byOwnerCpf);
        }

        table.setItems(listaAnimals);
    }





}
