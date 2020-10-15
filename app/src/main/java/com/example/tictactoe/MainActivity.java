package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;  // the first player always begins the game

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView player1TextView;
    private TextView player2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.player1_TextView);
        player2TextView = findViewById(R.id.player2_TextView);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")){
            return;
        }
        if (player1Turn){
            ((Button)v).setText("X");
        } else {
            ((Button)v).setText("O");
        }

        roundCount++;
        if (checkForWin()){
            if (player1Turn){
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9){
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }


    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this,"Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void resetBoard(){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }


    private void draw(){
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        player1TextView.setText("Player 1 Points: "+ player1Points);
        player2TextView.setText("Player 2 Points: "+ player2Points);
    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // check rows
        for (int i = 0; i < 3; i++){
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals("")){
                return true;
            }
        }

        // check columns
        for (int i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundcount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
