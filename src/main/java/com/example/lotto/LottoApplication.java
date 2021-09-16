package com.example.lotto;

import com.example.lotto.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LottoApplication extends Application {

    private Client c;

    Label l;

    Boolean canUpdateLabel = false;

    ArrayList<Integer> lottoCode_integer = new ArrayList<>();

    public String[] lottoCode = new String[6];
    public int code_counter = 0;



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
        l = new Label("Call the button");

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

                            Collections.sort(lottoCode_integer);

                            l.setText(String.valueOf(lottoCode_integer));

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

    public String[] getLottoCode() {
        return lottoCode;
    }

    public void setLottoCode(String lottoCode) {

        if(Arrays.asList(this.lottoCode).contains(lottoCode)){

            requestLottoCode();

            System.out.println("A DUPLICATE YOU FOOL");

        } else {

            this.lottoCode[code_counter] = lottoCode;

            lottoCode_integer.add(Integer.valueOf(lottoCode));

            code_counter++;

            canUpdateLabel = true;
        }

    }

    public void requestLottoCode(){

        if(code_counter < 6) {
            c.sendMessage("0");
        }
        else{
            code_counter = 0;
            lottoCode = new String[6];
            lottoCode_integer.clear();
            canUpdateLabel = true;
        }

    }

}