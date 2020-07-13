package com.raju;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SudokuUIService {

    private static final double BLOCK_SIZE = 180;
    private static final double CELL_SIZE = 60;
    private static final double INITIAL_POSITION_X = 50;
    private static final double INITIAL_POSITION_Y = 70;
    private static final int SUDOKU_SIZE = 9;
    private static final int SUB_GRID_SIZE = (int) Math.sqrt(SUDOKU_SIZE);
    private static final String CELL_CLASS_NAME = "value-cell";
    private static final double LABEL_SIZE = 10;
    private static final String KEY_SPLITTER = "_";

    private static final int NUM_ROUNDS = 6;
    private static final int MINIMUM_NUMBER_OF_NON_EMPTY_CELLS = 18;
    private static final int BOUND = 10;
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 8;

    public Map<String, TextField> boardMap = new HashMap<>();

    public TextField[] getSudokuCells() {
        TextField[] cellTextFields = new TextField[SUDOKU_SIZE * SUDOKU_SIZE];

        int counter = 0;
        for (int rowIndex = 0; rowIndex < SUDOKU_SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SUDOKU_SIZE; colIndex++) {
                TextField textField = new TextField();
                textField.setDisable(false);
                textField.getStyleClass().add(CELL_CLASS_NAME);
                textField.setLayoutX(colIndex * CELL_SIZE + INITIAL_POSITION_X);
                textField.setLayoutY(rowIndex * CELL_SIZE + INITIAL_POSITION_Y);
                String keyId = rowIndex + KEY_SPLITTER + colIndex;
                textField.setId(keyId);
                textField.setText("");
                boardMap.put(keyId, textField);
                //textField.appendText((rowIndex+1)+"_"+(colIndex+1));
                cellTextFields[counter++] = textField;
            }
        }

        return cellTextFields;
    }

    public Line[] getBlockLines(boolean isHorizontal) {
        Line[] blockLines = new Line[SUB_GRID_SIZE + 1];
        for (int index = 0; index < SUB_GRID_SIZE + 1; index++) {
            double lineIndex = isHorizontal ? index * BLOCK_SIZE + INITIAL_POSITION_Y : index * BLOCK_SIZE + INITIAL_POSITION_X;
            Line line = isHorizontal ? new Line(INITIAL_POSITION_X, lineIndex, INITIAL_POSITION_X + SUB_GRID_SIZE * BLOCK_SIZE, lineIndex) :
                    new Line(lineIndex, INITIAL_POSITION_Y, lineIndex, INITIAL_POSITION_Y + SUB_GRID_SIZE * BLOCK_SIZE);
            line.setStroke(Paint.valueOf("black"));
            line.setStrokeWidth(8);
            blockLines[index] = line;
        }
        return blockLines;
    }

    public Label[] getLabels(boolean isHorizontal) {
        Label[] labels = new Label[SUDOKU_SIZE];
        int stopIndex = labels.length;
        for (int index = 0; index < stopIndex; index++) {
            Label label = new Label(String.valueOf(index + 1));
            label.setAlignment(Pos.CENTER);
            String className = isHorizontal ? "label-x-axis" : "label-y-axis";
            label.getStyleClass().add(className);
            if (isHorizontal) {
                label.setLayoutX(INITIAL_POSITION_X + index * CELL_SIZE);
                label.setLayoutY(INITIAL_POSITION_Y - LABEL_SIZE * 3);
            } else {
                label.setLayoutX(INITIAL_POSITION_X - LABEL_SIZE * 2);
                label.setLayoutY(INITIAL_POSITION_Y + index * CELL_SIZE);
            }
            labels[index] = label;
        }
        return labels;
    }

    public List<Integer> getValueList() {
        List<Integer> valueList = new ArrayList<>();
        for (int value = 1; value <= SUDOKU_SIZE; value++) {
            valueList.add(value);
        }
        return valueList;
    }

    public boolean initializeSudokuBoard(List<Integer> valueList) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                String cellKey = row + KEY_SPLITTER + col;
                TextField textField = boardMap.get(cellKey);
                textField.setAlignment(Pos.CENTER);
                String val = textField.getText();
                if (StringUtils.isNotBlank(val)) {
                    continue;
                }
                Collections.shuffle(valueList);
                for (Integer valElem : valueList) {
                    if (!isPresentInRowOrCol(null, row, col, valElem, true) && !isPresentInRowOrCol(null, row, col, valElem, false) &&
                            !isPresentInSubGrid(null, row, col, valElem)) {
                        textField.setText(String.valueOf(valElem));
                        boolean isValid = initializeSudokuBoard(valueList);
                        if (isValid) {
                            textField.setDisable(true);
                            return isValid;
                        }
                    }
                }
                textField.setText("");
                return false;
            }
        }
        return true;
    }


    private boolean isPresentInRowOrCol(int[][] sudokuGrid, int row, int col, int cellValue, boolean isRow) {
        if (cellValue == 0) {
            return false;
        }
        for (int index = 0; index < SUDOKU_SIZE; index++) {
            if ((isRow && index == col) || (!isRow && index == row)) {
                continue;
            }
            int value;
            if (sudokuGrid == null) {
                TextField textField = isRow ? boardMap.get(row + KEY_SPLITTER + index) : boardMap.get(index + KEY_SPLITTER + col);
                value = StringUtils.isNotBlank(textField.getText()) ? Integer.parseInt(textField.getText().trim()) : 0;
            } else {
                value = isRow ? sudokuGrid[row][index] : sudokuGrid[index][col];
            }
            if (value == cellValue) {
                return true;
            }
        }
        return false;
    }

    private boolean isPresentInSubGrid(int[][] sudokuGrid, int row, int col, int value) {
        if (value == 0) {
            return false;
        }
        int subGridRowIndex = Math.floorDiv(row, SUB_GRID_SIZE);
        int subGridColIndex = Math.floorDiv(col, SUB_GRID_SIZE);
        int startRowIndex = subGridRowIndex * SUB_GRID_SIZE;
        int startColIndex = subGridColIndex * SUB_GRID_SIZE;

        for (int rowIndex = startRowIndex; rowIndex < startRowIndex + SUB_GRID_SIZE; rowIndex++) {
            for (int colIndex = startColIndex; colIndex < startColIndex + SUB_GRID_SIZE; colIndex++) {
                if (row == rowIndex && col == colIndex) {
                    continue;
                }
                int cellVal;
                if (sudokuGrid == null) {
                    String strVal = boardMap.get(rowIndex + KEY_SPLITTER + colIndex).getText();
                    cellVal = StringUtils.isNotBlank(strVal) ? Integer.parseInt(strVal.trim()) : 0;
                } else {
                    cellVal = sudokuGrid[rowIndex][colIndex];
                }
                if (value == cellVal) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidSudoku(boolean isSolution) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                TextField textField = boardMap.get(row + KEY_SPLITTER + col);
                String strVal = textField.getText();
                int cellVal = StringUtils.isNotBlank(strVal) ? Integer.parseInt(strVal.trim()) : 0;
                if ((isSolution && cellVal == 0) || (isPresentInRowOrCol(null, row, col, cellVal, true) ||
                        isPresentInRowOrCol(null, row, col, cellVal, false) ||
                        isPresentInSubGrid(null, row, col, cellVal))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int solveSudoku(int[][] sudokuGrid, List<Integer> valueList, int solutionCtr, boolean isMulti) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                int value = sudokuGrid[row][col];
                if (value != 0) {
                    continue;
                }
                int accumulator = -1;
                for (Integer valElem : valueList) {
                    if (!isPresentInRowOrCol(sudokuGrid, row, col, valElem, true) &&
                            !isPresentInRowOrCol(sudokuGrid, row, col, valElem, false) &&
                            !isPresentInSubGrid(sudokuGrid, row, col, valElem)) {
                        sudokuGrid[row][col] = valElem;
                        accumulator = solveSudoku(sudokuGrid, valueList, solutionCtr, isMulti);
                        if (accumulator > 0) {
                            solutionCtr = accumulator;
                        }
                        if (!isMulti && solutionCtr > 0) {
                            return solutionCtr;
                        }
                    }
                }
                sudokuGrid[row][col] = 0;
                return accumulator;
            }
        }
        return solutionCtr + 1;
    }

    public void generateSudokuPuzzle(List<Integer> valueList) {
        int rounds = NUM_ROUNDS;
        Stack<int[]> nonEmptyCells = getRandomNonEmptyCells();
        while (rounds > 0 && nonEmptyCells.size() >= MINIMUM_NUMBER_OF_NON_EMPTY_CELLS) {
            Collections.shuffle(nonEmptyCells);
            int[] nonEmptyCell = nonEmptyCells.pop();
            int[][] copyGrid = getSudokuGridCopy();
            copyGrid[nonEmptyCell[0]][nonEmptyCell[1]] = 0;
            int solutionCtr = solveSudoku(copyGrid, valueList, 0, true);
            if (solutionCtr > 1) {
                rounds--;
            } else {
                TextField textField = boardMap.get(nonEmptyCell[0] + KEY_SPLITTER + nonEmptyCell[1]);
                textField.setText("");
                textField.setDisable(false);
            }
        }
    }

    private Stack<int[]> getRandomNonEmptyCells() {
        Stack<int[]> result = new Stack<>();
        Random rand = new Random();
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                String strVal = boardMap.get(row + KEY_SPLITTER + col).getText();
                int cellVal = StringUtils.isNotBlank(strVal) ? Integer.parseInt(strVal.trim()) : 0;
                if (cellVal != 0) {
                    int randNum = rand.nextInt(BOUND);
                    if (randNum >= LOWER_BOUND && randNum <= UPPER_BOUND) {
                        result.push(new int[]{row, col});
                    }
                }
            }
        }
        return result;
    }

    public int[][] getSudokuGridCopy() {
        int[][] copyGrid = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                String textVal = boardMap.get(row + KEY_SPLITTER + col).getText();
                copyGrid[row][col] = StringUtils.isNotBlank(textVal) ? Integer.parseInt(textVal.trim()) : 0;
            }
        }
        return copyGrid;
    }


}
