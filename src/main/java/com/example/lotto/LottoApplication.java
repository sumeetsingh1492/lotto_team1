package com.example.lotto;

import com.example.lotto.client.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    ListView<Text> listWrapper = new ListView<>();

    String[] results;


    @Override
    public void start(Stage stage) throws IOException {
        // create a stack pane
        VBox gridPane = new VBox(12);

        // create a scene
        Scene sc = new Scene(gridPane, 350, 450);

        //sc.getStylesheets().add("com/example/lotto/styles.css");


        //initiate client object
        c = new Client(this, "127.0.0.1");

        // set title for the stage
        stage.setTitle("Lotto Code For U");

        HBox wrapper_btn = new HBox(12);

        // create a button
        b = new Button("Play");
        b.setMinSize(80, 35);
        b.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        b_r = new Button("Reset");
        b_r.setMinSize(80, 35);
        b_r.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        wrapper_btn.getChildren().addAll(b, b_r);

        wrapper_btn.setAlignment(Pos.CENTER);

        // create a label
        l = new Label("Call the button");

        ArrayList<Label> labels = new ArrayList<>();
        labels.add(new Label("?"));
        labels.add(new Label("?"));
        labels.add(new Label("?"));
        labels.add(new Label("?"));
        labels.add(new Label("?"));
        labels.add(new Label("?"));
        HBox wrapper = new HBox();

        for (Label label : labels) {
            label.setPadding(new Insets(10, 10, 10, 10));
            label.setStyle("-fx-border-color: black;\n" +
                    "    -fx-border-width: 4px;\n"+
                    "-fx-background-color: white;");

            label.setMaxWidth(55);
            label.setMinSize(55,55);
            label.setAlignment(Pos.CENTER);
            label.setMaxHeight(55);
            wrapper.getChildren().add(label);
        }

        wrapper.setAlignment(Pos.CENTER);


        // action event
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                requestLottoCode();

            }
        };

        // action event
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
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
                        if (canUpdateLabel) {

                            Collections.sort(lottoCode_integer);

                            int index = lottoCode_integer.size() - 1;

                            if (index >= 0) {
                                Label label = labels.get(index);
                            }
                            for (Label item : labels) {
                                item.setText("?");
                            }

                            for (int i = 0; i < lottoCode_integer.size(); i++) {
                                Label label1 = labels.get(i);
                                label1.setText(String.valueOf(lottoCode_integer.get(i)));
                            }
//                            l.setText(String.valueOf(lottoCode_integer));

                            listWrapper.getItems().clear();

                            for (String result : results) {
                                Text text = new Text(result);
                                listWrapper.getItems().add(text);
                            }
                            canUpdateLabel = false;

                            b.setDisable(false);

                        }
                    });
                }
            }
        };
        new Thread(task).start();

        //okay

        gridPane.setMargin(wrapper_btn, new Insets(10, 3, 1.5, 3));


        // when button is pressed
        b.setOnAction(event);
        b_r.setOnAction(event2);


        // add button
        gridPane.getChildren().add(wrapper_btn);
        gridPane.getChildren().add(wrapper);
        gridPane.getChildren().add(listWrapper);

        gridPane.setAlignment(Pos.CENTER);




        gridPane.setBackground(new Background(new BackgroundFill(Color.web("#eab759"), CornerRadii.EMPTY, Insets.EMPTY)));


        // set the scene
        stage.setScene(sc);


        stage.setWidth(350);
        stage.setHeight(450);

        stage.setMaxWidth(350);
        stage.setMaxHeight(450);

        stage.setMaximized(false);



        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

    public String[] getLottoCode() {
        return lottoCode;
    }

    public void setLottoCode(String lottoCode) {

        if (Arrays.asList(this.lottoCode).contains(lottoCode)) {

            requestLottoCode();

            System.out.println("A DUPLICATE YOU FOOL");

        } else {

            if (code_counter < 6) {
                this.lottoCode[code_counter] = lottoCode;

                lottoCode_integer.add(Integer.valueOf(lottoCode));

                code_counter++;

                c.sendMessage("2");

                canUpdateLabel = true;

            }
        }

    }

    public void requestLottoCode() {

        if (code_counter >= 6) {

            //sends value to be store inside database
            Collections.sort(lottoCode_integer);

            String value = String.valueOf(lottoCode_integer);

            String new_value = ((value.replace("[", "")).replace("]", "")).replace(" ", "");

            c.sendMessage(new String[]{"1", new_value});


            code_counter = 0;
            lottoCode = new String[6];
            lottoCode_integer.clear();
            canUpdateLabel = true;

        }

        c.sendMessage("0");

        b.setDisable(true);

    }

    public void resetEverything() {
        c.sendMessage("3");
        code_counter = 0;
        lottoCode = new String[6];
        lottoCode_integer.clear();
        results = new String[6];
        canUpdateLabel = true;

    }

    public void populateList(String content) {
        results = content.split("X");
    }
}