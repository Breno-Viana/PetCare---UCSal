module br.ucsal.vetclinicsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.base;


    opens br.ucsal.vetclinicsystem to javafx.fxml;
    opens br.ucsal.vetclinicsystem.controllers to javafx.fxml;
    opens br.ucsal.vetclinicsystem.model.mock to javafx.base;
    exports br.ucsal.vetclinicsystem;
    opens br.ucsal.vetclinicsystem.model.entities to javafx.base;
}