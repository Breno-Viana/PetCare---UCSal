package br.ucsal.vetclinicsystem;

import br.ucsal.vetclinicsystem.utils.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class AppMain extends Application {

    @Override
    public void start(Stage principal_stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(R.main_view);

        var scene = new Scene(loader.load());
        principal_stage.setScene(scene);
        principal_stage.setTitle("PetCare");
        principal_stage.setMinWidth(1500);
        principal_stage.setMinHeight(900);
        principal_stage.setMaximized(true);
        principal_stage.show();
    }


}
