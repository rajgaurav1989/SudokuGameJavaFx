package com.raju;

import java.util.*;

/**
 * this file is not related to the sudoku game project , the purpose of this file is to test the
 * algorithms and logic before coding it in the sudoku game .
 * Please uncomment the code in case you are interested in trying out the algorithms used in the game.
 */


public class AlgoTest {
    private static final int SUDOKU_SIZE = 9;
    private static final int SUB_GRID_SIZE = (int) Math.sqrt(SUDOKU_SIZE);
    private static final int NUM_ROUNDS = 6;
    private static final int MINIMUM_NUMBER_OF_NON_EMPTY_CELLS = 20;
    private static final int BOUND = 10;
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 7;

    public static void main(String[] args) {
        int[][] sudokuGrid = getBlankSudokuBoard();
        initializeSudokuSolution(sudokuGrid, getValueList());
        try {
            checkEmptyInSolvedSudoku(sudokuGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        printSudokuBoard(sudokuGrid);
        boolean validity = isValidSudoku(sudokuGrid, true);
        System.out.println("check solution validity " + validity);

//        int sudokuGrid1[][] = {{0, 0, 0, 0}, {0, 0, 0, 0}, {3, 2, 4, 1}, {4, 1, 3, 2}};
//        int solutionSize = solveSudoku(sudokuGrid1, getValueList(), 0, true);
//        System.out.println("solutionSize = " + solutionSize);

        generateSudokuPuzzle(sudokuGrid, getValueList());
        System.out.println("\n\n\n\n");
        printSudokuBoard(sudokuGrid);
        boolean validity1 = isValidSudoku(sudokuGrid, false);
        System.out.println("line 31 " + validity1);
        int solutionSize = solveSudoku(sudokuGrid, getValueList(), 0, false);
        System.out.println("\nSolution\n");
        printSudokuBoard(sudokuGrid);
        try {
            checkEmptyInSolvedSudoku(sudokuGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getValueList() {
        List<Integer> valueList = new ArrayList<>();
        for (int value = 1; value <= SUDOKU_SIZE; value++) {
            valueList.add(value);
        }
        return valueList;
    }

    private static int[][] getBlankSudokuBoard() {
        int[][] sudokuGrid = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int rowIndex = 0; rowIndex < SUDOKU_SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SUDOKU_SIZE; colIndex++) {
                sudokuGrid[rowIndex][colIndex] = 0;
            }
        }
        return sudokuGrid;
    }

    private static boolean initializeSudokuSolution(int[][] sudokuGrid, List<Integer> valueList) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                if (sudokuGrid[row][col] != 0) {
                    continue;
                }
                Collections.shuffle(valueList);
                for (Integer valElem : valueList) {
                    if (!isPresentInRowOrCol(sudokuGrid, row, col, valElem, true) &&
                            !isPresentInRowOrCol(sudokuGrid, row, col, valElem, false) &&
                            !isPresentInSubGrid(sudokuGrid, row, col, valElem)) {
                        sudokuGrid[row][col] = valElem;
                        boolean isValid = initializeSudokuSolution(sudokuGrid, valueList);
                        if (isValid) {
                            return isValid;
                        }
                    }
                }
                sudokuGrid[row][col] = 0;
                return false;
            }
        }
        return true;
    }

    private static boolean isPresentInRowOrCol(int[][] sudokuGrid, int row, int col, int cellValue, boolean isRow) {
        if (cellValue == 0) {
            return false;
        }
        for (int index = 0; index < SUDOKU_SIZE; index++) {
            if ((isRow && index == col) || (!isRow && index == row)) {
                continue;
            }
            int value = isRow ? sudokuGrid[row][index] : sudokuGrid[index][col];
            if (value == cellValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPresentInSubGrid(int[][] sudokuGrid, int row, int col, int value) {
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
                if (value == sudokuGrid[rowIndex][colIndex]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidSudoku(int[][] sudokuGrid, boolean isSolution) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                int cellVal = sudokuGrid[row][col];
                if ((isSolution && cellVal == 0) || (isPresentInRowOrCol(sudokuGrid, row, col, cellVal, true) ||
                        isPresentInRowOrCol(sudokuGrid, row, col, cellVal, false) ||
                        isPresentInSubGrid(sudokuGrid, row, col, cellVal))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printSudokuBoard(int[][] sudokuGrid) {
        for (int rowIndex = 0; rowIndex < SUDOKU_SIZE; rowIndex++) {
            for (int colIndex = 0; colIndex < SUDOKU_SIZE; colIndex++) {
                System.out.print(sudokuGrid[rowIndex][colIndex] + "\t");
            }
            System.out.println();
        }
    }

    private static int solveSudoku(int[][] sudokuGrid, List<Integer> valueList, int solutionCtr, boolean isMulti) {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                if (sudokuGrid[row][col] != 0) {
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

//        System.out.println("\n\n");
//        printSudokuBoard(sudokuGrid);
//        System.out.println("\n\n"+isValidSudoku(sudokuGrid,true));
//        System.out.println("\n\n");

        return solutionCtr + 1;
    }

    private static void generateSudokuPuzzle(int[][] sudokuGrid, List<Integer> valueList) {
        int rounds = NUM_ROUNDS;
        Stack<int[]> nonEmptyCells = getRandomNonEmptyCells(sudokuGrid);
        while (rounds > 0 && nonEmptyCells.size() >= MINIMUM_NUMBER_OF_NON_EMPTY_CELLS) {
            Collections.shuffle(nonEmptyCells);
            int[] nonEmptyCell = nonEmptyCells.pop();
            int[][] copyGrid = getSudokuGridCopy(sudokuGrid);
            copyGrid[nonEmptyCell[0]][nonEmptyCell[1]] = 0;
            int solutionCtr = solveSudoku(copyGrid, valueList, 0, true);
            if (solutionCtr > 1) {
                System.out.println("\nmultiple solution : " + solutionCtr + "\n");
                rounds--;
            } else {
                sudokuGrid[nonEmptyCell[0]][nonEmptyCell[1]] = 0;
            }
        }
    }

    private static Stack<int[]> getRandomNonEmptyCells(int[][] sudokuGrid) {
        Stack<int[]> result = new Stack<>();
        Random rand = new Random();
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                if (sudokuGrid[row][col] != 0) {
                    int randNum = rand.nextInt(BOUND);
                    if (randNum >= LOWER_BOUND && randNum <= UPPER_BOUND) {
                        result.push(new int[]{row, col});
                    }
                }
            }
        }
        return result;
    }

    private static int[][] getSudokuGridCopy(int[][] sudokuGrid) {
        int[][] copyGrid = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                copyGrid[row][col] = sudokuGrid[row][col];
            }
        }
        return copyGrid;
    }

    private static boolean checkEmptyInSolvedSudoku(int[][] sudokuGrid) throws Exception {
        for (int row = 0; row < SUDOKU_SIZE; row++) {
            for (int col = 0; col < SUDOKU_SIZE; col++) {
                if (sudokuGrid[row][col] == 0) {
                    throw new Exception("Blank value in solved sudoku");
                }
            }
        }
        return false;
    }

}
