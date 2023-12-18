package com.example.codegeneratorfx;

import com.example.codegeneratorfx.controllers.MainController;
import com.example.codegeneratorfx.supportClasses.Lottery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    Lottery lottery = new Lottery();
    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController(lottery);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("start-scene.fxml"));
        fxmlLoader.setController(mainController);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome in Code Generator!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}