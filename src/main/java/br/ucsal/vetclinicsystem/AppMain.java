package br.ucsal.vetclinicsystem;

import br.ucsal.vetclinicsystem.utils.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


//--------------------------
// CLASSE QUE INICIA O SISTEMA
//--------------------------
public class AppMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(R.main_view);

        var scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("PetCare");
        stage.setMinWidth(1500);
        stage.setMinHeight(900);
        stage.centerOnScreen();
        stage.show();
    }


}
