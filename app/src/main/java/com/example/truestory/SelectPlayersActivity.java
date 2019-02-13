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

    public Button player2Button;
    public Button player3Button;
    public Button player4Button;


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
                /** Start the activity. */
                startActivityForResult(myIntent, 0);
            }
        });

        player3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION","click click.");
                /** When the player clicks the new game-button,
                 * he gets rerouted to the select no. of players screen. */
                //StoryActivity activity = new StoryActivity();
                Intent myIntent = new Intent(view.getContext(), StoryActivity.class);
                /** Pass some arguments to the next activity. */
                passArguments(myIntent, 3, 5, 1, 1);
                /** Start the activity. */
                startActivityForResult(myIntent, 0);
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
                /** Start the activity. */
                startActivityForResult(myIntent, 0);
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
