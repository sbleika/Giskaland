package gl.giskaland;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class QuizGame extends ActionBarActivity {

    // to know what level we are working with
    int lvl;

    DbManager dbManager;
    List<List<String>> allQuestions;
    int nrQuestions;

    Button optaButton, optbButton, optcButton, optdButton;

    int correctOptionIndex; //3, 4, 5, 6 (which could be mapped to a, b, c, d)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_level_3);

        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        initDbManager();
        showScores(lvl);
        allQuestions = dbManager.getAllQuestions();
        nrQuestions = allQuestions.size();

        newQuestion();
    }

    public void newQuestion() {
        ProgressDialog pDialog = ProgressDialog.show(
                QuizGame.this, "Giskaland", "Sækir nýja spurningu...", true
                );

        int ind = randomIndex();
        List<String> aQuestion = allQuestions.get(ind);

        // String handlers for the question and it's answer options.
        String question = aQuestion.get(1);
        String correctOpt = aQuestion.get(2);
        String opta = aQuestion.get(3);
        String optb = aQuestion.get(4);
        String optc = aQuestion.get(5);
        String optd = aQuestion.get(6);

        // Handlers for the UI things that are to display the question
        // and it's answering options.
        TextView qText = (TextView)findViewById(R.id.QuestionTextLvl3);
        optaButton = (Button)findViewById(R.id.optaButton);
        optbButton = (Button)findViewById(R.id.optbButton);
        optcButton = (Button)findViewById(R.id.optcButton);
        optdButton = (Button)findViewById(R.id.optdButton);

        // Set the text for the UI things.
        qText.setText(question);
        optaButton.setText(opta);
        optbButton.setText(optb);
        optcButton.setText(optc);
        optdButton.setText(optd);

        try {
            correctOptionIndex = aQuestion.lastIndexOf(correctOpt);
        } catch (Exception e) {
            Log.e("QuizGame, newQuestion()", e.getMessage());
            Log.e("QuizGame, newQuestion()", "The correct option does not match with any of the other options.");
        }

        setupButtons();

        pDialog.dismiss();
    }

    public void setupButtons() {
        optaButton.setOnClickListener(evaluateGuess);
        optbButton.setOnClickListener(evaluateGuess);
        optcButton.setOnClickListener(evaluateGuess);
        optdButton.setOnClickListener(evaluateGuess);
    }

    Button.OnClickListener evaluateGuess = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            int optIndex = 0; // default, won't result in anything
            switch (view.getId()) {
                case R.id.optaButton:
                    optIndex = 3;
                    break;
                case R.id.optbButton:
                    optIndex = 4;
                    break;
                case R.id.optcButton:
                    optIndex = 5;
                    break;
                case R.id.optdButton:
                    optIndex = 6;
                    break;
            }
            // Error checking
            if (optIndex == 0){
                Log.e("QuizGame, evaluateGuess", "No correct option available");
                return;
            }

            // Checking if correct the correct option has been chosen.
            if (optIndex == correctOptionIndex) {
                saveScore(2);
                showScores(lvl);
                newQuestion();
            }
            else {
                saveScore(-1);
                showScores(lvl);
            }
        }
    };

    public int randomIndex() {
        Random rand = new Random();
        int max = nrQuestions;
        int min = 0;
        int randNum = rand.nextInt((max - min) + 1) + min;
        return randNum;
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
