package com.arkjj.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class HBoxBuilder {
    private HBox hbox;

    public HBoxBuilder() {
        hbox = new HBox();
    }

    public HBoxBuilder alignment(Pos alignment) {
        hbox.setAlignment(alignment);
        return this;
    }

    public HBoxBuilder padding(Insets padding) {
        hbox.setPadding(padding);
        return this;
    }

    public HBoxBuilder addText(String textContent, String style) {
        Text text = new Text(textContent);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(style);
        hbox.getChildren().add(textFlow);
        return this;
    }

    public HBoxBuilder addText(String textContent, String style, Paint value) {
        Text text = new Text(textContent);
        text.setFill(value);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(style);
        hbox.getChildren().add(textFlow);
        return this;
    }

    public HBox build() {
        return hbox;
    }
}