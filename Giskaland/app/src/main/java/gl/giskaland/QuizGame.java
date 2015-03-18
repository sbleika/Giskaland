package gl.giskaland;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class QuizGame extends ActionBarActivity {

    // to know what level we are working with
    int lvl;

    DbManager dbManager;
    List<List<String>> allQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_level_3);
        lvl = 3;
        initDbManager();
        showScores(lvl);
        saveScore(2);

        // Testing : print out all the questions
        allQuestions = dbManager.getAllQuestions();
        for (List<String> aQuestion : allQuestions)
            for (String elem : aQuestion)
                System.out.println(elem);
    }

    public void initDbManager() {
        dbManager = new DbManager(this);

        try {
            dbManager.createDatabase();
        } catch (IOException ioe) {
            Log.e("initDbManager()", ioe.getMessage());
        }

        try {
            dbManager.openDatabase();
        } catch (SQLiteException sqle) {
            Log.e("initDbManager()", sqle.getMessage());
        }

        initScores();
    }

    public void saveScore(int change){
        dbManager.updateScore("QuizScores", 0, lvl, change, false);
    }

    // Return value : An array of string of length 2 containing
    //               the tmp score and the total score.
    public String[] getScore(){
        List<String> allSpellingScores = dbManager.getData("QuizScores", 0, 7);
        String[] score = {
                allSpellingScores.get((lvl * 2) - 1),   // Tmp score
                allSpellingScores.get(lvl * 2)    // Total score
        };
        return score;
    }

    public void initScores() {
        dbManager.updateScore("QuizScores", 0, lvl, 0, true);
    }

    public void showScores(int lvl) {
        String[] newScores = getScore();

        TextView scoreView = (TextView)findViewById(R.id.TextQuizLevel3Score);

        scoreView.setText("Stig : " + newScores[0] + "\t Heildarstig : " + newScores[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
