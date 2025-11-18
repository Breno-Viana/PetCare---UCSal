module br.ucsal.vetclinicsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens br.ucsal.vetclinicsystem to javafx.fxml;
    opens br.ucsal.vetclinicsystem.controllers to javafx.fxml;
    opens br.ucsal.vetclinicsystem.model.mock to javafx.base;
    exports br.ucsal.vetclinicsystem;
}