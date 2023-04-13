package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {
    private Label playerXScoreLabel, playerOScoreLabel;
    private Button buttons[][] = new Button[3][3];

    private boolean playerXTurn = true;

    private int playerXScore=0, playerOScore=0;

    private BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10,20,20,10));

        //Title
        Label titleLabel = new Label("TIC TAC TOE");
        titleLabel.setStyle("-fx-font-size : 30pt; -fx-font-weight : bold;");
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        //Game Board
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3 ; j++) {
                Button button = new Button();
                button.setPrefSize(100,100);
                button.setStyle("-fx-font-size : 24pt; -fx-font-weight : bold;");
                button.setOnAction(actionEvent -> buttonClicked(button));
                buttons[i][j] = button;
                gridPane.add(button,j,i);
            }
        }
        root.setCenter(gridPane);

        //Score Board
        HBox scoreBoard = new HBox(20);
        scoreBoard.setAlignment(Pos.CENTER);
        playerXScoreLabel = new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        playerOScoreLabel = new Label("Player O : 0");
        playerOScoreLabel.setStyle("-fx-font-size : 16pt; -fx-font-weight : bold;");
        scoreBoard.getChildren().addAll(playerXScoreLabel, playerOScoreLabel);
        root.setBottom(scoreBoard);

        return root;
    }

    private void checkWinner(){
        //condition 1: all row values same
        for(int i=0; i<3;i++){
            if( buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][1].getText().equals(buttons[i][2].getText())
                    && !buttons[i][0].getText().isEmpty())
            {
                String winner = buttons[i][0].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }

        //condition 2: all column values same
        for(int i=0; i<3;i++){
            if(buttons[0][i].getText().equals(buttons[1][i].getText())
                    && buttons[1][i].getText().equals(buttons[2][i].getText())
                    && !buttons[0][i].getText().isEmpty())
            {
                String winner = buttons[0][i].getText();
                showWinnerDialog(winner);
                updateScore(winner);
                resetBoard();
                return;
            }
        }
        //condition 3: all diagonal values same
        //diagonal 1
        if(buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[2][2].getText().equals(buttons[1][1].getText())
                && !buttons[1][1].getText().isEmpty()){
            String winner = buttons[1][1].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }
        //diagonal 2
        if(buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[2][0].getText().equals(buttons[1][1].getText())
                && !buttons[1][1].getText().isEmpty())
        {
            String winner = buttons[1][1].getText();
            showWinnerDialog(winner);
            updateScore(winner);
            resetBoard();
            return;
        }

        //condition 4 : Draw
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(buttons[row][col].getText().isEmpty()){
                   return;
                }
            }
        }
        resetBoard();
        showDrawDialog();

    }

    private void buttonClicked(Button button){
        if(button.getText().contains("")){
            if(playerXTurn){
                button.setText("X");
            }else{
                button.setText("O");
            }
            playerXTurn = !playerXTurn;
        }
        checkWinner();
    }

    private  void showWinnerDialog(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setContentText("Congratulations "+ winner + "!You won the game");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private  void showDrawDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setContentText("Game Over ! It's a Draw.");
        alert.setHeaderText("");
        alert.showAndWait();
    }

    private void updateScore(String winner){
        if(winner.equals("X")){
            playerXScore++;
            playerXScoreLabel.setText("Player X : "+playerXScore);
        }else{
            playerOScore++;
            playerOScoreLabel.setText("Player O : "+playerOScore);
        }
    }

    private void resetBoard(){
        for(Button row[]: buttons){
            for(Button button: row){
                button.setText("");
            }
        }

    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Tic Tac Toe");
        stage.getIcons().add(new Image("file:/Users/ajay/IdeaProjects/Tic-Tac-Toe/src/main/resources/icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}