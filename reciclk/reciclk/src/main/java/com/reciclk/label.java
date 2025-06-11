package com.reciclk;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CustomLabel extends Label {
    public CustomLabel() {
        super();
        applyDefaultStyle();
    }

    public CustomLabel(String text) {
        super(text);
        applyDefaultStyle();
    }

    private void applyDefaultStyle() {
        this.setFont(Font.font("Arial", 14));
        this.setTextFill(Color.web("#2c3e50"));
    }

    public void setErrorStyle() {
        this.setStyle("-fx-text-fill: #e74c3c;");
    }

    public void setSuccessStyle() {
        this.setStyle("-fx-text-fill: #27ae60;");
    }
}