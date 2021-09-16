package com.example.lotto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class LottoController {

    @FXML
    public void onResetButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RESET");
        alert.setHeaderText("Hello");
        alert.setContentText("You've clicked reset.");
        alert.showAndWait();
    }

    @FXML
    public void onGenerateButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RESET");
        alert.setHeaderText("Hello");
        alert.setContentText("You've clicked reset.");
        alert.showAndWait();
    }
}