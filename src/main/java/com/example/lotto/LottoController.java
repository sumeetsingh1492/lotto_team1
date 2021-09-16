package com.example.lotto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LottoController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onResetButtonClick(ActionEvent actionEvent) {
    }

    public void onGenerateButtonClick(ActionEvent actionEvent) {
    }
}