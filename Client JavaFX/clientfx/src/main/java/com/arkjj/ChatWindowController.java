package com.arkjj;

import java.net.URL;
import java.util.ResourceBundle;

import com.arkjj.Client.WebSocketClientImp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChatWindowController implements Initializable {

    @FXML
    private Button sendBTN;

    @FXML
    private TextField textArea;

    private WebSocketClientImp client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new WebSocketClientImp();
    }

    public void send() {
        client.sendMessage(textArea.getText());
    }

}