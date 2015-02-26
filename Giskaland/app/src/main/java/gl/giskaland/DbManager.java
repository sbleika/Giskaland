package gl.giskaland;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tumsgis on 26.2.2015.
 *
 * DbManager.
 * Currently including a table for Questions and a
 * method to add new questions.
 */
public class DbManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "giskalandDb";
    private static final int DB_VERSION = 1;

    private static final String TABLE_QUESTIONS = "Questions";

    // Database attributes.
    // private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";  // The right option

    // Answer options a-d
    private static final String KEY_OPTA = "opta";
    private static final String KEY_OPTB = "optb";
    private static final String KEY_OPTC = "optc";
    private static final String KEY_OPTD = "optd";

    public DbManager (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @ Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUESTIONS = ""  +
                "CREATE TABLE IF NOT EXISTS Questions ("      +
                "question varhar(300),"         +
                "answer varchar(10),"           +
                "opta varchar(100),"            +
                "optb varchar(100),"            +
                "optc varchar(100),"            +
                "optd varchar(100),"            +
                "PRIMARY KEY(question)"         +
                ");";

        db.execSQL(CREATE_TABLE_QUESTIONS);
    }

    // Inputs: options is a String array of length 4, containing the
    //         answering options.
    public void addQuestions(String question, String answer, String[] options) {

        if (options.length != 4)
            throw new IllegalArgumentException("Number of options must be 4.");

        // Add reference to the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Handler for inuput values
        ContentValues values = new ContentValues();

        values.put(KEY_QUESTION, question);
        values.put(KEY_ANSWER, answer);
        values.put(KEY_OPTA, options[0]);
        values.put(KEY_OPTB, options[1]);
        values.put(KEY_OPTC, options[2]);
        values.put(KEY_OPTD, options[3]);

        db.insert(TABLE_QUESTIONS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // Drop older table and make a new version that includes the new updates.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }
}
