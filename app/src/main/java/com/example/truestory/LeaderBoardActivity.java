package com.example.truestory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The leaderboard activity gets displayed when all players are ready.
 *
 * */

public class LeaderBoardActivity extends AppCompatActivity {

    /** We only have two variables*/
    public TextView leaderBoardText;
    public Button continueButton;
    public DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_bord);

        databaseHelper = new DatabaseHelper(this);

        /** Get number of players*/
        Bundle extras = getIntent().getExtras();
        int noPlayers = extras.getInt("noPlayers");


        String ranking = "";

        /** Complete the ranking String */
        for(int i = 0; i < noPlayers; i++){
            databaseHelper.openDataBase();
            ranking = ranking + "Player " + String.valueOf(i + 1) + ": " + databaseHelper.fetchScore(i) + "\n";
            databaseHelper.close();
        }

        /** Bind the screen elements. */
        leaderBoardText.findViewById(R.id.leaderBoardText);
        continueButton.findViewById(R.id.continueButton);

        leaderBoardText.setText(ranking);


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), StartGameActivity.class);

                /** Start the activity. */
                startActivity(myIntent);
                finish();
            }
        });


    }
}
