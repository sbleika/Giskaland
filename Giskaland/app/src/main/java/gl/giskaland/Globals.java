package gl.giskaland;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tumsgis on 9.4.2015.
 */
public class Globals extends Application {

    public static boolean shouldUpgradeDb = false;

    public static ContentValues quizScoreValues;
    public static ContentValues mathScoreValues;
    public static ContentValues spellingScoreValues;

    public static void handleUpgrade(DbManager dbManager) {
        // Retrieve old scores.
        List<String> quizScores = dbManager.getData("QuizScores", 0, 7);
        List<String> mathScores = dbManager.getData("MathScores", 0, 7);
        List<String> spellingScores = dbManager.getData("SpellingScores", 0, 7);

        System.out.println("GETTING HEREEEEEEE");

        // Make sure the scores will find their place in the updated database.
        quizScoreValues.put("lvl2Total", quizScores.get(4));
        quizScoreValues.put("lvl3Total", quizScores.get(6));

        mathScoreValues.put("lvl1Total", mathScores.get(2));
        mathScoreValues.put("lvl2Total", mathScores.get(4));
        mathScoreValues.put("lvl3Total", mathScores.get(6));

        spellingScoreValues.put("lvl1Total", spellingScores.get(2));
        spellingScoreValues.put("lvl2Total", spellingScores.get(4));
        spellingScoreValues.put("lvl3Total", spellingScores.get(6));

        smuggleOldScores(dbManager);
    }

    public static void smuggleOldScores(DbManager dbManager) {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        db.update("QuizScores", Globals.quizScoreValues, "_id=0", null);
        db.update("MathScores", Globals.mathScoreValues, "_id=0", null);
        db.update("SpellingScores", Globals.spellingScoreValues, "_id=0", null);
        shouldUpgradeDb = false;
    }

    private static Globals singleton;

    public static Globals getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
