package br.ucsal.vetclinicsystem.controllers.modal.consultations;

import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class AnimalChoiceController {
    public TableView<Animal> table;

    public Button select;
    public AnchorPane pane;
    public Label choiceLbl;

    private Animal selectedAnimal;

    private List<Animal> animals;

    private ObservableList<Animal>observableAnimals;

    private ToggleGroup toggleGroup = new ToggleGroup();

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
        observableAnimals= FXCollections.observableArrayList(animals);
        choiceLbl.setText(String.format("Escolha um animal Associado à %s", animals.getFirst().getOwner()));
        loadTable();
    }

    public Animal getSelectedAnimal() {
        return selectedAnimal;
    }


    @FXML
    void initialize() {
        select.setStyle(R.CSS_BEFORE_ANIMATE);
    }

    void loadTable() {
        table.getColumns().clear();
        TableColumn<Animal, String> nameColumn = new TableColumn<>("Nome");
        TableColumn<Animal, String> species = new TableColumn<>("Espécie");


        nameColumn.setResizable(false);
        species.setResizable(false);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        species.setCellValueFactory(new PropertyValueFactory<>("species"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(species);

        loadToggle();

        table.setItems(observableAnimals);
    }

    private void loadToggle() {
        TableColumn<Animal, Void> toggle = new  TableColumn<>("Selecione");
        toggle.setResizable(false);
        toggle.setCellFactory(col -> new TableCell<>() {
            private final RadioButton radio = new RadioButton();

            {
                radio.setToggleGroup(toggleGroup);

                // evento disparado quando clica no radio
                radio.setOnAction(e -> {
                    selectedAnimal = getTableView().getItems().get(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Animal animal = getTableView().getItems().get(getIndex());
                radio.setSelected(animal == selectedAnimal);

                setGraphic(radio);
                setAlignment(Pos.CENTER);
            }
        });
        table.getColumns().add(toggle);
    }


    @FXML
    public void selectAnimal(ActionEvent actionEvent) {
        var stage = (Stage) pane.getScene().getWindow();
        if (selectedAnimal != null) {
            stage.close();
        }
    }
}
