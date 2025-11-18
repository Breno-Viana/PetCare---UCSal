package br.ucsal.vetclinicsystem.utils;

import br.ucsal.vetclinicsystem.AppMain;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class R {


    public static final Image logo;
    public static final URL main_view;

    public static final InputStream principal_font;

    static {
        principal_font = Objects.requireNonNull(AppMain.class.getResourceAsStream("fonts/principal_bold.otf"));
        logo = new Image((Objects.requireNonNull(AppMain.class.getResource("img/petcare-logo.png")).toExternalForm()));
        main_view = Objects.requireNonNull(AppMain.class.getResource("views/main.fxml"));
    }
}
