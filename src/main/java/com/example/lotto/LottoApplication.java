package com.example.lotto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LottoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LottoApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Button generateButton = (Button) scene.lookup("#generateButton");
        Button resetButton = (Button) scene.lookup("#resetButton");

        GridPane grid = (GridPane) scene.lookup("#grid");

//      Create output labels
        Label first = new Label("Your");
        Label second = new Label("lotto");
        Label third = new Label("numbers");
        Label fourth = new Label("will");
        Label fifth = new Label("appear");
        Label sixth = new Label("here");

        // Define padding
        first.setPadding(new Insets(10, 10, 10, 10));
        second.setPadding(new Insets(10, 10, 10, 10));
        third.setPadding(new Insets(10, 10, 10, 10));
        fourth.setPadding(new Insets(10, 10, 10, 10));
        fifth.setPadding(new Insets(10, 10, 10, 10));
        sixth.setPadding(new Insets(10, 10, 10, 10));

        generateButton.setPadding(new Insets(5, 5, 5, 5));
        resetButton.setPadding(new Insets(5, 5, 5, 5));

        // Add to grid
        grid.add(first, 0, 2, 1, 1);
        grid.add(second, 1, 2, 1, 1);
        grid.add(third, 2, 2, 1, 1);
        grid.add(fourth, 3, 2, 1, 1);
        grid.add(fifth, 4, 2, 1, 1);
        grid.add(sixth, 5, 2, 1, 1);

    }

    public static void main(String[] args) {
        launch();
    }
}