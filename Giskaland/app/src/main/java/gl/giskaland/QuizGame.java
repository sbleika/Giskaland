package gl.giskaland;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by tumsgis
 *
 * QuizGame.
 * A QuizGame for levels 2 and 3.
 */
public class QuizGame extends ActionBarActivity {

    int lvl; // The lvl we're currently in.
    DbManager dbManager;
    List<List<String>> allQuestions;
    int nrQuestions;
    Button optaButton, optbButton, optcButton, optdButton;
    int correctOptionIndex; //3, 4, 5, 6 (which could be mapped to a, b, c, d)
    String tableName = "QuizScores"; // table name from the database.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);
        getSupportActionBar().hide();
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        dbManager = new DbManager(this);
        //if (Globals.shouldUpgradeDb) Globals.handleUpgrade(dbManager);

        dbManager.initDbManager(lvl, tableName);
        dbManager.showScores(lvl, tableName, (TextView)findViewById(R.id.TextQuizLevel3Score));

        // Fetch all the questions for ease of access later on.
        allQuestions = dbManager.getAllInfo(7, "Questions");
        nrQuestions = allQuestions.size();

        newQuestion();
    }


    /**
     * A new random question is generated from the question bank.
     * The question itself is displayed in a TextView and the options
     * are displayed on buttons.
     */
    public void newQuestion() {

        //
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

    /**
     * Event listeners are activated for the answering options buttons.
     */
    public void setupButtons() {
        optaButton.setOnClickListener(evaluateGuess);
        optbButton.setOnClickListener(evaluateGuess);
        optcButton.setOnClickListener(evaluateGuess);
        optdButton.setOnClickListener(evaluateGuess);
    }

    /**
     * Button events handled -> Guesses evaluated.
     */
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
                // Update the score
                dbManager.saveScore(lvl, tableName, 2);
                dbManager.showScores(lvl, tableName, (TextView) findViewById(R.id.TextQuizLevel3Score));
                // Then generate a new question
                newQuestion();
            }
            else {
                // Update the score.
                dbManager.saveScore(lvl, tableName, -1);
                dbManager.showScores(lvl, tableName, (TextView) findViewById(R.id.TextQuizLevel3Score));
            }
        }
    };

    /**
     * Generate a random number (integer).
     * @return A random integer x, where
     *         0 <= x <= nrQuestions - 1
     */
    public int randomIndex() {
        Random rand = new Random();
        int max = nrQuestions - 1;
        int min = 0;
        int randNum = rand.nextInt((max - min) + 1) + min;
        return randNum;
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
