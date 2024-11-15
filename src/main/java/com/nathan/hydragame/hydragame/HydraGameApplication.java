package com.nathan.hydragame.hydragame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class HydraGameApplication extends Application {

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HydraGameApplication.class.getResource("hydra-view.fxml"));
        Scene scene = new Scene(loader.load(), 800,850);

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // set the stage's title and icon
        stage.setTitle("Hydra Game");
        stage.getIcons().add(new Image(getClass().getResource("HydraIcon.png").toExternalForm()));


        // set the scene on the stage and show it
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String args[])
    {
        launch(args);
    }
}
