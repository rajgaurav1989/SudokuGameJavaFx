package com.raju.controllers;

import com.raju.App;
import com.raju.ProjectConstants;
import com.raju.StyleConstants;
import com.raju.enums.GameLevel;
import com.raju.enums.SceneName;
import com.raju.services.SudokuUIService;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class SudokuController {
    private Stage primaryStage;
    private static SudokuController sudokuController;

    private SudokuController(Stage stage) {
        this.primaryStage = stage;
    }

    public static void setSudokuController(Stage stage) {
        if (sudokuController == null) {
            sudokuController = new SudokuController(stage);
        }
    }

    public static SudokuController getInstance() {
        return sudokuController;
    }

    public void handleLevelSceneChange(Event mouseEvent, String level) {
        App.setGameLevel(GameLevel.valueOf(level.toUpperCase().trim()));
        this.primaryStage.setScene(App.getSceneMap().get(SceneName.BOARD_SCENE));
        SudokuUIService.getInstance().generatePuzzle();
    }

    public void handleSubmit() {
        if (SudokuUIService.getInstance().isValidSudoku(true)) {
            SudokuUIService.getInstance().getErrorLabel().setText("Congratulations buddy !!");
            return;
        }
        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.initOwner(primaryStage);
        VBox dialogBox = new VBox(ProjectConstants.DIALOG_BOX_SPACING);
        dialogBox.getChildren().add(new Text("The Sudoku is still not valid"));
        Scene dialogBoxScene = new Scene(dialogBox, ProjectConstants.DIALOG_BOX_WIDTH, ProjectConstants.DIALOG_BOX_HEIGHT);
        popUpStage.setScene(dialogBoxScene);
        popUpStage.showAndWait();
    }

    public void handleQuit() {
        Stage popupStage = new Stage();
        getPopupBox(popupStage, ProjectConstants.QUIT_LABEL_TEXT, event -> {
            popupStage.close();
            primaryStage.close();
        });
    }

    public void handleRestart() {
        Stage popupStage = new Stage();
        getPopupBox(popupStage, ProjectConstants.RESTART_LABEL_TEXT, event -> {
            SudokuUIService.getInstance().clearBoard();
            this.primaryStage.setScene(App.getSceneMap().get(SceneName.INITIAL_SCENE));
            popupStage.close();
        });
    }

    public void handleReset() {
        Stage popupStage = new Stage();
        getPopupBox(popupStage, ProjectConstants.RESET_LABEL_TEXT, event -> {
            SudokuUIService.getInstance().resetBoard();
            popupStage.close();
        });
    }

    public void handleCheckSolution() {
        Stage popupStage = new Stage();
        SudokuUIService sudokuUIService = SudokuUIService.getInstance();
        getPopupBox(popupStage,ProjectConstants.CHECK_SOLUTION_TEXT, event -> {
            List<Integer> valList = sudokuUIService.getValueList();
            sudokuUIService.resetBoard();
            int[][] sudokuBoard = sudokuUIService.getSudokuGridCopy();
            sudokuUIService.solveSudoku(sudokuBoard, valList, 0, false);
            sudokuUIService.populateSudokuBoard(sudokuBoard);
            boolean validity = sudokuUIService.isValidSudoku(true);
            System.out.println("the system generated solution is "+validity);
            popupStage.close();
        });
    }

    private void getPopupBox(Stage popUpStage, String labelText, EventHandler<Event> eventEventHandler) {
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.initOwner(primaryStage);

        VBox dialogBox = new VBox(ProjectConstants.DIALOG_BOX_SPACING);

        Label label = new Label(labelText);
        label.setStyle(StyleConstants.LARGE_GENERIC_ELEMENT_STYLE);
        label.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(ProjectConstants.DIALOG_BOX_SPACING);

        Button quitButton = new Button("Yes Confirm");
        quitButton.setAlignment(Pos.CENTER);
        quitButton.setStyle(StyleConstants.GENERIC_ELEMENT_STYLE);
        Button cancelButton = new Button("Cancel");
        cancelButton.setAlignment(Pos.CENTER);
        quitButton.setOnMouseClicked(eventEventHandler);
        cancelButton.setOnMouseClicked(event -> popUpStage.close());

        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(quitButton, cancelButton);

        dialogBox.setAlignment(Pos.CENTER);
        dialogBox.getChildren().addAll(label, buttonBox);

        Scene dialogBoxScene = new Scene(dialogBox, ProjectConstants.DIALOG_BOX_WIDTH, ProjectConstants.DIALOG_BOX_HEIGHT);
        popUpStage.setScene(dialogBoxScene);
        popUpStage.showAndWait();
    }

}
