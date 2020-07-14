package com.raju.scenes;

import com.raju.ProjectConstants;
import com.raju.services.SudokuUIService;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.File;

public class BoardScene implements SceneInterface {
    private SudokuUIService sudokuUIService;

    public BoardScene(SudokuUIService sudokuUIService) {
        this.sudokuUIService = sudokuUIService;
    }

    @Override
    public Scene getScene() {
        Group rootGroup = new Group();
        rootGroup.getChildren().addAll(sudokuUIService.getSudokuCells());
        rootGroup.getChildren().addAll(sudokuUIService.getBlockLines(true));
        rootGroup.getChildren().addAll(sudokuUIService.getBlockLines(false));
        rootGroup.getChildren().addAll(sudokuUIService.getLabels(true));
        rootGroup.getChildren().addAll(sudokuUIService.getLabels(false));
        rootGroup.getChildren().addAll(sudokuUIService.getSideBar());
        rootGroup.getChildren().addAll(sudokuUIService.getErrorLabel());

        Scene scene = new Scene(rootGroup, ProjectConstants.WINDOW_WIDTH, ProjectConstants.WINDOW_HEIGHT);

        String css = BoardScene.class.getResource("/sudoku.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);

        return scene;
    }

}
