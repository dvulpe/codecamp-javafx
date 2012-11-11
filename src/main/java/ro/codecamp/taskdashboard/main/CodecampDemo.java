package ro.codecamp.taskdashboard.main;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CodecampDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Label label = LabelBuilder.create()
                .text("Hello JavaFX!")
                .font(Font.font("Arial", 55))
                .build();

        Scene scene = new Scene(label, 500, 300);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        TranslateTransition translateTransition = TranslateTransitionBuilder.create()
                .node(label)
                .fromX(0)
                .toX(100)
                .duration(Duration.millis(500))
                .interpolator(Interpolator.EASE_OUT)
                .autoReverse(true)
                .cycleCount(2)
                .build();

        FadeTransition fadeTransition = FadeTransitionBuilder.create()
                .node(label)
                .fromValue(1)
                .toValue(0.4)
                .duration(Duration.millis(500))
                .cycleCount(2)
                .autoReverse(true)
                .build();

        Transition transition = ParallelTransitionBuilder
                .create()
                .children(translateTransition, fadeTransition)
                .build();
        transition.play();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
