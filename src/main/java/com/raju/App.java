package com.raju;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;


/**
 * Hello world!
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField textField = new TextField();
        textField.setLayoutX(50);
        textField.setLayoutY(50);
        textField.getStyleClass().add("value-cell");


//        Group childGroup = new Group();
//        childGroup.setId("child-block");
//        childGroup.getChildren().add(textField);

        Group rootGroup = new Group();
        rootGroup.setId("sudoku-board");
        rootGroup.getChildren().add(textField);
        // rootGroup.getChildren().add(childGroup);

        Scene scene = new Scene(rootGroup, 600, 600);

        File cssFile = new File("src/resources/sudoku.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + cssFile.getAbsolutePath().replace("\\", "/"));

        primaryStage.setTitle("Raj's Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
