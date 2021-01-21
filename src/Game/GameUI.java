package Game;

import Sudoku.SudokuGenerator;
import Sudoku.SudokuGrid;

import java.util.Stack;

public class GameUI {

    private final SudokuGrid sudokuGrid;
    private final SudokuGenerator sudokuGenerator;
    private final Stack<GameMove> undoMoves;
    private final Stack<GameMove> redoMoves;

    public GameUI() {
        sudokuGrid = new SudokuGrid();
        sudokuGenerator = new SudokuGenerator(sudokuGrid);
        undoMoves = new Stack<>();
        redoMoves = new Stack<>();
    }

    public void newGame() {
        undoMoves.clear();
        redoMoves.clear();
        sudokuGenerator.generate();
    }

    public void move(int row, int col, int number) {
        undoMoves.push(new GameMove(row, col, sudokuGrid.getAt(row, col), number));
        sudokuGrid.setAt(row, col, number);
    }

    public void undo() {
        if (undoMoves.empty()) {
            return;
        }
        GameMove move = undoMoves.peek();
        undoMoves.pop();
        sudokuGrid.setAt(move.getRow(), move.getCol(), move.getPrevious());
        redoMoves.push(move);
    }

    public void redo() {
        if (redoMoves.empty()) {
            return;
        }
        GameMove move = redoMoves.peek();
        redoMoves.pop();
        sudokuGrid.setAt(move.getRow(), move.getCol(), move.getNext());
        undoMoves.push(move);
    }

    public boolean finish() {
        return sudokuGrid.equals(sudokuGenerator.getSolvedGrid());
    }

    public void changeDifficulty(GameDifficulty difficulty) {
        sudokuGenerator.setGameDifficulty(difficulty);
    }

    public SudokuGrid getSudokuGrid() {
        return sudokuGrid;
    }
}
