package com.arkjj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;

public class LoginWindowController implements Initializable {

    @FXML
    private Button joinBTN;

    @FXML
    private TextField usernameInput;

    @FXML
    private Label warningLabel;

    public void connectToChat(ActionEvent event) {

        // Checks if the user input in blank
        if (usernameInput.getText().isBlank()) {
            warningLabel.setText("Please enter a username");
            warningLabel.setVisible(true);
            return;
        }
        // Hide the red error text and load the FXML
        warningLabel.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatWindow.fxml"));

        try {
            buildChatWindowController(loader, event);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buildChatWindowController(FXMLLoader loader, ActionEvent event) throws IOException {
        Parent root = loader.load();
        ChatWindowController controller = loader.getController();

        String username = usernameInput.getText();
        controller.startConnection(username);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat - " + username);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add the event handler so whenever you press enter you can start the
        // connectToChat
        usernameInput.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                connectToChat(new ActionEvent(joinBTN, null));
            }
        });
    }
}
