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
        SudokuUIService sudokuUIService = new SudokuUIService() ;

        Group rootGroup = new Group();
        rootGroup.getChildren().addAll(sudokuUIService.getSudokuCells());
        rootGroup.getChildren().addAll(sudokuUIService.getBlockLines(true));
        rootGroup.getChildren().addAll(sudokuUIService.getBlockLines(false));
        rootGroup.getChildren().addAll(sudokuUIService.getLabels(true));
        rootGroup.getChildren().addAll(sudokuUIService.getLabels(false));

        Scene scene = new Scene(rootGroup, ProjectConstants.WINDOW_WIDTH, ProjectConstants.WINDOW_HEIGHT);

        File cssFile = new File("src/resources/sudoku.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + cssFile.getAbsolutePath().replace("\\", "/"));

        primaryStage.setTitle("Raj's Sudoku");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
