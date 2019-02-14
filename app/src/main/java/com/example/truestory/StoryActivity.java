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

    /** This variable indicated whether an option has been selected.
     * -1 = nothing selected, 0 = true selected, 1 = false selected.
     * */
    int buttonSelected = -1;

    /** This  boolean indicates whether the story is fake (made up). */
    boolean trueNews = true;

    /**
     * This string is the story.
     * May be fetched from Reddit or from the internal story database.
     * */
    String story;

    /**
     * Buttons
     * You have to pick true or false for the current story to proceed.
     * */
    public Button storyTrueButton;
    public Button storyFalseButton;
    public Button proceedButton;

    /** Text views */
    public TextView notificationsText;
    public TextView currentPlayerText;
    public TextView currentRoundText;

    /** Initialize the buttons. */
    public void init(){
        /** Give a value to all these button variables. */
        storyTrueButton = findViewById(R.id.trueButton);
        storyFalseButton = findViewById(R.id.falseButton);
        proceedButton = findViewById(R.id.proceedButton);

        /** Specifiy the textviews. */
        notificationsText = findViewById(R.id.notificationsText);
        currentPlayerText = findViewById(R.id.currentPlayerText);
        currentRoundText = findViewById(R.id.currentRoundText);


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
                boolean playerCorrect;
                /** Nothing has been selected yet. */
               if(buttonSelected == -1){
                   notificationsText.setText("Select a button to proceed");
               }
               /** True button selected. */
               else if (buttonSelected == 0){
                   /** Indicate whether the player was right or not. */
                   if (trueNews){
                       playerCorrect = true;
                   }
                   else playerCorrect= false;


                   Intent myIntent = new Intent(view.getContext(), StoryResultActivity.class);

                   /** Debugger */
                   Log.d("playerCorrect?", String.valueOf(playerCorrect));

                   /** Pass some parameters. */
                   myIntent.putExtra("playerCorrect", playerCorrect);
                   myIntent.putExtra("noPlayers", noPlayers);
                   myIntent.putExtra("noRounds", noRounds);
                   myIntent.putExtra("currentPlayer", currentPlayer);
                   myIntent.putExtra("currentRound", currentRound);
                   /** Start the activity. */
                   startActivityForResult(myIntent, 0);

               }
               /** False button selected. */
               else if (buttonSelected == 1){
                   /** Indicate whether the player was right or not. */
                   if (trueNews){
                       playerCorrect = false;
                   }
                   else playerCorrect= true;

                   Intent myIntent = new Intent(view.getContext(), StoryResultActivity.class);
                   /** Pass some parameters. */
                   Log.d("playerCorrect?", String.valueOf(playerCorrect));
                   myIntent.putExtra("playerCorrect", playerCorrect);
                   myIntent.putExtra("noPlayers", noPlayers);
                   myIntent.putExtra("noRounds", noRounds);
                   myIntent.putExtra("currentPlayer", currentPlayer);
                   myIntent.putExtra("currentRound", currentRound);
                   /** Start the activity. */
                   startActivityForResult(myIntent, 0);
               }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story);
        init();

        /** Get the parameters that have been passed for creation. */
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            noPlayers = extras.getInt("noPlayers");
            noRounds = extras.getInt("noRounds");
            currentPlayer = extras.getInt("currentPlayer");
            currentRound = extras.getInt("currentRound");

            /** Specify the current player and round. */
            currentPlayerText.setText("Player: " + currentPlayer);
            currentRoundText.setText("Round: "+ currentRound);
        }
        else throw new java.lang.Error("No passed arguments found.");
    }


}
