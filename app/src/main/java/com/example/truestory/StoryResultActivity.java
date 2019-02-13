package com.example.truestory;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StoryResultActivity extends AppCompatActivity {

    /** Fields */
    public int noPlayers;
    public int noRounds;
    int currentPlayer = 1;
    int currentRound = 1;
    boolean playerCorrect;
    private RelativeLayout relativeLayoutOne;
    //private TextView resultText = findViewById(R.id.resultText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_result);

        relativeLayoutOne = (RelativeLayout) findViewById(R.id.relativeLayoutOne);

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

        /** Was the player correct? */
        if (playerCorrect){
            relativeLayoutOne.setBackgroundColor(Color.GREEN);
            //resultText.setText("Good guess. Nice work!");
        }
        else {
            relativeLayoutOne.setBackgroundColor(Color.RED);
            //resultText.setText("Bad Guess. Try again.");
        }


    }
}
