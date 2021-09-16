package com.example.lotto;

import com.example.lotto.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private Client c;

    Label l;

    Boolean canUpdateLabel = false;

    public String lottoCode = "Button not selected";



    @Override
    public void start(Stage stage) throws IOException {

        //initiate client object
        c = new Client(this, "127.0.0.1");

        // set title for the stage
        stage.setTitle("creating buttons");

        // create a button
        Button b = new Button("button");

        // create a stack pane
        TilePane r = new TilePane();

        // create a label
        l = new Label(lottoCode);

        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                requestLottoCode();

            }
        };

        // background task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Thread.sleep(30);
                    Platform.runLater(() -> {
                        if(canUpdateLabel) {

                            l.setText(lottoCode);

                            canUpdateLabel = false;

                        }
                    });
                }
            }
        };
        new Thread(task).start();


        // when button is pressed
        b.setOnAction(event);

        // add button
        r.getChildren().add(b);
        r.getChildren().add(l);

        // create a scene
        Scene sc = new Scene(r, 200, 200);

        // set the scene
        stage.setScene(sc);

        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

    public String getLottoCode() {
        return lottoCode;
    }

    public void setLottoCode(String lottoCode) {

        this.lottoCode = lottoCode;

        canUpdateLabel = true;

    }

    public void requestLottoCode(){

        c.sendMessage("0");

    }

}