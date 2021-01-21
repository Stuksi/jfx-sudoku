package GUI;

import Game.GameDifficulty;
import Game.GameUI;
import Sudoku.SudokuGrid;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuControls implements Initializable {

    private final int GS = SudokuGrid.gridSize;
    private final int MGS = SudokuGrid.miniGridSize;

    private GameUI gameUI;

    private Timeline timeline;
    private int minutes = 0;
    private int seconds = 0;

    private Label[][] gridTiles;
    private Label highlightedTile;

    private boolean finished;

    @FXML
    private GridPane gpGrid;

    // Initialize

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridTiles = new Label[GS][GS];
        gameUI = new GameUI();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
            if (seconds == 60) {
                minutes++;
                seconds = 0;
            }
            lblTimer.setText(String.format("%02d:%02d", minutes, seconds));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        int row, col, rowIt = 0, colIt = 0;
        for (Node gpMiniGridIt : gpGrid.getChildren()) {
            if (gpMiniGridIt instanceof GridPane) {
                GridPane gpMiniGrid = (GridPane) gpMiniGridIt;
                row = 0;
                col = 0;
                for (Node lblTileIt : gpMiniGrid.getChildren()) {
                    if (lblTileIt instanceof Label) {
                        Label lblTile = (Label) lblTileIt;
                        lblTile.setBackground(exitBackground);
                        gridTiles[row + rowIt * MGS][col + colIt * MGS] = lblTile;
                        col++;
                        if (col == MGS) {
                            row++;
                            col = 0;
                        }
                    }
                }
                colIt++;
                if (colIt == MGS) {
                    rowIt++;
                    colIt = 0;
                }
            }
        }

        resetGame();
    }

    private void render() {
        SudokuGrid sudokuGrid = gameUI.getSudokuGrid();

        int number;
        for (int row = 0; row < GS; row++) {
            for (int col = 0; col < GS; col++) {
                number = sudokuGrid.getAt(row, col);
                gridTiles[row][col].setText(number == 0 ? "" : "" + number);
            }
        }
    }

    // Key Listener

    @FXML
    void makeMove(KeyEvent event) {
        if (highlightedTile == null || !event.getCode().isDigitKey()) {
            return;
        }

        int number = 0;
        switch (event.getCode()) {
            case DIGIT1 -> number = 1;
            case DIGIT2 -> number = 2;
            case DIGIT3 -> number = 3;
            case DIGIT4 -> number = 4;
            case DIGIT5 -> number = 5;
            case DIGIT6 -> number = 6;
            case DIGIT7 -> number = 7;
            case DIGIT8 -> number = 8;
            case DIGIT9 -> number = 9;
        }

        highlightedTile.setText(number == 0 ? "" : "" + number);
        Pair<Integer, Integer> tilePosition = findTilePosition();
        gameUI.move(tilePosition.getKey(), tilePosition.getValue(), number);
    }

    private Pair<Integer, Integer> findTilePosition() {
        for (int row = 0; row < GS; row++) {
            for (int col = 0; col < GS; col++) {
                if (highlightedTile == gridTiles[row][col]) {
                    return new Pair(row, col);
                }
            }
        }
        return null;
    }

    // Mouse Listener

    private final Background clickBackground = new Background(new BackgroundFill(Color.rgb(140, 250, 160), new CornerRadii(0), new Insets(0)));
    private final Background enterBackground = new Background(new BackgroundFill(Color.rgb(150, 180, 160), new CornerRadii(0), new Insets(0)));
    private final Background exitBackground = new Background(new BackgroundFill(Color.rgb(250, 250, 250), new CornerRadii(0), new Insets(0)));

    @FXML
    void clickTile(MouseEvent event) {
        Label clickedTile = (Label) event.getSource();

        if (clickedTile == highlightedTile) {
            clickedTile.setBackground(exitBackground);
            highlightedTile = null;
            return;
        }

        if (highlightedTile != null) {
            highlightedTile.setBackground(exitBackground);
        }

        clickedTile.setBackground(clickBackground);
        highlightedTile = clickedTile;
    }

    @FXML
    void enterTile(MouseEvent event) {
        Label hoveredTile = (Label) event.getSource();

        if (hoveredTile == highlightedTile) {
            return;
        }

        hoveredTile.setBackground(enterBackground);
    }

    @FXML
    void exitTile(MouseEvent event) {
        Label exitedTile = (Label) event.getSource();

        if (exitedTile == highlightedTile) {
            return;
        }

        exitedTile.setBackground(exitBackground);
    }

    // Menu

        // Game

    @FXML
    void resetGame() {
        timeline.stop();
        finished = false;
        minutes = 0;
        seconds = 0;
        gameUI.newGame();
        lblTimer.setText("00:00");
        render();
        timeline.play();
    }

    @FXML
    void openPlayerLogs() {

    }

    @FXML
    void quitGame() {
        System.exit(0);
    }

        // Difficulty

    @FXML
    void changeDifficultyEasy() {
        gameUI.changeDifficulty(GameDifficulty.EASY);
    }

    @FXML
    void changeDifficultyMedium() {
        gameUI.changeDifficulty(GameDifficulty.MEDIUM);
    }

    @FXML
    void changeDifficultyHard() {
        gameUI.changeDifficulty(GameDifficulty.HARD);
    }

        // Edit

    @FXML
    void undoMove() {
        gameUI.undo();
        render();
    }

    @FXML
    void redoMove() {
        gameUI.redo();
        render();
    }

    @FXML
    private Label lblTimer;

    @FXML
    private Label lblSuccessfulFinish;

    @FXML
    private HBox hbFailedFinish;

    @FXML
    void finishGame() {
        if (finished) {
            return;
        }
        if (gameUI.finish()) {
            lblSuccessfulFinish.setVisible(true);
            timeline.stop();
        } else {
            hbFailedFinish.setVisible(true);
            PauseTransition visiblePause = new PauseTransition(Duration.seconds(10));
            visiblePause.setOnFinished(ev -> hbFailedFinish.setVisible(false));
            visiblePause.play();
        }
    }

    @FXML
    void solveSudoku() {

    }
}
