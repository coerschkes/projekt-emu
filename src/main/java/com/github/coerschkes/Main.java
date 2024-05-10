package com.github.coerschkes;

import com.github.coerschkes.gui.BaseControl;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage){
		new BaseControl(primaryStage);
    }	
	
	public static void main(String... args){
		launch(args);
	}
}
