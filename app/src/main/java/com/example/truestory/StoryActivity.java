package com.example.truestory;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.Random;

/**
 * This activity handles a story where the user must guess
 * if it's actually true or made up.
 * */

public class StoryActivity extends AppCompatActivity {

    /**
     * Fields for the current game.
     * This includes the round we're at, the number of players and the current player we're at.
     * Every player has to guess a number of stories equal to the selected rounds
     * and then pass the phone to the next player.
     * */
    public int noPlayers;
    public int noRounds;
    int currentPlayer = 1;
    int currentRound = 1;
    boolean playerCorrect;

    /** Players' scores */
    int playerScore1;
    int playerScore2;
    int playerScore3 ;
    int playerScore4;

    /** This variable indicated whether an option has been selected.
     * -1 = nothing selected, 0 = true selected, 1 = false selected.
     * */
    int buttonSelected = -1;

    /** This  boolean indicates whether the story is fake (made up). */
    boolean trueNews = true;

    /** This is the database helper. */
    DatabaseHelper databaseHelper;

    /**
     * Buttons
     * You have to pick true or false for the current story to proceed. */
    public Button storyTrueButton;
    public Button storyFalseButton;
    public Button proceedButton;
    public Button exitButton;

    /** Text views */
    public TextView notificationsText;
    public TextView currentPlayerText;
    public TextView currentRoundText;
    public TextView storyText;
    public TextView playerScoreText;

    /** Query */
    public String query(String queryString){
        databaseHelper.openDataBase();
        String result = databaseHelper.queryDatabase(queryString);
        databaseHelper.close();
        return result;
    }

    /** This function will initialize the story featured */
    public void initStory(){
        /** Initialize a random number */
        Random number = new Random();
        // Obtain a number between [0 - 1].
        int n = number.nextInt(2);

        databaseHelper.openDataBase();
        String storyString;

        if (n == 0){
            trueNews = true;
            storyString =  databaseHelper.queryDatabase("SELECT * FROM TrueStory ORDER BY RANDOM() LIMIT 1;");
        }
        else {
            trueNews = false;
            storyString = databaseHelper.queryDatabase("SELECT * FROM FakeStory  ORDER BY RANDOM() LIMIT 1;");
        }
        databaseHelper.close();
        /** Debugger */
        Log.d("storyString chosen: ", storyString);
        storyText.setText(storyString);
    }

    /** Initialize the buttons. */
    public void init(){
        databaseHelper =  new DatabaseHelper(this);

        /** Give a value to all these button variables. */
        storyTrueButton = findViewById(R.id.trueButton);
        storyFalseButton = findViewById(R.id.falseButton);
        proceedButton = findViewById(R.id.proceedButton);
        exitButton = findViewById(R.id.buttonExit);

        /** Specifiy the textviews. */
        notificationsText = findViewById(R.id.notificationsText);
        currentPlayerText = findViewById(R.id.currentPlayerText);
        currentRoundText = findViewById(R.id.currentRoundText);
        storyText = findViewById(R.id.storyText);
        playerScoreText = findViewById(R.id.playerScoreText);


        /** Set-up a listener for the new game button. */
        storyTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelected = 0;
                storyTrueButton.setBackgroundColor(Color.GREEN);
                storyFalseButton.setBackgroundColor(Color.WHITE);
            }
        });

        /** Set-up a listener for the new game button. */
        storyFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSelected = 1;
                storyTrueButton.setBackgroundColor(Color.WHITE);
                storyFalseButton.setBackgroundColor(Color.GREEN);
            }
        });

        /** Set-up a listener for the new game button. */
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Nothing has been selected yet. */
               if(buttonSelected == -1){
                   notificationsText.setText("Select a button to proceed");
               }
               /** True button selected. */
               else {
                   if (buttonSelected == 0){
                       /** Indicate whether the player was right or not. */
                       if (trueNews){
                           playerCorrect = true;
                           databaseHelper.openDataBase();
                           databaseHelper.increaseScore(currentPlayer - 1);
                           databaseHelper.close();
                       }
                       else playerCorrect= false;

                       /** Debugger */
                       Log.d("playerCorrect?", String.valueOf(playerCorrect));


                   }
                   /** False button selected. */
                   else if (buttonSelected == 1){
                       /** Indicate whether the player was right or not. */
                       if (trueNews){
                           playerCorrect = false;
                           databaseHelper.openDataBase();
                           databaseHelper.increaseScore(currentPlayer - 1);
                           databaseHelper.close();
                       }
                       else playerCorrect= true;
                   }

                   Intent myIntent = new Intent(view.getContext(), StoryResultActivity.class);
                   /** Pass some parameters.
                    *  Alot of code duplication, should be improved.
                    *  */
                   myIntent.putExtra("playerCorrect", playerCorrect);
                   myIntent.putExtra("noPlayers", noPlayers);
                   myIntent.putExtra("noRounds", noRounds);
                   myIntent.putExtra("currentPlayer", currentPlayer);
                   myIntent.putExtra("currentRound", currentRound);

                   myIntent.putExtra("scorePlayer1", playerScore1);
                   myIntent.putExtra("scorePlayer2", playerScore2);
                   myIntent.putExtra("scorePlayer3", playerScore3);
                   myIntent.putExtra("scorePlayer4", playerScore4);

                   /** Start the next activity, end this one. */
                   startActivity(myIntent);
                   finish();
               }
            }
        });

        /** Exit button: the player gets back to the start screen. */
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), StartGameActivity.class);
                /** Start the next activity, end this one. */
                startActivity(myIntent);
                finish();
            }});
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        init();
        initStory();

        /** Get the parameters that have been passed for creation. */
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            noPlayers = extras.getInt("noPlayers");
            noRounds = extras.getInt("noRounds");
            currentPlayer = extras.getInt("currentPlayer");
            currentRound = extras.getInt("currentRound");

            /** Get players' scores */
            playerScore1 = extras.getInt("scorePlayer1");
            playerScore2 = extras.getInt("scorePlayer2");
            playerScore3 = extras.getInt("scorePlayer3");
            playerScore4 = extras.getInt("scorePlayer4");

            if (noPlayers == 1){
                /** Specify the current player and round. */
                currentPlayerText.setText("Player: one ");
                currentRoundText.setText("Round: infinite");
                /** At default, the exit button is not visible. */
                exitButton.setVisibility(View.VISIBLE);
            }
            else {
                /** Specify the current player and round. */
                currentPlayerText.setText("Player: " + currentPlayer);
                currentRoundText.setText("Round: " + currentRound);
                databaseHelper.openDataBase();
                String currentScore = databaseHelper.fetchScore(currentPlayer - 1);
                databaseHelper.close();
                playerScoreText.setText("Score: " + currentScore);
            }
        }
        else throw new java.lang.Error("No passed arguments found.");
    }

}
