package Sudoku;

public class SudokuGrid {

    public static final int gridSize = 9;
    public static final int miniGridSize = 3;

    private int[][] grid;

    public SudokuGrid() {
        grid = new int[gridSize][gridSize];
    }

    public void setAt(int row, int col, int number) {
        grid[row][col] = number;
    }

    public int getAt(int row, int col) {
        return grid[row][col];
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid;
    }
}
