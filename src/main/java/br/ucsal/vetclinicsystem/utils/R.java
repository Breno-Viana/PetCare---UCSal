package br.ucsal.vetclinicsystem.utils;

import br.ucsal.vetclinicsystem.AppMain;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class R {


    public static final Image logo;
    public static final URL main_view;
    public static final URL principal_font;
    public static final URL owner_view;

    public static final String CSS_BEFORE_ANIMATE =
            "-fx-background-color: #6eafcc; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-cursor: hand; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-background-radius: 5px;";
    public static final String CSS_CHOICEBOX =
            " -fx-background-color: #f7faf9;"
                    + " -fx-border-color: #b1c6c4;"
                    + " -fx-border-radius: 10px;"
                    + " -fx-background-radius: 10px;"
                    + " -fx-font-size: 15px;"
                    + " -fx-padding: 6px 12px;"
                    + " -fx-text-fill: #2d2d2d;" +
                    "-fx-min-width:200px;";


    static {
        principal_font = Objects.requireNonNull(AppMain.class.getResource("fonts/principal_bold.otf"));
        logo = new Image((Objects.requireNonNull(AppMain.class.getResource("img/petcare-logo.png")).toExternalForm()));
        main_view = Objects.requireNonNull(AppMain.class.getResource("views/main.fxml"));
        owner_view = Objects.requireNonNull(AppMain.class.getResource("views/add_owner_view.fxml"));
    }

    public static void animateBtn(Button btn) {
        btn.setStyle("-fx-background-color: #5d899c;" +
                "-fx-text-fill: #2fadbd");
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(90), btn);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(0.9);
        scaleTransition.setToY(0.9);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
        scaleTransition.setOnFinished(e -> {
            btn.setStyle(CSS_BEFORE_ANIMATE);
        });
    }
}
