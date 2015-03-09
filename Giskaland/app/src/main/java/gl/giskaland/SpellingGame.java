package gl.giskaland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class SpellingGame extends ActionBarActivity {

    // to make sure we only get one of each letter
    String[] SpellingOut = new String[5];
    // the rigth answer
    String IMGvalue;

    // to know where the right answer is
    Boolean SpellingOut1 = false;
    Boolean SpellingOut2 = false;
    Boolean SpellingOut3 = false;
    Boolean SpellingOut4 = false;

    // the last outcome in the game before
    int LASTans;

    int lvl;

    // DbManager for usage inside this activity
    DbManager dbManager;

    /**
     * on opening the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_game);

        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        dbManager = new DbManager(this);
        initScores();
        showScores();

        makeRandom(); // start the game
    }

    /**
     * put new letters to the buttons and a new image to guess on
     */
    public void makeRandom(){
        // reset all the global values
        //********************************************************************
        SpellingOut[1] = "1";
        SpellingOut[2] = "2";
        SpellingOut[3] = "3";
        SpellingOut[4] = "4";
        IMGvalue = "-1";
        SpellingOut1 = false;
        SpellingOut2 = false;
        SpellingOut3 = false;
        SpellingOut4 = false;
        //********************************************************************

        // set up the buttons
        //********************************************************************
        ImageButton IBout1;
        IBout1 = (ImageButton) findViewById(R.id.SpellingOut1);
        IBout1.setOnClickListener(checkIfRightAnsout1);
        ImageButton IBout2;
        IBout2 = (ImageButton) findViewById(R.id.SpellingOut2);
        IBout2.setOnClickListener(checkIfRightAnsout2);
        ImageButton IBout3;
        IBout3 = (ImageButton) findViewById(R.id.SpellingOut3);
        IBout3.setOnClickListener(checkIfRightAnsout3);
        ImageButton IBout4;
        IBout4 = (ImageButton) findViewById(R.id.SpellingOut4);
        IBout4.setOnClickListener(checkIfRightAnsout4);
        ImageButton IMG;
        IMG = (ImageButton) findViewById(R.id.IMGspelling);
        //********************************************************************

        // set the image to a random image
        setRandomnumIMG(IMG);
        // set random numbers to the 4 options
        setTheOptions();
    }

    /**
     * set new random numbers to the options and the correct answers
     */
    public void setTheOptions(){

        // load in buttons
        //********************************************************************
        ImageButton IBout1;
        IBout1 = (ImageButton) findViewById(R.id.SpellingOut1);
        ImageButton IBout2;
        IBout2 = (ImageButton) findViewById(R.id.SpellingOut2);
        ImageButton IBout3;
        IBout3 = (ImageButton) findViewById(R.id.SpellingOut3);
        ImageButton IBout4;
        IBout4 = (ImageButton) findViewById(R.id.SpellingOut4);
        //********************************************************************

        // random number from 1 to 4
        int randomOut = (int) Math.ceil(Math.random()*4);

        // set one of four to the correct value
        if(randomOut == 1){
            setAnswer(IBout1);
            SpellingOut1 = true;
            setRandom(IBout4);
            setRandom(IBout2);
            setRandom(IBout3);
        }

        if(randomOut == 2){
            setAnswer(IBout2);
            SpellingOut2 = true;
            setRandom(IBout1);
            setRandom(IBout4);
            setRandom(IBout3);
        }

        if(randomOut == 3){
            setAnswer(IBout3);
            SpellingOut3 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout4);
        }

        if(randomOut == 4){
            setAnswer(IBout4);
            SpellingOut4 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout3);
        }
    }

    /**
     *  set the rigth answer to one of the buttons
     * @param view
     */
    public void setAnswer(ImageView view){
        //get the id of the button
        String ID = getID(view);
        //save the value of the answer to the rigth array place
        setValue(ID, IMGvalue);
        //set the rigth image(the letter) to one of the buttons
        setIMG(IMGvalue, view);
    }

    /**
     *  set a random wrong answer to one of the buttons
     * @param view
     */
    public void setRandom(ImageView view){
        String letter = getRandomLetter();
        // make sure we only get the letters once
        while ( SpellingOut[1].equals(letter) ||
                SpellingOut[2].equals(letter) ||
                SpellingOut[3].equals(letter) ||
                SpellingOut[4].equals(letter)){
            letter = getRandomLetter();
        }
        // get the id of the button we are working with
        String ID = getID(view);
        // save the value of the answer to the rigth array place
        setValue(ID, letter);
        // set the rigth image(the letter) to one of the buttons
        setIMG(letter, view);
    }

    /**
     * set a letter to the right place
     * @param ID id of the button
     * @param letter the letter to be set
     */
    public void setValue(String ID, String letter){
        if(ID.equals("SpellingOut1")) {
            SpellingOut[1] = letter;
        }
        else if (ID.equals("SpellingOut2")){
            SpellingOut[2] = letter;
        }
        else if (ID.equals("SpellingOut3")){
            SpellingOut[3] = letter;
        }
        else if (ID.equals("SpellingOut4")){
            SpellingOut[4] = letter;
        }
    }

    /**
     * returns a random letter
     * @return a random letter
     */
    public String getRandomLetter(){
        // ranom number from 0 to 31
        int randomNum = ((int) (Math.random()*32));

        String randomLetter = Integer.toString(randomNum);
        String nestnum = Integer.toString(randomNum + 1);
        // all the letters
        String alphabet = "0 sa_1 saa_2 sae_3 sb_4 sd_5 sdd_6 se_7 see_8 sf_9 sg_10sh_11si_12sii_13sj_14sk_15sl_16sm_17sn_18so_19soo_20sooo_21sp_22sr_23ss_24st_25sth_26su_27suu_28sv_29sx_30sy_31syy_32";
        // tke one of the letters
        String letter = alphabet.substring(	alphabet.indexOf(randomLetter) + 2,alphabet.indexOf(nestnum) - 1);
        //System.out.println(letter);
        return letter;
    }

    /**
     * to get a id of a view
     * @param view the button id
     * @return the id of the button
     */
    public String getID(View view){
        String viewId = view.getResources().getResourceName(view.getId());
        String ID = viewId.substring(viewId.lastIndexOf('/') + 1);
        return ID;
    }


    /**
     * set a random image to the view
     * @param view
     */
    public void setRandomnumIMG(ImageView view){
        int numberOFimages = 5;
        // random image from 0 to 5
        int randomNum = ((int) Math.ceil(Math.random()*numberOFimages));
        // make sure we get new image
        while (LASTans == randomNum){
            randomNum = ((int) Math.ceil(Math.random()*numberOFimages));
        }
        //make it the lastans so we dont get it next time
        LASTans = randomNum;
        //make it string and one number higher
        String randomLetter = Integer.toString(randomNum);
        String nextNUMafter = Integer.toString(randomNum + 1);
        // all the images that are avalible
        String imagies = "1 api_2 balls_3 line_4 hex_5 ring_6";
        // take one of the names from imagies
        String letter = imagies.substring(imagies.indexOf(randomLetter) + 2, imagies.indexOf(nextNUMafter) - 1);
        // make the view have the random image
        int resID = getResources().getIdentifier(letter , "drawable", "gl.giskaland");
        // set the letter to the button
        view.setImageResource(resID);
        //set the fyrst letter of the thing on the image to IMGvalue
        IMGvalue = "s" + letter.substring(0,1);
    }

    /**
     * set the image to the button
     * @param randomLetter a name of the letter to be used
     * @param view the view
     */
    public void setIMG(String randomLetter, ImageView view){
        int resID = getResources().getIdentifier(randomLetter , "drawable", "gl.giskaland");
        view.setImageResource(resID);
    }

    public void saveScore(int change){
        dbManager.updateScore("SpellingScores", 0, lvl, change, false);
    }

    // Return value : An array of string of length 2 containing
    //               the tmp score and the total score.
    public String[] getScore(){
        List<String> allSpellingScores = dbManager.getData("SpellingScores", 0, 7);
        String[] score = {
                allSpellingScores.get((lvl * 2) - 1),   // Tmp score
                allSpellingScores.get(lvl * 2)    // Total score
        };
        return score;
    }

    public void initScores() {
        dbManager.updateScore("SpellingScores", 0, lvl, 0, true);
    }

    public void showScores() {
        String[] newScores = getScore();
        TextView scoreView = (TextView)findViewById(R.id.TextSpellingLevel1Score);
        scoreView.setText("Stig : " + newScores[0] + "\t Heildarstig : " + newScores[1]);
    }

    public void handleScore(int index) {
        Boolean[] allSpellingOut = {SpellingOut1, SpellingOut2, SpellingOut3, SpellingOut4};
        if (allSpellingOut[index]) {
            makeRandom();
            saveScore(2);
        }
        else {
            saveScore(-1);
        }
        showScores();
    }

    /**
     *
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
        getMenuInflater().inflate(R.menu.menu_spelling_level_1, menu);
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