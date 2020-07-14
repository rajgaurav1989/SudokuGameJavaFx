package com.raju;

import com.raju.controllers.SudokuController;
import com.raju.enums.GameLevel;
import com.raju.enums.SceneName;
import com.raju.scenes.BoardScene;
import com.raju.scenes.InitialScene;
import com.raju.scenes.SceneInterface;
import com.raju.services.SudokuUIService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static Map<SceneName, Scene> sceneMap = new HashMap<>();
    private static GameLevel gameLevel ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SudokuController.setSudokuController(primaryStage);

        SceneInterface startScene = new InitialScene();
        SceneInterface boardScene = new BoardScene(SudokuUIService.getInstance());

        sceneMap.put(SceneName.INITIAL_SCENE, startScene.getScene());
        sceneMap.put(SceneName.BOARD_SCENE, boardScene.getScene());

        primaryStage.setTitle("Raj's Sudoku");
        primaryStage.setScene(startScene.getScene());
        primaryStage.show();
    }

    public static Map<SceneName, Scene> getSceneMap() {
        return sceneMap;
    }

    public static void setGameLevel(GameLevel level) {
        gameLevel = level;
    }

    public static GameLevel getGameLevel() {
        return gameLevel;
    }

}
