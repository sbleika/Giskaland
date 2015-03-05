package gl.giskaland;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

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

    // Pre : The Questions table exists in the database.
    // Return value : An array of Strings containing all the
    //                elements from the Questions table that have the
    //                _id id.
    public List<String> getQuestion(int id) {
        List<String> data = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM Questions", null);
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("PAST THE CURSOR");

        // Maybe populate the data array
        if (cursor != null && cursor.moveToFirst()) {
            do {
                for (int i = 0; i < 7; i++)
                    data.add(cursor.getString(i));
            } while (cursor.moveToNext());
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
    public void onCreate(SQLiteDatabase db) {/*nothing*/}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {/*nothing*/}
}