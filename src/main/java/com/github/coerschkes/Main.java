package com.github.coerschkes;

import com.github.coerschkes.gui.BaseControl;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		new BaseControl(primaryStage);
    }	
	
	public static void main(String... args){
		launch(args);
	}
}
