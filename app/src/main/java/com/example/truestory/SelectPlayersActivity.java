package com.example.truestory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectPlayersActivity  extends AppCompatActivity {

    /** Different buttons */
    public Button player2Button;
    public Button player3Button;
    public Button player4Button;

    /** This is the database helper. */
    DatabaseHelper databaseHelper;


    /** Pass arguments to the next activity that you have the intention to start. */
    public void passArguments(Intent intent, int noPlayers, int noRounds, int currentPlayer, int currentRound){
        intent.putExtra("noPlayers", noPlayers);
        intent.putExtra("noRounds", noRounds);
        intent.putExtra("currentPlayer", currentPlayer);
        intent.putExtra("currentRound", currentRound);
        return;
    }

    /** Initialize the buttons. */
    public void init(){

        /** Get the database helper and reset the scores of the potential 4 players. */
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDataBase();
        databaseHelper.resetScores();
        databaseHelper.close();

        /** Initialize the buttons */
        player2Button = findViewById(R.id.player2Button);
        player3Button = findViewById(R.id.player3Button);
        player4Button = findViewById(R.id.player4Button);

        /** Set-up a listener for the new game button. */
        player2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION","click click.");
                /** When the player clicks the new game-button,
                 * he gets rerouted to the select no. of players screen. */
                Intent myIntent = new Intent(view.getContext(), StoryActivity.class);
                /** Pass some arguments to the next activity. */
                passArguments(myIntent, 2, 5, 1, 1);

                databaseHelper.openDataBase();
                databaseHelper.setNoPlayers(2);
                databaseHelper.close();

                /** Start the activity. */
                startActivity(myIntent);
                finish();
            }
        });

        player3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION","click click.");
                /** When the player clicks the new game-button,
                 * he gets rerouted to the select no. of players screen. */
                Intent myIntent = new Intent(view.getContext(), StoryActivity.class);
                /** Pass some arguments to the next activity. */
                passArguments(myIntent, 3, 5, 1, 1);

                databaseHelper.openDataBase();
                databaseHelper.setNoPlayers(3);
                databaseHelper.close();

                /** Start the activity. */
                startActivity(myIntent);
                finish();
            }
        });

        player4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION","click click.");
                /** When the player clicks the new game-button,
                 * he gets rerouted to the select no. of players screen. */
                //StoryActivity activity = new StoryActivity();
                Intent myIntent = new Intent(view.getContext(), StoryActivity.class);
                /** Pass some arguments to the next activity. */
                passArguments(myIntent, 4, 5, 1, 1);

                databaseHelper.openDataBase();
                databaseHelper.setNoPlayers(4);
                databaseHelper.close();


                /** Start the activity and finish this activity.
                 *  Prevents the user from going back to this activity.
                 *
                 *  It also clears the activity stack, liberating alot of memory space
                 *  and speeding up the application.
                 *  */
                startActivity(myIntent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_players);
        init();
    }
}
