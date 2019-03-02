package com.example.truestory;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * Name giving is wrong.
 * Should end with activity: SHOULD BE FIXED.
 * */

public class StartGame extends AppCompatActivity {
    /** Setup the new game button */
    public Button newGameButton;
    public Button randomStoriesButton;

    /** Pass arguments to the next activity that you have the intention to start. */
    public void passArguments(Intent intent, int noPlayers, int noRounds, int currentPlayer, int currentRound){
        intent.putExtra("noPlayers", noPlayers);
        intent.putExtra("noRounds", noRounds);
        intent.putExtra("currentPlayer", currentPlayer);
        intent.putExtra("currentRound", currentRound);
        return;
    }

    /** Initialise the public buttons */
    void initButtons(){
        newGameButton = findViewById(R.id.newGameButton);
        randomStoriesButton = findViewById(R.id.randomStories);

        /** Set-up a listener for the new game button. */
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CREATION","click click.");
                /** When the player clicks the new game-button,
                 * he gets rerouted to the select no. of players screen. */
                Intent myIntent = new Intent(view.getContext(), SelectPlayersActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        /** Set-up a listener for the new game button. */
        randomStoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** When the player clicks the random story button,
                 *  he can play as long as he likes. */
                Intent myIntent = new Intent(view.getContext(), StoryActivity.class);
                /** Pass some arguments to the next activity. */
                passArguments(myIntent, 1, -1, 1, 1);
                /** Start the activity. */
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("CREATION","created.");
        /** Initialize the buttons. */
        initButtons();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         * */
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}