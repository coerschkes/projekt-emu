package com.github.coerschkes.gui;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class NumericTextFormatter {
    private static final UnaryOperator<TextFormatter.Change> filter = change -> {
        String text = change.getText();

        if (text.matches("[0-9]*")) {
            return change;
        }

        return null;
    };

    public static TextFormatter<String> create() {
        return new TextFormatter<>(filter);
    }
}
