package Sudoku;

import Game.GameDifficulty;

import java.util.Random;

public class SudokuGenerator {

    private final SudokuGrid sudokuGrid;
    private final SudokuGrid solvedGrid;

    private GameDifficulty gameDifficulty;

    private int[][] generatedGrid;

    private int countSolutions;
    private int[] numberList;

    private Random r;

    public SudokuGenerator(SudokuGrid sudokuGrid) {
        this.sudokuGrid = sudokuGrid;
        solvedGrid = new SudokuGrid();
        gameDifficulty = GameDifficulty.EASY;
        numberList = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        countSolutions = 0;
        r = new Random();
    }

    public void generate() {
        generatedGrid = new int[SudokuGrid.gridSize][SudokuGrid.gridSize];

        fillGrid();
        solvedGrid.setGrid(generatedGrid);
        reduceGrid();

        sudokuGrid.setGrid(generatedGrid);
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public SudokuGrid getSolvedGrid() {
        return solvedGrid;
    }

    // Grid Generation

    private boolean checkGrid() {
        for (int row = 0; row < SudokuGrid.gridSize; row++) {
            for (int col = 0; col < SudokuGrid.gridSize; col++) {
                if (generatedGrid[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean solveGrid() {
        for (int row = 0; row < SudokuGrid.gridSize; row++) {
            for (int col = 0; col < SudokuGrid.gridSize; col++) {
                if (generatedGrid[row][col] == 0) {
                    for (int number = 1; number <= SudokuGrid.gridSize; number++) {
                        if (isValidPlacement(row, col, number)) {
                            generatedGrid[row][col] = number;
                            if (checkGrid()) {
                                countSolutions++;
                                break;
                            } else {
                                if (solveGrid()) {
                                    return true;
                                }
                            }
                        }
                    }
                    generatedGrid[row][col] = 0;
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isValidPlacement(int row, int col, int number) {
        for (int colIt = 0; colIt < SudokuGrid.gridSize; colIt++) {
            if (generatedGrid[row][colIt] == number) {
                return false;
            }
        }

        for (int rowIt = 0; rowIt < SudokuGrid.gridSize; rowIt++) {
            if (generatedGrid[rowIt][col] == number) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int rowIt = 0; rowIt < SudokuGrid.miniGridSize; rowIt++) {
            for (int colIt = 0; colIt < SudokuGrid.miniGridSize; colIt++) {
                if (generatedGrid[rowIt + startRow][colIt + startCol] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean fillGrid() {
        for (int row = 0; row < SudokuGrid.gridSize; row++) {
            for (int col = 0; col < SudokuGrid.gridSize; col++) {
                if (generatedGrid[row][col] == 0) {
                    shuffle();
                    for (int numIt = 0; numIt < numberList.length; numIt++) {
                        if (isValidPlacement(row, col, numberList[numIt])) {
                            generatedGrid[row][col] = numberList[numIt];
                            if (checkGrid()) {
                                return true;
                            } else {
                                if (fillGrid()) {
                                    return true;
                                }
                            }
                        }
                    }
                    generatedGrid[row][col] = 0;
                    return false;
                }
            }
        }
        return false;
    }

    private void shuffle() {
        int index, temp;
        for (int i = numberList.length - 1; i > 0; i--) {
            index = r.nextInt(i + 1);

            temp = numberList[index];
            numberList[index] = numberList[i];
            numberList[i] = temp;
        }
    }

    private void reduceGrid() {
        int attempts = switch (gameDifficulty) {
            case EASY -> 5;
            case MEDIUM -> 10;
            case HARD -> 15;
        };

        int row, col, temp;
        int[][] copyGrid = new int[SudokuGrid.gridSize][SudokuGrid.gridSize];
        while (attempts > 0) {
            do {
                row = r.nextInt(SudokuGrid.gridSize);
                col = r.nextInt(SudokuGrid.gridSize);
            } while (generatedGrid[row][col] == 0);

            temp = generatedGrid[row][col];
            generatedGrid[row][col] = 0;

            for (int rowIt = 0; rowIt < SudokuGrid.gridSize; rowIt++) {
                for (int colIt = 0; colIt < SudokuGrid.gridSize; colIt++) {
                    copyGrid[rowIt][colIt] = generatedGrid[rowIt][colIt];
                }
            }

            countSolutions = 0;
            solveGrid();
            if (countSolutions != 1) {
                generatedGrid[row][col] = temp;
                attempts--;
            }
        }
    }
}