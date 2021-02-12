package Game;

import Sudoku.SudokuGenerator;
import Sudoku.SudokuGrid;

import java.util.Stack;

// GameUI is the control unit of the sudoku game
public class GameUI {

    private SudokuGrid sudokuGrid;
    private final SudokuGenerator sudokuGenerator;
    private final Stack<GameMove> undoMoves;
    private final Stack<GameMove> redoMoves;

    public GameUI() {
        sudokuGrid = new SudokuGrid();
        sudokuGenerator = new SudokuGenerator(sudokuGrid);
        undoMoves = new Stack<>();
        redoMoves = new Stack<>();
    }

    // Destroys previous information and generates a new sudoku game
    public void newGame() {
        undoMoves.clear();
        redoMoves.clear();
        sudokuGenerator.generate();
    }

    // Makes and saves the currently played move
    public void move(int row, int col, int number) {
        undoMoves.push(new GameMove(row, col, sudokuGrid.getAt(row, col), number));
        sudokuGrid.setAt(row, col, number);
    }

    // Undoes to the previously played move
    public void undo() {
        if (undoMoves.empty()) {
            return;
        }
        GameMove move = undoMoves.peek();
        undoMoves.pop();
        sudokuGrid.setAt(move.getRow(), move.getCol(), move.getPrevious());
        redoMoves.push(move);
    }

    // Redoes to the last undid move
    public void redo() {
        if (redoMoves.empty()) {
            return;
        }
        GameMove move = redoMoves.peek();
        redoMoves.pop();
        sudokuGrid.setAt(move.getRow(), move.getCol(), move.getNext());
        undoMoves.push(move);
    }

    // Checks if the sudoku is correctly solved
    public boolean finish() {
        return sudokuGrid.equals(sudokuGenerator.getSolvedGrid());
    }

    // Solves the current sudoku
    public void solve() {
        sudokuGrid.setGrid(sudokuGenerator.getSolvedGrid().getGrid());
    }

    // Changes the difficulty of the sudoku game
    public void changeDifficulty(GameDifficulty difficulty) {
        sudokuGenerator.setGameDifficulty(difficulty);
    }

    // Returns the current sudoku grid
    public SudokuGrid getSudokuGrid() {
        return sudokuGrid;
    }

    // Return the current game difficulty
    public GameDifficulty getGameDifficulty() {
        return sudokuGenerator.getGameDifficulty();
    }
}
