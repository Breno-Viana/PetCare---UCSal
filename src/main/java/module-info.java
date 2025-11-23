module br.ucsal.vetclinicsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;

    opens br.ucsal.vetclinicsystem to javafx.fxml;
    opens br.ucsal.vetclinicsystem.controllers to javafx.fxml;
    opens br.ucsal.vetclinicsystem.controllers.common to javafx.fxml;
    exports br.ucsal.vetclinicsystem;
    opens br.ucsal.vetclinicsystem.controllers.modal.veterinarians to javafx.fxml;
    opens br.ucsal.vetclinicsystem.model.entities to javafx.base;
    opens br.ucsal.vetclinicsystem.controllers.modal.consultations to javafx.fxml;
    opens br.ucsal.vetclinicsystem.controllers.modal.owners to javafx.fxml;
}