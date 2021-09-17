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

    Button b;

    Button b_r;



    ArrayList<Integer> lottoCode_integer = new ArrayList<>();

    public String[] lottoCode = new String[6];

    public int code_counter = 0;

    ArrayList<String> listCodes = new ArrayList<>();



    @Override
    public void start(Stage stage) throws IOException {

        //initiate client object
        c = new Client(this, "127.0.0.1");

        // set title for the stage
        stage.setTitle("creating buttons");

        // create a button
        b = new Button("button");

        b_r = new Button("reset");

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

        // action event
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                resetEverything();
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

                            b.setDisable(false);

                        }
                    });
                }
            }
        };
        new Thread(task).start();


        // when button is pressed
        b.setOnAction(event);
        b_r.setOnAction(event2);

        // add button
        r.getChildren().add(b);
        r.getChildren().add(l);
        r.getChildren().add(b_r);


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

            if(code_counter < 6) {
                this.lottoCode[code_counter] = lottoCode;

                lottoCode_integer.add(Integer.valueOf(lottoCode));

                code_counter++;

                c.sendMessage("2");

                canUpdateLabel = true;

            }
        }

    }

    public void requestLottoCode(){

        if(code_counter >= 6) {

            //sends value to be store inside database
            Collections.sort(lottoCode_integer);

            String value = String.valueOf(lottoCode_integer);

            String new_value = ((value.replace("[","")).replace("]","" )).replace(" ","" );

            c.sendMessage(new String[]{"1", new_value });


            code_counter = 0;
            lottoCode = new String[6];
            lottoCode_integer.clear();
            canUpdateLabel = true;



        }

        c.sendMessage("0");

        b.setDisable(true);

    }

    public void resetEverything(){
        c.sendMessage("3");
        code_counter = 0;
        lottoCode = new String[6];
        lottoCode_integer.clear();
        canUpdateLabel = true;
    }

}