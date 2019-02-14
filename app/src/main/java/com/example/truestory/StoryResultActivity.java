package com.example.truestory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoryResultActivity extends AppCompatActivity {

    /** Fields */
    public int noPlayers;
    public int noRounds;
    int currentPlayer = 1;
    int currentRound = 1;
    boolean playerCorrect;
    public RelativeLayout relativeLayoutOne;
    public TextView resultText;
    public Button continueButton;

    /** Initialization function */
    void init(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Intent left blank because we can go in different directions. */
                Intent myIntent;

                /** Can we finish the game? */
                if(currentRound == noRounds && currentPlayer == noPlayers){
                    myIntent = new Intent(view.getContext(), StartGame.class);
                }
                /** Is it the next player's turn? */
                else if (currentRound == noRounds){
                    currentRound = 1;
                    currentPlayer = currentPlayer + 1;
                    myIntent = new Intent(view.getContext(), StoryActivity.class);
                }
                /** Go to the next round. */
                else {
                    currentRound = currentRound + 1;
                    myIntent = new Intent(view.getContext(), StoryActivity.class);
                }

                /** Pass some parameters. */
                myIntent.putExtra("noPlayers", noPlayers);
                myIntent.putExtra("noRounds", noRounds);
                myIntent.putExtra("currentPlayer", currentPlayer);
                myIntent.putExtra("currentRound", currentRound);
                /** Start the activity. */
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_result);

        /** Specify the activity layout, the result text and the continue button. */
        relativeLayoutOne = findViewById(R.id.relativeLayoutOne);
        resultText = findViewById(R.id.resultText);
        continueButton = findViewById(R.id.continueButton);

        /** Initialize the continue button. */
        init();

        /** Get the parameters that have been passed for creation. */
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            noPlayers = extras.getInt("noPlayers");
            noRounds = extras.getInt("noRounds");
            currentPlayer = extras.getInt("currentPlayer");
            currentRound = extras.getInt("currentRound");
            playerCorrect = extras.getBoolean("playerCorrect");
        }
        /** If the parameters cannot be found, something went wrong. */
        else throw new java.lang.Error("No passed arguments found.");

        Log.d("green? ", String.valueOf(playerCorrect));

        /** Was the player correct? */
        if (playerCorrect){
            relativeLayoutOne.setBackgroundColor(Color.GREEN);
            resultText.setText("Good guess. Nice work!");
        }
        else {
            relativeLayoutOne.setBackgroundColor(Color.RED);
            resultText.setText("Bad Guess. Try again.");
        }


    }
}
