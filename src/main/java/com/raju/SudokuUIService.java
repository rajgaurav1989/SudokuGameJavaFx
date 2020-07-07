package com.raju;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.Map;

public class SudokuUIService {

    private static final double BLOCK_SIZE = 180 ;
    private static final double CELL_SIZE = 60 ;
    private static final double INITIAL_POSITION_X = 50 ;
    private static final double INITIAL_POSITION_Y = 70 ;
    private static final int NUM_BLOCK_ROWS = 3 ;
    private static final int NUM_BLOCK_COLS = 3 ;
    private static final int NUM_CELL_ROWS = 9 ;
    private static final int NUM_CELL_COLS = 9 ;
    private static final String CELL_CLASS_NAME = "value-cell" ;
    private static final double LABEL_SIZE = 10 ;
    private static final String CELL_KEY_PREFIX = "cell_" ;
    private static final String KEY_SPLITTER = "_";

    private Map<String,TextField> textFieldMap = new HashMap<>();

    public TextField[] getSudokuCells() {
        TextField cellTextFields[] = new TextField[NUM_CELL_ROWS * NUM_CELL_COLS] ;

        int counter = 0 ;
        for (int rowIndex = 0 ; rowIndex < NUM_CELL_ROWS ; rowIndex++){
            for (int colIndex = 0 ; colIndex < NUM_CELL_COLS ; colIndex++)  {
                TextField textField = new TextField() ;
                textField.setDisable(false);
                textField.getStyleClass().add(CELL_CLASS_NAME);
                textField.setLayoutX(colIndex * CELL_SIZE + INITIAL_POSITION_X);
                textField.setLayoutY(rowIndex * CELL_SIZE + INITIAL_POSITION_Y);
                String keyId = CELL_KEY_PREFIX+rowIndex+KEY_SPLITTER+colIndex ;
                textField.setId(keyId);
                textFieldMap.put(keyId,textField);
                //textField.appendText((rowIndex+1)+"_"+(colIndex+1));
                cellTextFields[counter++] = textField ;
            }
        }

        return cellTextFields ;
    }

    public Line[] getBlockLines(boolean isHorizontal) {
        Line blockLines[] = new Line[NUM_BLOCK_ROWS +  1] ;
        for (int index = 0 ; index < NUM_BLOCK_ROWS + 1 ; index++) {
            double lineIndex = isHorizontal ? index * BLOCK_SIZE + INITIAL_POSITION_Y : index * BLOCK_SIZE + INITIAL_POSITION_X ;
            Line line = isHorizontal ? new Line(INITIAL_POSITION_X,lineIndex,INITIAL_POSITION_X + NUM_BLOCK_COLS * BLOCK_SIZE,lineIndex) :
                        new Line(lineIndex,INITIAL_POSITION_Y,lineIndex,INITIAL_POSITION_Y+NUM_BLOCK_ROWS * BLOCK_SIZE) ;
            line.setStroke(Paint.valueOf("black"));
            line.setStrokeWidth(8);
            blockLines[index] = line ;
        }
        return blockLines ;
    }

    public Label[] getLabels(boolean isHorizontal) {
        Label labels[] = new Label[isHorizontal ? NUM_CELL_COLS : NUM_CELL_ROWS] ;
        int stopIndex = labels.length ;
        for (int index = 0 ; index < stopIndex ; index++) {
            Label label = new Label(String.valueOf(index+1));
            label.setAlignment(Pos.CENTER);
            String className = isHorizontal ? "label-x-axis" : "label-y-axis" ;
            label.getStyleClass().add(className);
            if (isHorizontal) {
                label.setLayoutX(INITIAL_POSITION_X+index*CELL_SIZE);
                label.setLayoutY(INITIAL_POSITION_Y - LABEL_SIZE*3);
            }
            else {
                label.setLayoutX(INITIAL_POSITION_X - LABEL_SIZE*2);
                label.setLayoutY(INITIAL_POSITION_Y +index*CELL_SIZE);
            }
            labels[index] = label ;
        }
        return labels ;
    }

    public void initializeSudokuBoard() {

    }

}
