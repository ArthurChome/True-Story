package com.example.truestory;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * This class manages an SQLite database that is locally stored
 * in the Android application.
 *
 * The class has some explicit methods that need to get implemented.
 * */

public class DatabaseHandler extends SQLiteOpenHelper {
    
    public static final String DATABASE_NAME = "Story.db"; //Case in name does not matter.
    private final Context myContext;
    String DB_PATH = null;
    private SQLiteDatabase mDataBase;
    boolean databaseExist;

    /** Define two table names (for the true and fake stories). */
    public static final String TABLE_NAME_TRUE_STORY = "trueStoryTable";
    public static final String TABLE_NAME_FAKE_STORY =  "fakeStoryTable";

    /** Both tables only have 3 rows */
    public static final String COL_1 = "id";
    public static final String COL_2 =  "storyString";
    public static final String COL_3 = "true";

    /** For simplicity, the constructor only takes the context. */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
        /** Operation is going to create your database */
        //SQLiteDatabase db = this.getWritableDatabase();
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        /** Debugger to see what the final path is now. */
        Log.e("path", DB_PATH);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** When called, the class executes given query. */
        //db.execSQL("create table " +TABLE_NAME_TRUE_STORY + "(id integer primary key autoincrement, storyString text, true boolean)" );
        //db.execSQL("create table " +TABLE_NAME_FAKE_STORY + "(id integer primary key autoincrement, storyString text, true boolean)" );
        //boolean dbExist = checkDataBase
        //check if the database exists
        databaseExist = checkDataBase();
        if (databaseExist){
            openDataBase();
            Log.e("SUCCESS", "database was opened with no hick-ups.");
        }
        else Log.e("ERROR", "Database could not be opened.");
    }

    @Override
    /***/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRUE_STORY);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAKE_STORY);

        //onCreate(db);

    }

    /** Open the database to make sure we can query it. */
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    /** Check whether the database exists or not. */
    private boolean checkDataBase()
    {   /** See if you can find the SQLite file that has the name of your database. */
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    /** Get a story from the database. */
    public String getStoryDatabase(boolean story){
        String selectedTable = null;
        if (story){
            selectedTable = TABLE_NAME_TRUE_STORY;
        }
        else selectedTable = TABLE_NAME_FAKE_STORY;
        String queryString = "SELECT * FROM " + selectedTable + " ORDER BY RANDOM() LIMIT 1;";
        return  null;// mDataBase.query(queryString, null, null, null, null, null, null);
    }
}
