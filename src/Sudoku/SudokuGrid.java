package Sudoku;

// SudokuGrid - Data structure representing the cells of a 9x9 sudoku grid
public class SudokuGrid {

    public static final int gridSize = 9;
    public static final int miniGridSize = 3;

    private int[][] grid;

    public SudokuGrid() {
        grid = new int[gridSize][gridSize];
    }

    // Adds a value to the specified cell (row, col)
    public void setAt(int row, int col, int number) {
        grid[row][col] = number;
    }

    // Returns the value in the specified cell (row, col)
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

    public boolean equals(SudokuGrid sudokuGrid) {
        int[][] grid = sudokuGrid.getGrid();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (this.grid[row][col] != grid[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}
