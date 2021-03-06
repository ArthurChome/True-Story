package com.example.truestory;
//package org.harrix.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//Reference: http://qaru.site/questions/11467/ship-an-application-with-a-database
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "story.db";
    private static String DB_PATH = "C:\\Users\\Arthur\\Desktop\\TrueStory\\app\\src\\main\\res\\raw\\";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        Log.d("path raw database: ", DB_PATH);
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        //InputStream mInput = mContext.getAssets().open(DB_NAME);
        InputStream mInput = mContext.getResources().openRawResource(R.raw.story);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    public String storyStringTable(String tableName){
       Cursor result = mDataBase.rawQuery("SELECT * FROM "  + tableName + ';', null);

        Random number = new Random();
        long bound = DatabaseUtils.queryNumEntries(mDataBase, tableName);
        int n = number.nextInt((int) bound); //WARNING: THIS VARIABLE IS HARDCODED: AVOID THIS.
        Log.d("n: ", String.valueOf(n));
        result.moveToPosition(n);
        String resultString = result.getString(result.getColumnIndex("StoryString"));
       return resultString;
    }

    /** This function will query our database. */
    public String queryDatabase(String query){
        Cursor result = mDataBase.rawQuery(query, null);
        result.moveToPosition(0);
        String resultString = result.getString(result.getColumnIndex("StoryString"));
        /** With this if-clause, we will continue looping untill we find an non-empty story. */
        if (resultString == null){
            resultString = queryDatabase(query);
        }
        return resultString;
    }

    public String numberOfPlayers(){
        String queryString = "SELECT TOP 1 Score  FROM (SELECT TOP 5 Score FROM PlayerScores ORDER BY Score) sub ORDER BY ScoreDESC ";
        Cursor result = mDataBase.rawQuery(queryString, null);
        result.moveToPosition(0);
        String resultString = result.getString(result.getColumnIndex("StoryString"));
        /** Convert to number before giving it back. */
        Log.d("Number of players: ", resultString);
        return resultString;//Integer.parseInt(resultString);
    }

    /** Change the score of the given player to a given new score. */
    public void setScore(int score, int player){
        //String queryString = "UPDATE PlayerScores SET Score = "+ Integer.toString(score) +" WHERE id = "+ Integer.toString(player) +";";
        //Cursor result = mDataBase.rawQuery(queryString, null);
        ContentValues cv = new ContentValues();
        cv.put("Score", Integer.toString(score)); //These Fields should be your String values of actual column names
        cv.put("id", Integer.toString(player));

        mDataBase.update("PlayerScores", cv, "id="+Integer.toString(player), null);

    }

    /** Reset the scores of the 4 possible players to zero. */
    public void resetScores(){
        setScore(0, 0);
        setScore(0, 1);
        setScore(0, 2);
        setScore(0, 3);
    }

    public String fetchScore(int player){
        String fetchScoreQuery = "SELECT Score FROM PlayerScores WHERE id = " + Integer.toString(player) + ";";
        Cursor result = mDataBase.rawQuery(fetchScoreQuery, null);
        result.moveToPosition(0);
        int currentScore = result.getInt(result.getColumnIndex("Score"));
        return Integer.toString(currentScore);
    }


    /** Increase the current score of the given player. */
    public void increaseScore(int player){
        /** First fetch the current score */
        String fetchScoreQuery = "SELECT Score FROM PlayerScores WHERE id = " + Integer.toString(player) + ";";
        Cursor result = mDataBase.rawQuery(fetchScoreQuery, null);
        result.moveToPosition(0);
        int currentScore = result.getInt(result.getColumnIndex("Score"));
        /** Debugger */
        Log.d("found score: ", Integer.toString(currentScore));
        /** Write the new increased score to the player's score field. */
        setScore(currentScore + 1, player);
    }


    /** Player 4 is the 5th entry in the PlayerScores table and hence specifies the number
     * of players currently involved in this game. */
    public void setNoPlayers(int players){
        setScore(players, 4);
    }

    /** Winner string + reset the scores. */
    public String generateLeaderboardString(int noPlayers){

        String resultString = "";

        return resultString;



    }



    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
}