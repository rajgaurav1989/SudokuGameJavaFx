package com.raju.scenes;

import com.raju.ProjectConstants;
import com.raju.StyleConstants;
import com.raju.controllers.SudokuController;
import com.raju.services.SudokuUIService;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class InitialScene implements SceneInterface {
    private static final double SPACING = 20;

    @Override
    public Scene getScene() {
        VBox root = new VBox();

        HBox levelBox = new HBox(SPACING);
        levelBox.getChildren().addAll(getGameLabel());
        levelBox.setAlignment(Pos.CENTER);

        HBox difficultyBox = new HBox(SPACING);
        difficultyBox.getChildren().addAll(getGameLevelButtons());
        difficultyBox.setAlignment(Pos.CENTER);

        HBox quitBox = new HBox(SPACING);
        Button quitButton = new Button(ProjectConstants.QUIT_BUTTON_TEXT);
        quitButton.setOnMouseClicked(event -> SudokuController.getInstance().handleQuit());
        quitButton.setAlignment(Pos.CENTER);
        quitBox.getChildren().addAll(quitButton);
        quitBox.setAlignment(Pos.CENTER);

        VBox container = new VBox(SPACING);
        container.getChildren().addAll(levelBox, difficultyBox, quitBox);

        root.getChildren().addAll(container);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #8ac6fd");
        return new Scene(root, ProjectConstants.WINDOW_WIDTH, ProjectConstants.WINDOW_HEIGHT);
    }


    private Label getGameLabel() {
        Label label = new Label(ProjectConstants.GAME_LEVEL_TEXT);
        label.setAlignment(Pos.CENTER);
        label.setStyle(StyleConstants.GENERIC_ELEMENT_STYLE);
        return label;
    }

    private List<Node> getGameLevelButtons() {
        SudokuController sudokuController = SudokuController.getInstance();
        SudokuUIService sudokuUIService = SudokuUIService.getInstance();
        return Arrays.asList(
                sudokuUIService.getButton(ProjectConstants.EASY_LEVEL_KEY, event -> sudokuController.handleLevelSceneChange(event, ProjectConstants.EASY_LEVEL_KEY)),
                sudokuUIService.getButton(ProjectConstants.MEDIUM_LEVEL_KEY, event -> sudokuController.handleLevelSceneChange(event, ProjectConstants.MEDIUM_LEVEL_KEY)),
                sudokuUIService.getButton(ProjectConstants.HARD_LEVEL_KEY, event -> sudokuController.handleLevelSceneChange(event, ProjectConstants.HARD_LEVEL_KEY))
        );
    }

}
