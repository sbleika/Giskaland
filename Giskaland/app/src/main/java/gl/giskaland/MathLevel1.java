package gl.giskaland;

import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;


public class MathLevel1 extends ActionBarActivity {

    // to make sure we only get one of each
    int IBout1value;
    int IBout2value;
    int IBout3value;
    int IBout4value;

    int IMGvalue;

    // to know where the right answer is
    Boolean isIBout1 = false;
    Boolean isIBout2 = false;
    Boolean isIBout3 = false;
    Boolean isIBout4 = false;

    int lvl;

    // DbManager for usage inside this activity
    DbManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_level_1);

        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        initDbManager();

        makeRandom(); // new numbers Restart
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
        showScores();
    }

    /**
     * put new numbers to the buttons
     */
    public void makeRandom(){
        // reset all the global values
        //********************************************************************

        IBout1value = -1;
        IBout2value = -1;
        IBout3value = -1;
        IBout4value = -1;
        IMGvalue = -1;
        isIBout1 = false;
        isIBout2 = false;
        isIBout3 = false;
        isIBout4 = false;
        //********************************************************************

        // set up the buttons
        //********************************************************************
        ImageButton IBout1;
        IBout1 = (ImageButton) findViewById(R.id.IBout1);
        IBout1.setOnClickListener(checkIfRightAnsout1);
        ImageButton IBout2;
        IBout2 = (ImageButton) findViewById(R.id.IBout2);
        IBout2.setOnClickListener(checkIfRightAnsout2);
        ImageButton IBout3;
        IBout3 = (ImageButton) findViewById(R.id.IBout3);
        IBout3.setOnClickListener(checkIfRightAnsout3);
        ImageButton IBout4;
        IBout4 = (ImageButton) findViewById(R.id.IBout4);
        IBout4.setOnClickListener(checkIfRightAnsout4);
        ImageButton IMG;
        IMG = (ImageButton) findViewById(R.id.IMG);
        //********************************************************************

        setRandomnumIMG(IMG);

        // set random numbers to the four options
        setTheOptions();
    }

    /**
     * set new random numbers to the options and the correct answers
     */
    public void setTheOptions(){

        // load in buttons
        //********************************************************************
        ImageButton IBout1;
        IBout1 = (ImageButton) findViewById(R.id.IBout1);
        ImageButton IBout2;
        IBout2 = (ImageButton) findViewById(R.id.IBout2);
        ImageButton IBout3;
        IBout3 = (ImageButton) findViewById(R.id.IBout3);
        ImageButton IBout4;
        IBout4 = (ImageButton) findViewById(R.id.IBout4);
        //********************************************************************

        // random number from 1 to 4
        int randomOut = (int) Math.ceil(Math.random()*4);

        // set one of four to the correct value
        if(randomOut == 1){
            setAnswer(IBout1);
            isIBout1 = true;
            setRandom(IBout4);
            setRandom(IBout2);
            setRandom(IBout3);
        }

        if(randomOut == 2){
            setAnswer(IBout2);
            isIBout2 = true;
            setRandom(IBout1);
            setRandom(IBout4);
            setRandom(IBout3);
        }

        if(randomOut == 3){
            setAnswer(IBout3);
            isIBout3 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout4);
        }

        if(randomOut == 4){
            setAnswer(IBout4);
            isIBout4 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout3);
        }
    }

    /**
     *Setting correct value to the button used to call the function
     * @param view
     */
    public void setAnswer(ImageView view){
        String viewId = view.getResources().getResourceName(view.getId());
        String ID = viewId.substring(viewId.lastIndexOf('/') + 1);

        if(ID.equals("IBout1")) {
            isIBout1 = true;
            IBout1value = IMGvalue;
        }
        else if (ID.equals("IBout2")){
            isIBout2 = true;
            IBout2value = IMGvalue;
        }
        else if (ID.equals("IBout3")){
            isIBout3 = true;
            IBout3value = IMGvalue;
        }
        else if (ID.equals("IBout4")){
            isIBout4 = true;
            IBout4value = IMGvalue;
        }

        setIMG(IMGvalue, view);
    }

    /**
     *Random numbers
     * @param view
     */
    public void setRandom(ImageView view){
        // random from 0 to 10
        int randomNum = ((int) (Math.random()*11));
        while ( IBout1value == randomNum ||
                IBout2value == randomNum ||
                IBout3value == randomNum ||
                IBout4value == randomNum){
            //System.out.println(randomNum);
            randomNum = ((int)(Math.random()*11));
        }

        String viewId = view.getResources().getResourceName(view.getId());
        String ID = viewId.substring(viewId.lastIndexOf('/') + 1);
        //checking randomness
        if(ID.equals("IBout1")) {
            IBout1value = randomNum;
        }
        else if (ID.equals("IBout2")){
            IBout2value = randomNum;
        }
        else if (ID.equals("IBout3")){
            IBout3value = randomNum;
        }
        else if (ID.equals("IBout4")){
            IBout4value = randomNum;
        }

        setIMG(randomNum, view);
    }

    /**
     *pictures and correct answers for counting
     * @param view
     */
    public void setRandomnumIMG(ImageView view){
        // random from 0 to 10
        int IMGnum = ((int) Math.ceil(Math.random()*4));
        String viewId = view.getResources().getResourceName(view.getId());
        String ID = viewId.substring(viewId.lastIndexOf('_') + 1);
        System.out.println(ID);
        if (IMGnum == 1) {
            view.setImageResource(R.drawable.img_1_line_2);
            IMGvalue = 2;
        }
        else if (IMGnum == 2) {
            view.setImageResource(R.drawable.img_2_ring_3);
            IMGvalue = 3;
        }
        else if (IMGnum == 3) {
            view.setImageResource(R.drawable.img_3_balls_5);
            IMGvalue = 5;
        }
        else if (IMGnum == 4) {
            view.setImageResource(R.drawable.img_4_hex_6);
            IMGvalue = 6;
        }

    }

    /**
     *
     * @param randomNum
     * @param view
     */
    public void setIMG(int randomNum, ImageView view){
        System.out.println("randim in set img");
        System.out.println(randomNum);
        if (randomNum == 0) {
            view.setImageResource(R.drawable.zero);
        }
        else if (randomNum == 1) {
            view.setImageResource(R.drawable.one);
        }
        else if (randomNum == 2) {
            view.setImageResource(R.drawable.two);
        }
        else if (randomNum == 3) {
            view.setImageResource(R.drawable.tree);
        }
        else if (randomNum == 4) {
            view.setImageResource(R.drawable.four);
        }
        else if (randomNum == 5) {
            view.setImageResource(R.drawable.five);
        }
        else if (randomNum == 6) {
            view.setImageResource(R.drawable.six);
        }
        else if (randomNum == 7) {
            view.setImageResource(R.drawable.seven);
        }
        else if (randomNum == 8) {
            view.setImageResource(R.drawable.eigth);
        }
        else if (randomNum == 9) {
            view.setImageResource(R.drawable.nine);
        }
        else if (randomNum == 10) {
            view.setImageResource(R.drawable.ten);
        }
    }

    public void saveScore(int change){
        dbManager.updateScore("MathScores", 0, lvl, change, false);
    }

    // Return value : An array of string of length 2 containing
    //               the tmp score and the total score.
    public String[] getScore(){
        List<String> allSpellingScores = dbManager.getData("MathScores", 0, 7);
        String[] score = {
                allSpellingScores.get((lvl * 2) - 1),   // Tmp score
                allSpellingScores.get(lvl * 2)    // Total score
        };
        return score;
    }

    public void initScores() {
        dbManager.updateScore("MathScores", 0, lvl, 0, true);
    }

    public void showScores() {
        String[] newScores = getScore();
        TextView scoreView = (TextView)findViewById(R.id.TextMathLevel1score);
        scoreView.setText("Stig : " + newScores[0] + "\t Heildarstig : " + newScores[1]);
    }

    public void handleScore(int index) {
        Boolean[] allMathOut = {isIBout1, isIBout2, isIBout3, isIBout4};
        if (allMathOut[index]) {
            makeRandom();
            saveScore(2);
        }
        else {
            saveScore(-1);
        }
        showScores();
    }
    /**
     *checking for correct answer and adding to score
     */
    View.OnClickListener checkIfRightAnsout1 = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            handleScore(0);
        }
    };

    /**
     *
     */
    View.OnClickListener checkIfRightAnsout2 = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            handleScore(1);
        }
    };

    /**
     *
     */
    View.OnClickListener checkIfRightAnsout3 = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            handleScore(2);
        }
    };

    /**
     *
     */
    View.OnClickListener checkIfRightAnsout4 = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            handleScore(3);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_math_level_1, menu);
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