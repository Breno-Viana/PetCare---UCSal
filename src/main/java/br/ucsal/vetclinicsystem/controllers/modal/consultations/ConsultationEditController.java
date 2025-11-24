package br.ucsal.vetclinicsystem.controllers.modal.consultations;

import br.ucsal.vetclinicsystem.controllers.common.ConsulteCommonAttributes;
import br.ucsal.vetclinicsystem.model.dao.ConsultationDAO;
import br.ucsal.vetclinicsystem.model.dao.OwnerDAO;
import br.ucsal.vetclinicsystem.model.entities.Animal;
import br.ucsal.vetclinicsystem.model.entities.Consultation;
import br.ucsal.vetclinicsystem.model.entities.Owner;
import br.ucsal.vetclinicsystem.model.entities.Veterinarian;
import br.ucsal.vetclinicsystem.utils.R;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ConsultationEditController extends ConsulteCommonAttributes {


    public Button deleteBtn;
    public Label diagLbl;
    public Label valueLbl;
    public Label dateLbl;
    public Label aniLbl;
    public Label vetLbl;
    public Label hourLbl;
    public Button updateBtn;
    private long id;
    private boolean closed = false;

    public boolean isClosed() {
        return closed;
    }

    private final ConsultationDAO dao = new ConsultationDAO();

    private void setId(long id) {
        this.id = id;
        search();
    }

    @FXML
    private Label editLabel;

    @FXML
    private void initialize() throws IOException {
        setStylesAndFonts();
    }

    private void setStylesAndFonts() throws IOException {
        Font font = Font.loadFont(R.principal_font.openStream(), 42);
        Font font_20 = Font.loadFont(R.principal_font.openStream(), 20);
        editLabel.setFont(font);
        deleteBtn.setFont(font_20);
        deleteBtn.getStyleClass().add("delete-btn");
        deleteBtn.setStyle(R.CSS_DELETE_BTN);
        vetChoice.setStyle(R.CSS_CHOICEBOX);
        hour.setStyle(R.CSS_CHOICEBOX);
        updateBtn.setStyle(R.CSS_BEFORE_ANIMATE);
        updateBtn.setFont(font_20);
       searchOwner.setStyle(R.CSS_BEFORE_ANIMATE);
    }


    private void search() {
        initChoiceBox();
        Consultation consultToEdit = dao.findById(id);
        setAnimal(consultToEdit.getAnimalO());
        vetChoice.setValue(consultToEdit.getVetO());

        vetChoice.setConverter(new StringConverter<Veterinarian>() {

            @Override
            public String toString(Veterinarian vet) {
                return vet == null ? "Selecione um veterinário" : String.format("%s | %s", vet.getName(), vet.getSpeciality());
            }

            @Override
            public Veterinarian fromString(String s) {
                return null;
            }
        });
        var localTime = consultToEdit.getDateTime().toLocalTime();
        var localDate = consultToEdit.getDateTime().toLocalDate();
        datePick.setValue(localDate);
        hour.setValue(localTime.toString());
        diagText.setText(consultToEdit.getDiagnosis());
        valueText.setText(consultToEdit.getValue().toString());
        ownerCpf.setText(String.format("%s | %s", animal.getName(), animal.getSpecies()));

    }

    public ConsultationEditController openEdit(long id) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(R.consult_edit_view);
        Parent root = fxmlLoader.load();
        ConsultationEditController editController = fxmlLoader.getController();
        editController.setId(id);
        stage.setTitle("Editar Consulta");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 990, 640));
        stage.initModality(Modality.APPLICATION_MODAL);
        root.requestFocus();

        stage.showAndWait();
        return editController;
    }

    @FXML
    void close(Event event) {
        KeyEvent keyEvent = (KeyEvent) event;
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            var stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void deleteConsulte(Event event) {
        R.animateDeleteBtn(deleteBtn);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("DESEJA MESMO DELETAR ESSA CONSULTA?");
        ButtonType yes = new ButtonType("SIM");
        ButtonType not = new ButtonType("NÃO");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(not, yes);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("APAGAR CONSULTA");
        alert.initOwner(pane.getScene().getWindow());
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.initStyle(StageStyle.UNDECORATED);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == yes) {
            dao.deleteById(id);
            Alert confirm = new Alert(Alert.AlertType.NONE);
            confirm.setTitle("Deletado");
            confirm.setContentText("consulta de id " + id + " deletada");
            confirm.getButtonTypes().clear();
            confirm.initModality(Modality.APPLICATION_MODAL);
            confirm.getButtonTypes().add(ButtonType.OK);
            confirm.initStyle(StageStyle.UNDECORATED);
            confirm.initOwner(pane.getScene().getWindow());
            Optional<ButtonType> buttonType1 = confirm.showAndWait();
            if (buttonType1.isPresent() && buttonType1.get() == ButtonType.OK) {
                closed = true;
                confirm.close();
                var stage = (Stage) pane.getScene().getWindow();
                stage.close();
            }
        }
    }

    private void initChoiceBox() {
        vetChoice.setItems(veterinarians);
        hour.setItems(FXCollections.observableList(List.of(hours)));
    }

    @FXML
    public void updateConsultation(ActionEvent actionEvent) throws SQLException {
        R.animateBtn(updateBtn);
        LocalTime time = LocalTime.parse(hour.getValue());
        LocalDate date = datePick.getValue();
        if (!validate(animal,vetChoice.getValue(),valueText.getText(),diagText.getText(), datePick.getValue(),hour.getValue())){
            return;
        }
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        String replace = valueText.getText().replace(",", ".");
        Consultation consultation = new Consultation(id,animal, vetChoice.getValue(), dateTime, diagText.getText(), new BigDecimal(replace));

        Alert updating = new Alert(Alert.AlertType.CONFIRMATION);
        updating.setTitle("ATUALIZAR CONSULTA");
        updating.setResizable(false);
        updating.setContentText("ATUALIZAR CONSULTA "+consultation.getId()+"?");
        updating.getButtonTypes().clear();
        updating.initStyle(StageStyle.UNDECORATED);
        ButtonType yes = new ButtonType("Sim");
        ButtonType not = new ButtonType("Não");
        updating.getButtonTypes().addAll(not, yes);
        Optional<ButtonType> buttonType = updating.showAndWait();
        if (buttonType.isPresent() && buttonType.get() == yes){
            var stage = (Stage)pane.getScene().getWindow();
            dao.update(consultation);
            stage.close();
            closed = true;
        }

    }
    @FXML
    protected void searchOwner(Event event) throws SQLException, IOException {
        R.animateBtn(searchOwner);
        String ownerCpfText = ownerCpf.getText();
        if (!validate(ownerCpfText)){
            warning("DIGITE UM CPF VALIDO E CADASTRADO");
            return;
        }

        List<Animal> animals = animalDAO.findByOwnerCpf(ownerCpfText);
        openAnimalChoice(animals);
    }

    private void openAnimalChoice(List<Animal> animals) throws IOException {
        FXMLLoader loader = new FXMLLoader(R.animal_choice);
        Parent parent = loader.load();

        AnimalChoiceController controller = loader.getController();
        controller.setAnimals(animals);

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initOwner(pane.getScene().getWindow());
        stage.showAndWait();

        Animal selected = controller.getSelectedAnimal();
        ownerCpf.setText(String.format("%s | %s", selected.getName(), selected.getSpecies()));
        setAnimal(selected);
    }


    boolean validate(String cpf) throws SQLException {
        if(cpf == null || cpf.isEmpty() || validEntry(cpf)){
            return false;
        }
        OwnerDAO ownerDAO = new OwnerDAO();
        List<Owner> byCpf = ownerDAO.findByCpf(cpf);
        return !byCpf.isEmpty();
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
