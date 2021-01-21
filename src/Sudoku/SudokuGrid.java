package Sudoku;

import java.util.Arrays;

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
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                this.grid[row][col] = grid[row][col];
            }
        }
    }

    public int[][] getGrid() {
        return grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuGrid that = (SudokuGrid) o;
        return Arrays.equals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(grid);
    }
}
