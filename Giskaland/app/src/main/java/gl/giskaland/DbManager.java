package gl.giskaland;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    private String[] tables = {"Questions", "MathScores", "QuestionScores"};

    private static final int DB_VERSION = 1;

    public DbManager(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;

        String packageName = context.getPackageName();
        DB_PATH = "/data/data/" + packageName + "/databases/";
    }

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

        return db != null ? true : false;
    }

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

    public void openDatabase() throws SQLiteException {
        // Open database
        String myPath = DB_PATH + DB_NAME;
        myDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }


    /*
        Input :table is the name of the table from giskaland.db
               id is the _id of the row to update
               lvl is the difficulty level of the game (1-3)
               tmpScore is the score of the current game.
        Post : lvlXTmp in table in giskaland.db has been set to tmpScore
               and lvlXTotal has been increased by tmpScore.
               The number of rows in table are the same as before.
    */
    public void updateScore(String table, int id, int lvl, int tmpScore) {
        // Get the old score first
        int scoreAttr = 7;  // holds for all games
        List<String> scoreData = getData(table, id, scoreAttr);

        int oldTotalScore = Integer.parseInt(scoreData.get(lvl*2));

        scoreData.set((lvl * 2) - 1, String.valueOf(tmpScore)); // tmp score
        scoreData.set(lvl * 2, String.valueOf(oldTotalScore + tmpScore)); // total score

        // Remove old score
        deleteRow(table, id);

        // Add updated score
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO " + table + " VALUES(" +
                              scoreData.get(0) + "," + scoreData.get(1) + "," +
                              scoreData.get(2) + "," + scoreData.get(3) + "," +
                              scoreData.get(4) + "," + scoreData.get(5) + "," +
                              scoreData.get(6) + ")";
        try {
            db.execSQL(sql);
        } catch (SQLiteException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    /*
        Input : table is the name of the table from giskaland.db
                id is the _id of the row to delete
        Post :  The row with the _id id from table in giskaland.db
                has been deleted.
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

    /*
        Input : table is the name of the table from giskaland.db
                id is the _id of the row to get data from
                attr is the number of attributes table has.
        Return value :
                An ArrayList<String> containing the row corresponding
                to id.
                If that row does not exist the return value is an
                ArrayList<String> with attr number of String values of 0.
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
        cursor.close();
        db.close();

        return data;
    }

    @Override
    public synchronized void close() {
        if (myDb != null)
            myDb.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*nothing*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*nothing for now, we might have to upgrade in new iterations/releases*/
    }
}