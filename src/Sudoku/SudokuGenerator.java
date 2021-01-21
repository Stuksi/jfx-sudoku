package Sudoku;

import Game.GameDifficulty;

public class SudokuGenerator {

    private final SudokuGrid sudokuGrid;
    private GameDifficulty gameDifficulty;

    public SudokuGenerator(SudokuGrid sudokuGrid) {
        this.sudokuGrid = sudokuGrid;
        this.gameDifficulty = GameDifficulty.EASY;
    }

    public void generate() {
        int[][] genGrid = new int[SudokuGrid.gridSize][SudokuGrid.gridSize];

        // Generation

        sudokuGrid.setGrid(genGrid);
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }
}