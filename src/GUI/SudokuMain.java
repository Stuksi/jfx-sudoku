package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene gameScene = FXMLLoader.load(getClass().getResource("SudokuGUI.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sudoku");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
