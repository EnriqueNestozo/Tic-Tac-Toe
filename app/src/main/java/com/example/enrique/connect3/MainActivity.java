package com.example.enrique.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean gameIsActive = true;
    int activePlayer = 0; //0 = yellow, 1 = red
    int[] gameState = {2,2,2,2,2,2,2,2,2}; //the array represent the places, 2 means unplayed, the free slots
    int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}}; //all the posibles solutions to win

    public void dropIn(View view){ //method contained in all red and yellow images within the gridlayout
        ImageView counter = (ImageView) view; //get the image
        int tappedCounter = Integer.parseInt(counter.getTag().toString()); //the tag is on the xml, and represent the position

        if(gameState[tappedCounter] == 2 && gameIsActive){ //if the position is free or unplayed(it's 2) and if the game is active
            gameState[tappedCounter] = activePlayer; //the gameState in that position change from two to the number of the active player
            counter.setTranslationY(-1000f);//when the user press the image, this move 1000 pixels up, so it's off the screen

            if(activePlayer == 0){
                counter.setImageResource(R.drawable.yellow);//if is player 0, set the image yellow
                activePlayer = 1;//now the activePlayer change to 1
            } else{
                counter.setImageResource(R.drawable.red);//if is player 1, set image red
                activePlayer = 0;//now the activePlayer change to 0
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);//the image drops to it position in 300 milliseconds

            for(int[] winningPosition : winningPositions){ //check all the winningPositions in the gameState to see if has the same value
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] !=2){ //if all 3 are the same and are different to 2(are played)
                    //someone has won
                    gameIsActive = false;
                    String winner = "Red";
                    if(gameState[winningPosition[0]] == 0){
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }else{ //if no one won
                    boolean gameIsOver = true;
                    for(int counterState : gameState){ //check all the slots

                        if(counterState == 2){ //if there is at less one 2, it means that the game is not over yet
                            gameIsOver = false;
                        }
                    }

                    if(gameIsOver){
                        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
                        winnerMessage.setText("It's a draw!");
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }


    }

    public void playAgain(View view){

        gameIsActive = true;

        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        for(int i = 0; i < gameState.length ; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //getChildCount get the number of views there are inside of the gridLayout
        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
