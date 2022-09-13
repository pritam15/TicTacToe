package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
// All GUI Veriables
    private static Scene scene;
    private GridPane gridPane = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private Label title = new Label("Tic Tac Toe Game");
    private Button restartButton = new Button("Restart Game");
    Font font = Font.font("Roboto", FontWeight.BOLD, 30);
    private Button[] btns = new Button[9];


    // All Logic Variable

    boolean gameOver = false;
    int activePlayer=0;
    int gameState[]={3,3,3,3,3,3,3,3,3};
    int[][] winningPosition = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6},
    };





    @Override
    public void start(Stage stage) throws IOException {

        this.createGUI();
        this.handelEvent();

        Scene scene = new Scene(borderPane,550,650);
        stage.setScene(scene);
        stage.show();
    }

    // This functon is for creating GUI
    private void createGUI() {
        // Creating Title
        title.setFont(font);
        // Creating Restart button
        restartButton.setFont(font);
        restartButton.setDisable(true);
        // Setting title and restart buttone to borderPane
        borderPane.setTop(title);
        borderPane.setBottom(restartButton);
        //setting borderPane Components Alignmment
        borderPane.setAlignment(title, Pos.CENTER);
        borderPane.setAlignment(restartButton,Pos.CENTER);
        borderPane.setPadding(new Insets(20,20,20,20));

        // Working on Game Grid or we can say button
        int label = 0;
        for(int i = 0; i < 3;i++){
            for(int j = 0; j < 3;j++){
                Button btn = new Button();
                btn.setId(label+"");
                btn.setFont(font);
                gridPane.add(btn,j,i);
                gridPane.setAlignment(Pos.CENTER);
                btn.setPrefWidth(160);
                btn.setPrefHeight(160);
                btns[label] = btn;
                label++;

            }
        }
        borderPane.setCenter(gridPane);
    }

    // Event handling method
    private void handelEvent() {
        // Set Event on Restart Button
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i = 0; i < 9; i++){
                    gameState[i] = 3;
                    btns[i].setGraphic(null);
                }
                gameOver = false;
                restartButton.setDisable(true);

            }
        });
        for (Button btn : btns) {
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Button currentButton = (Button) actionEvent.getSource();
                    String ids = currentButton.getId();
                    int id = Integer.parseInt(ids);

                    //Logic
                    if (gameOver) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setContentText("Game Over");
                        alert.show();
                    } else {

                        if (gameState[id] == 3) {
                            if (activePlayer == 1) {
                                //currentButton.setText(activePlayer + "");
                                currentButton.setGraphic(new ImageView(new Image("file:src/main/resources/x2.png")));
                                gameState[id] = activePlayer;
                                checkForWinner();
                                activePlayer = 0;
                            } else {
                                //currentButton.setText(activePlayer + "");
                                currentButton.setGraphic(new ImageView(new Image("file:src/main/resources/zero1.png")));
                                gameState[id] = activePlayer;
                                checkForWinner();
                                activePlayer = 1;
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message");
                            alert.setContentText("Try to a Valid Move");
                            alert.show();
                        }
                    }


                }
            });

        }
    }
    // Creating a Method For checking for who was the Winner
    private void checkForWinner() {
        for(int[] wp : winningPosition){
            if(gameState[wp[0]] == gameState[wp[1]]  && gameState[wp[1]]  == gameState[wp[2]]  && gameState[wp[0]]  != 3){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Success Message");
                alert.setContentText(activePlayer == 1 ? "X" : "0" +" has Won");
                alert.show();
                restartButton.setDisable(false);
                break;
            }
            else{
                boolean flag = true;
                for (int i : gameState){
                    flag = true;
                    if(i == 3) {
                        flag = false;
                    }
                }
                if(flag ){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Draw Message");
                    alert.setContentText("It's Draw");
                    alert.show();
                    restartButton.setDisable(false);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}