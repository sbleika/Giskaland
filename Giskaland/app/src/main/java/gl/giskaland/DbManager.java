package gl.giskaland;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tumsgis on 26.2.2015.
 *
 * DbManager.
 * Provides access to the giskaland database.
 */
public class DbManager extends SQLiteOpenHelper {

    private static String DB_PATH;
    private static String DB_NAME = "giskaland.db";
    private SQLiteDatabase myDb;
    private final Context myContext;

    private static final int DB_VERSION = 19;

    /**
     *  Constructor for the DbManager.
     * @param context This context.
     */
    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;

        String packageName = context.getPackageName();
        DB_PATH = "/data/data/" + packageName + "/databases/";
    }

    /**
     * Creates a new database.
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (dbExist) {
            // nothing, already exists
        } else {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Checks if the database exists in the /assets folder.
     * @return True iiff the database exists in the /assets folder.
     */
    private boolean checkDatabase() {
        SQLiteDatabase db = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            System.out.println(DB_PATH + DB_NAME);
            //throw new Error("Database doesn't exist yet");
        }

        if (db != null) {
            db.close();
        }

        return db != null;
    }

    /**
     * Copy the database file from the /assets folder.
     * @throws IOException
     */
    private void copyDatabase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);


        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Opens the database.
     * @throws SQLiteException
     */
    public void openDatabase() throws SQLiteException {
        // Open database
        String myPath = DB_PATH + DB_NAME;
        myDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    /**
     * The scores for level "lvl" in "table" in the database is updated, or set to 0
     * if init is true.
     * @param table The name of the table from the database.
     * @param id The _id of the row to update.
     * @param lvl The difficulty level of the game (1-3)
     * @param change The change of the score
     * @param init Indicates whether we are initializing the score or not.
     */
    public void updateScore(String table, int id, int lvl, int change, boolean init) {
        // Get the old score first
        int scoreAttr = 7;  // holds for all games
        List<String> scoreData = getData(table, id, scoreAttr);

        int oldTmpScore = Integer.parseInt(scoreData.get((lvl * 2) - 1));
        int oldTotalScore = Integer.parseInt(scoreData.get(lvl * 2));

        if(init) {
            scoreData.set((lvl * 2) - 1, String.valueOf(0)); // tmp score
        }
        else if (oldTmpScore + change >= 0) {
            scoreData.set((lvl * 2) - 1, String.valueOf(oldTmpScore + change)); // tmp score
            scoreData.set(lvl * 2, String.valueOf(oldTotalScore + change)); // total score
        }
        else {
            return; // don't update score
        }

        // Remove old score
        deleteRow(table, id);

        // Add updated score
        insertRow(table, scoreData);
    }

    /**
     * "rowData" is inserted into "table" in the database.
     * @param table The name of the table from the database
     * @param rowData One row of data, according to "table".
     */
    public void insertRow (String table, List<String> rowData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + table + " VALUES(";
        for (int i = 0; i < rowData.size() - 1; i++)
            sql += rowData.get(i) + ",";
        sql += rowData.get(rowData.size() - 1) + ")";

        try {
            db.execSQL(sql);
        } catch (SQLiteException sqle) {
            Log.e("dbManager,insertRow()", sqle.getMessage());
        }
    }

    /**
     * The row with the _id "id" from "table" in the database is deleted.
     * @param table The name of the table from the database
     * @param id The _id of the row to update.
     */
    public void deleteRow (String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + table + " WHERE _id = " + String.valueOf(id);
        try {
            db.execSQL(sql);
        } catch (SQLiteException sqle) {
            Log.e("DbManager, removeRow()", sqle.getMessage());
        }
    }

    /**
     *
     * @param table The name of the table from the database
     * @param id The _id of the row to update.
     * @param attr The number of attributes "table" has
     * @return The row corresponding to "id". If that row does not
     *        exist the return value is a list of 0's.
     */
    public List<String> getData (String table, int id, int attr) {
        List<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + table + " WHERE _id = ?", new String[] {String.valueOf(id)});
        } catch (SQLiteException sqle) {
            Log.e("DbManager, getData()", sqle.getMessage());
        }

        // Maybe populate the data array
        if (cursor != null && cursor.moveToFirst()) {
            do {
                for (int i = 0; i < attr; i++)
                    data.add(cursor.getString(i));
            } while (cursor.moveToNext());
        }
        else {
            for (int i = 0; i < attr; i++)
                data.add(String.valueOf(0));
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return data;
    }

    /**
     * Specifically for the Questions table.
     * @return All the data (all questions and answers)
     *         from the Questions table from the database,
     *         returned as a list of lists of strings.
     */
    public List<List<String>> getAllQuestions () {
        List<List<String>> allQuestions = new ArrayList<List<String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int attr = 7;   // nr of attributes in the Questions table.
        try {
            cursor = db.rawQuery("SELECT * FROM Questions", null);
        } catch (SQLiteException sqle) {
            Log.e("DbManager,getAllQ", sqle.getMessage());
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                List<String> aQuestion = new ArrayList<String>();
                for (int i = 0; i < attr; i++)
                    aQuestion.add(cursor.getString(i));   // get one question and it's answers
                allQuestions.add(aQuestion);    // add that question to the allQuestions container
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return allQuestions;
    }

    /**
     * Fetch the current and total score.
     * @param lvl The lvl to fetch the scores from.
     * @param tableName The name of the table from this database,
     *                  to fetch the scores from.
     * @return   An array of string of length 2 containing
    //               the tmp score and the total score.
     */
    public String[] getScore(int lvl, String tableName){
        List<String> allSpellingScores = getData(tableName, 0, 7);
        String[] score = {
                allSpellingScores.get((lvl * 2) - 1),   // Tmp score
                allSpellingScores.get(lvl * 2)    // Total score
        };
        return score;
    }

    /**
     * Display the current and total score in a TextView.
     * @param lvl The current difficulty level.
     * @param tableName The name of the table from this database,
     *                  to fetch the scores from.
     * @param scoreView The TextView to display the scores in.
     */
    public void showScores(int lvl, String tableName, TextView scoreView) {
        String[] newScores = getScore(lvl, tableName);
        scoreView.setText("Stig : " + newScores[0] + "\t Heildarstig : " + newScores[1]);
    }

    /**
     * Set the current score to 0.
     * @param lvl The current difficulty level.
     * @param tableName The name of the table from this database,
     *                  to fetch the scores from.
     */
    public void initScores(int lvl, String tableName) {
        updateScore(tableName, 0, lvl, 0, true);
    }

    /**
     * Update the current and total score in the database,
     * relative to the change.
     * @param lvl The current difficulty level.
     * @param tableName The name of the table from this database,
     *                  to fetch the scores from.
     * @param change The change in score. Change is an integer.
     */
    public void saveScore(int lvl, String tableName, int change){
        System.out.println("1ST ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        updateScore(tableName, 0, lvl, change, false);
        System.out.println("2ND ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }


    /**
     * Setup the dbManager for the calling activity.
     * @param lvl The current difficulty level.
     * @param tableName The name of the table from this database,
     *                  to fetch the scores from.
     */
    public void initDbManager(int lvl, String tableName) {

        try {
            this.createDatabase();
        } catch (IOException ioe) {
            Log.e("initDbManager()", ioe.getMessage());
        }

        try {
            this.openDatabase();
        } catch (SQLiteException sqle) {
            Log.e("initDbManager()", sqle.getMessage());
        }

        initScores(lvl, tableName);

    }
    /**
     * Close this database.
     */
    @Override
    public synchronized void close() {
        if (myDb != null)
            myDb.close();

        super.close();

    }

    /**
     * Run when the database is created.
     * @param db This database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            copyDatabase();
        } catch (IOException ioe) {
            Log.e("dbManager,onCreate()", ioe.getMessage());
        }
    }

    /**
     *  Run when the version number of this database is changed.
     * @param db This database
     * @param oldVersion Number of the old version of the database.
     * @param newVersion Number of the new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {   // upgrade to version 2 of database
            // TODO: prevent users from losing their scores!
            try {
                copyDatabase();

                try {
                    this.createDatabase();
                } catch (IOException ioe) {
                    Log.e("initDbManager()", ioe.getMessage());
                }
                try {
                    this.openDatabase();
                } catch (SQLiteException sqle) {
                    Log.e("initDbManager()", sqle.getMessage());
                }

                //saveScore(2, "QuizScores", 2);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}