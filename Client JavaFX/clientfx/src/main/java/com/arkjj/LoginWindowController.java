package com.arkjj;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class LoginWindowController {

    @FXML
    private Button joinBTN;

    @FXML
    private TextField usernameInput;

    @FXML
    private Label warningLabel;

    public void connectToChat(ActionEvent event) {
        if (usernameInput.getText().equals("")) {
            warningLabel.setText("Please enter a username");
            warningLabel.setVisible(true);
        } else {
            warningLabel.setVisible(false);
            String username = usernameInput.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));

            try {
                Parent root = loader.load();
                ChatWindowController controller = loader.getController();
                controller.setUsername(username);
                controller.startConnection();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene;
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Chat - " + username);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
