package com.example.assignment4task1;

import java.util.HashSet;
import java.util.Set;

public class SudokuGrid {
    private Integer[][] sudokuGrid;
    final private int SIZE = 9;
    public SudokuGrid(){
        sudokuGrid = new Integer[SIZE][SIZE];
    }
    public void setBoxValue(int row, int col, int value ){
        sudokuGrid[row][col] = value;
    }
    public int getValue(int row, int col){
        return sudokuGrid[row][col];
    }
    public void clearBoxValue(int row, int col){
        sudokuGrid[row][col]  = null;
    }
    public boolean isValid(int row, int col, int value){
        return isRowValid(row, value) && isColValid(col, value) && isBlockValid(row, col, value);
    }

    private boolean isBlockValid(int row, int col, int value) {
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (sudokuGrid[r][c] != null && sudokuGrid[r][c] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isColValid(int col, int value) {
        for(int row = 0; row < SIZE; row++){
            if(sudokuGrid[row][col] != null && sudokuGrid[row][col] == value)
                return false;
        }
        return true;
    }

    private boolean isRowValid(int row, int value) {
        for (int col = 0; col < SIZE; col++){
            if(sudokuGrid[row][col] != null && sudokuGrid[row][col] ==value)
                return  false;
        }
        return true;
    }
    public Set<String> getConflicts(int row, int col, int value) {
        Set<String> conflicts = new HashSet<>();
        for (int c = 0; c < SIZE; c++) {
            if (sudokuGrid[row][c] != null && sudokuGrid[row][c] == value && c != col) {
                conflicts.add("row");
                break;
            }
        }
        for (int r = 0; r < SIZE; r++) {
            if (sudokuGrid[r][col] != null && sudokuGrid[r][col] == value && r != row) {
                conflicts.add("column");
                break;
            }
        }
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (!(r == row && c == col) && sudokuGrid[r][c] != null && sudokuGrid[r][c] == value) {
                    conflicts.add("block");
                    break;
                }
            }
        }
        return conflicts;
    }
}
