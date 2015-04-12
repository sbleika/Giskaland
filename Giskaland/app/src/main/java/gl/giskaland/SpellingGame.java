package gl.giskaland;


import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;


public class SpellingGame extends ActionBarActivity {

    // to make sure we only get one of each letter level 1 and level 2
    String[] SpellingOut = new String[7];
    // the first letter in the answer
    String IMGvalueLetter = "1";
    // to know what letter we had last in level 2
    int IMGvalueLetterNum = 0;
    // ther word in the image
    String IMGword;
    // the word in Icelandic
    String IslOrd;

    // to know where the right answer is
    Boolean[] SpellingOutBool = new Boolean[7];

    // the last outcome in the game before
    int LASTans = 0;
    // the next one
    int NEXTans = 0;
    // WHAT LEVEL ARE WE ON
    int lvl;
    // are we playing in icelandic or english
    boolean NotEnglish = true;
    // to clear the text if we press in level 2 and 3
    boolean buttonNotPressed = true;

    // DbManager for usage inside this activity
    DbManager dbManager;

    List<List<String>> allQuestions;
    int nrQuestions;

    // the buttons
    ImageButton button1, button2, button3, button4, button5, button6;

    String tableName = "SpellingScores";

    TextView scoreView;
    // the output text to let us know if we won
    Toast toast;

    /**
     * on opening the activity
     * @param savedInstanceState v
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        if(lvl == 1)setContentView(R.layout.activity_spelling_game);
        else setContentView(R.layout.activity_spelling_game_two);
        // hide the action bar
        getSupportActionBar().hide();

        scoreView = (TextView)findViewById(R.id.TextSpellingLevel1Score);
        if (lvl == 2 || lvl == 3) scoreView = (TextView)findViewById(R.id.TextSpellingLevel2Score);

        dbManager = new DbManager(this);

        dbManager.initDbManager(lvl, tableName);
        dbManager.showScores(lvl, tableName, scoreView);

        if (lvl == 3)
            allQuestions = dbManager.getAllInfo(2, "SpellingImgsEn");
        else
            allQuestions = dbManager.getAllInfo(2, "SpellingImgsIce");

        nrQuestions = allQuestions.size();

        makeRandom(); // start the game
    }

    /**
     * Generate a random number (integer).
     * @return A random integer x, where
     *         0 <= x <= nrQuestions - 1
     */
    public int randomIndex() {
        return (int)(Math.random()*nrQuestions);
    }

    /**
     * put new letters to the buttons and a new image to guess on
     */
    public void makeRandom(){
        if(lvl == 1)setUpLevelOne();
        if(lvl == 2)setUpLevelTwo();
        if(lvl == 3){
            NotEnglish = false;
            setUpLevelTwo();
        }
    }

    /**
     * set up a random img and  random answer wrong and one right for level 1
     */
    public void setUpLevelOne(){
        // reset all the global values
        //********************************************************************
        for (int i = 0; i < SpellingOutBool.length; i++){
            SpellingOutBool[i]=false;
        }
        for (int j = 0; j < SpellingOut.length; j++){
            SpellingOut[j] = "-1";
        }
        IMGvalueLetter = "-1";
        IMGvalueLetterNum = 0;

        //********************************************************************

        // set up the buttons
        //********************************************************************
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button1.setOnClickListener(checkIfRightAnsout);
        button2 = (ImageButton) findViewById(R.id.SpellingOut2);
        button2.setOnClickListener(checkIfRightAnsout);
        button3 = (ImageButton) findViewById(R.id.SpellingOut3);
        button3.setOnClickListener(checkIfRightAnsout);
        button4 = (ImageButton) findViewById(R.id.SpellingOut4);
        button4.setOnClickListener(checkIfRightAnsout);
        // make the buttons clickable
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        ImageButton IMG;
        IMG = (ImageButton) findViewById(R.id.IMGspelling);
        //********************************************************************

        // set the image to a random image
        setRandomnumIMG(IMG);
        // set random numbers to the 4 options
        setTheOptionsLevel1();
    }

    /**
     * set up a random img and  random answer wrong and one right  for level 2 and 3
     */
    public void setUpLevelTwo(){
        // reset variables
        for (int i = 0; i < SpellingOutBool.length; i++){
            SpellingOutBool[i]=false;
        }
        for (int j = 0; j < SpellingOut.length; j++){
            SpellingOut[j] = "-1";
        }
        IMGvalueLetter = "-1";
        IMGvalueLetterNum = 0;

        TextView IMGtext;
        IMGtext = (TextView) findViewById(R.id.OutPutText);
        if(NotEnglish) {
            IMGtext.setText("Hvernig skrifar þú...");
            IMGtext.setTextSize(70);
        }else {
            IMGtext.setText("Hvernig skrifar þú ... á ensku");
            IMGtext.setTextSize(60);
        }
        buttonNotPressed = true;

        // set up the buttons
        //********************************************************************
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button1.setOnClickListener(checkIfRightAnsout);
        button2 = (ImageButton) findViewById(R.id.SpellingOut2);
        button2.setOnClickListener(checkIfRightAnsout);
        button3 = (ImageButton) findViewById(R.id.SpellingOut3);
        button3.setOnClickListener(checkIfRightAnsout);
        button4 = (ImageButton) findViewById(R.id.SpellingOut4);
        button4.setOnClickListener(checkIfRightAnsout);
        button5 = (ImageButton) findViewById(R.id.SpellingOut5);
        button5.setOnClickListener(checkIfRightAnsout);
        button6 = (ImageButton) findViewById(R.id.SpellingOut6);
        button6.setOnClickListener(checkIfRightAnsout);
        // make the buttons clickable
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);

        ImageView IMG;
        IMG = (ImageView) findViewById(R.id.imageOFitem);

        Button newphoto;
        newphoto = (Button) findViewById(R.id.newphoto);
        newphoto.setOnClickListener(newphotolistner);
        //********************************************************************

        // set the image to a random image
        setRandomnumIMG(IMG);
        // set random numbers to the 9 options
        setTheOptionsLevel2();
    }
    /**
     * set new random numbers to the options and the correct answers
     */
    public void setTheOptionsLevel1(){

        // load in buttons
        //********************************************************************
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button2 = (ImageButton) findViewById(R.id.SpellingOut2);
        button3 = (ImageButton) findViewById(R.id.SpellingOut3);
        button4 = (ImageButton) findViewById(R.id.SpellingOut4);
        button1.setBackgroundResource(android.R.drawable.btn_default);
        button2.setBackgroundResource(android.R.drawable.btn_default);
        button3.setBackgroundResource(android.R.drawable.btn_default);
        button4.setBackgroundResource(android.R.drawable.btn_default);
        //********************************************************************

        // random number from 1 to 4
        int randomOut = (int) Math.ceil(Math.random()*4);

        SpellingOutBool[randomOut] = true;
        // set one of four to the correct value

        switch (randomOut) {
            case 1:
                setAnswer(button1);

                setRandom(button2);
                setRandom(button3);
                setRandom(button4);
                break;
            case 2:
                setAnswer(button2);

                setRandom(button1);
                setRandom(button3);
                setRandom(button4);
                break;
            case 3:
                setAnswer(button3);

                setRandom(button1);
                setRandom(button2);
                setRandom(button4);
                break;
            case 4:
                setAnswer(button4);

                setRandom(button1);
                setRandom(button2);
                setRandom(button3);
                break;
        }
    }

    /**
     * set new random numbers to the options and the correct answers
     */
    public void setTheOptionsLevel2(){
        // load in buttons
        //********************************************************************
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button2 = (ImageButton) findViewById(R.id.SpellingOut2);
        button3 = (ImageButton) findViewById(R.id.SpellingOut3);
        button4 = (ImageButton) findViewById(R.id.SpellingOut4);
        button5 = (ImageButton) findViewById(R.id.SpellingOut5);
        button6 = (ImageButton) findViewById(R.id.SpellingOut6);
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button1.setBackgroundResource(android.R.drawable.btn_default);
        button2.setBackgroundResource(android.R.drawable.btn_default);
        button3.setBackgroundResource(android.R.drawable.btn_default);
        button4.setBackgroundResource(android.R.drawable.btn_default);
        button5.setBackgroundResource(android.R.drawable.btn_default);
        button6.setBackgroundResource(android.R.drawable.btn_default);
        //********************************************************************

        // reset variables
        for (int i = 0; i < SpellingOutBool.length; i++){
            SpellingOutBool[i]=false;
        }

        // random number from 1 to 10
        int randomOut = (int) Math.ceil(Math.random()*6);

        // set one of 10 to the correct value
        SpellingOutBool[randomOut] = true;

        switch (randomOut) {
            case 1:
                setAnswer(button1);

                setRandom(button2);
                setRandom(button3);
                setRandom(button4);
                setRandom(button5);
                setRandom(button6);
                break;
            case 2:
                setAnswer(button2);

                setRandom(button1);
                setRandom(button3);
                setRandom(button4);
                setRandom(button5);
                setRandom(button6);
                break;
            case 3:
                setAnswer(button3);

                setRandom(button1);
                setRandom(button2);
                setRandom(button4);
                setRandom(button5);
                setRandom(button6);
                break;
            case 4:
                setAnswer(button4);

                setRandom(button1);
                setRandom(button2);
                setRandom(button3);
                setRandom(button5);
                setRandom(button6);
                break;
            case 5:
                setAnswer(button5);

                setRandom(button1);
                setRandom(button2);
                setRandom(button3);
                setRandom(button4);
                setRandom(button6);
                break;
            case 6:
                setAnswer(button6);

                setRandom(button1);
                setRandom(button2);
                setRandom(button3);
                setRandom(button4);
                setRandom(button5);
                break;
        }
    }

    /**
     *  set the rigth answer to one of the buttons
     * @param view v
     */
    public void setAnswer(ImageView view){
        //get the id of the button
        String ID = getID(view);
        //save the value of the answer to the rigth array place
        setValue(ID, IMGvalueLetter);
        //set the right image(the letter) to one of the buttons
        setIMG(IMGvalueLetter, view);
    }

    /**
     *  set a random wrong answer to one of the buttons
     * @param view v
     */
    public void setRandom(ImageView view){
        String letter = getRandomLetter();
        // make sure we only get the letters once
        while ( SpellingOut[1].equals(letter) ||
                SpellingOut[2].equals(letter) ||
                SpellingOut[3].equals(letter) ||
                SpellingOut[4].equals(letter) ||
                SpellingOut[5].equals(letter) ||
                SpellingOut[6].equals(letter)){
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
        switch (ID) {
            case "SpellingOut1":
                SpellingOut[1] = letter;
                break;
            case "SpellingOut2":
                SpellingOut[2] = letter;
                break;
            case "SpellingOut3":
                SpellingOut[3] = letter;
                break;
            case "SpellingOut4":
                SpellingOut[4] = letter;
                break;
            case "SpellingOut5":
                SpellingOut[5] = letter;
                break;
            case "SpellingOut6":
                SpellingOut[6] = letter;
                break;
        }
    }

    /**
     * returns a random letter
     * @return a random letter
     */
    public String getRandomLetter(){
        // ranom number from 0 to 31
        int randomNum;
        if(NotEnglish){
                randomNum = ((int) (Math.random()*32));
        }else   randomNum = ((int) (Math.random()*23));

        String randomLetter = Integer.toString(randomNum);
        String nestnum = Integer.toString(randomNum + 1);
        String alphabet;
        // all the letters

        if(NotEnglish){
            alphabet = "0 sa_1 saa_2 sae_3 sb_4 sd_5 sdd_6 se_7 see_8 sf_9 sg_10sh_11si_12sii_13sj_14sk_15sl_16sm_17sn_18so_19soo_20sou_21sp_22sr_23ss_24st_25sth_26su_27suu_28sv_29sx_30sy_31syy_32";
        }else {
            alphabet = "0 sa_1 sb_2 sd_3 se_4 sf_5 sg_6 sh_7 si_8 sj_9 sk_10sl_11sm_12sn_13so_14sp_15sr_16ss_17st_18su_19sv_20sx_21sy_22sw_23sc24";
            //
        }
        // tke one of the letters
        String letter;
        letter = alphabet.substring(alphabet.indexOf(randomLetter) + 2,alphabet.indexOf(nestnum) - 1);
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
        String ID;
        ID = viewId.substring(viewId.lastIndexOf('/') + 1);
        return ID;
    }

    /**
     * set a random image to the view
     * @param view g
     */
    public void setRandomnumIMG(ImageView view){
        while (NEXTans == LASTans) NEXTans = randomIndex();

        List<String> aQuestion = allQuestions.get(NEXTans);

        // Path of the img
        IMGword = aQuestion.get(0);
        IslOrd = aQuestion.get(1);
        // make the view have the random image
        int resID = getResources().getIdentifier(IMGword , "drawable", "gl.giskaland");

        // set the letter to the button
        view.setImageResource(resID);

        //set the fyrst letter of the thing on the image to IMGvalueletter
        if(IMGword.substring(0,1).equals("_")) {
            System.out.println("serisl stafur kemur her jejjjjjjjjjjjjjjjjjjjjjj");
            IMGvalueLetter = "s" + IMGword.charAt((IMGvalueLetterNum+1)) + IMGword.charAt((IMGvalueLetterNum + 2));
            IMGvalueLetterNum = IMGvalueLetterNum + 2;
        }
        else IMGvalueLetter = "s" + IMGword.charAt((IMGvalueLetterNum));

        LASTans = NEXTans;
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

    /**
     * append the letter we pressed to the text
     */
    public void Append(){
        // get textview
        TextView Output;
        Output = (TextView)findViewById(R.id.OutPutText);
        System.out.println(Output.getText());
        if(buttonNotPressed){
            if(IMGvalueLetter.length() > 2){
                System.out.println(IMGvalueLetter.substring(1,3));
                if(IMGvalueLetter.substring(1,3).equals("aa")){
                    Output.setText("Á");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ae")){
                    Output.setText("Æ");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ee")){
                    Output.setText("É");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ii")){
                    Output.setText("Í");
                }
                else if (IMGvalueLetter.substring(1,3).equals("oo")){
                    Output.setText("Ó");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ou")){
                    Output.setText("Ö");
                }
                else if (IMGvalueLetter.substring(1,3).equals("uu")){
                    Output.setText("Ú");
                }
                else if (IMGvalueLetter.substring(1,3).equals("yy")){
                    Output.setText("Ý");
                }
            }
            else Output.setText((IMGvalueLetter.substring(1,2)).toUpperCase());
        }else{
            if(IMGvalueLetter.length() > 2){
                System.out.println(IMGvalueLetter.substring(1,3));
                if(IMGvalueLetter.substring(1,3).equals("aa")){
                    Output.append("Á");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ae")){
                    Output.append("Æ");
                }
                else if (IMGvalueLetter.substring(1,3).equals("dd")){
                    Output.append("Ð");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ee")){
                    Output.append("É");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ii")){
                    Output.append("Í");
                }
                else if (IMGvalueLetter.substring(1,3).equals("oo")){
                    Output.append("Ó");
                }
                else if (IMGvalueLetter.substring(1,3).equals("ou")){
                    Output.append("Ö");
                }
                else if (IMGvalueLetter.substring(1,3).equals("uu")){
                    Output.append("Ú");
                }
                else if (IMGvalueLetter.substring(1,3).equals("yy")){
                    Output.append("Ý");
                }
            }
            else Output.append((IMGvalueLetter.substring(1,2)).toUpperCase());
        }
        buttonNotPressed = false;
    }

    /**
     * get the next letters
     */
    public void NextLetterForOptions(){
        IMGvalueLetterNum++;
        if(IMGword.length() > IMGvalueLetterNum){
            if(IMGword.charAt(IMGvalueLetterNum) == '_') {
                System.out.println("serisl stafur kemur her jejjjjjjjjjjjjjjjjjjjjjj");
                IMGvalueLetter = "s" + IMGword.charAt((IMGvalueLetterNum+1)) + IMGword.charAt((IMGvalueLetterNum + 2));
                IMGvalueLetterNum = IMGvalueLetterNum + 2;
            }
            else IMGvalueLetter = "s" + IMGword.charAt((IMGvalueLetterNum));


        }
        else {
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);
            button6.setEnabled(false);

            String RightAnswerText = "þu skrifaðir " + IslOrd.toUpperCase() + " rétt !!";

            toast = Toast.makeText(getApplicationContext(), RightAnswerText, Toast.LENGTH_SHORT);
            toast.show();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    makeRandom();// you have won
                }
            }, 3000);

        }
    }

    /**
     *
     */
    public void PutUp(){
        button1 = (ImageButton) findViewById(R.id.SpellingOut1);
        button2 = (ImageButton) findViewById(R.id.SpellingOut2);
        button3 = (ImageButton) findViewById(R.id.SpellingOut3);
        button4 = (ImageButton) findViewById(R.id.SpellingOut4);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        String RightAnswerText = ("Já " + IslOrd.substring(0,1).toUpperCase() + " er fyrsti stafurinn í " + IslOrd.toUpperCase());

        toast = Toast.makeText(getApplicationContext(), RightAnswerText, Toast.LENGTH_SHORT);
        toast.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                setUpLevelOne();//you have won!!!!!!!!!!!!!!
            }
        }, 3000);
    }

    /**
     * check if we have the right Answer
     * @param index the button we are checking
     */
    public void handleScore(int index, View v) {
        if (SpellingOutBool[index]) {
            v.setBackgroundResource(R.drawable.greenback);
            if (lvl > 1) Append();

             if(lvl == 1)PutUp();
             if(lvl == 2 || lvl == 3){
                NextLetterForOptions();
                setTheOptionsLevel2();
              }
              dbManager.saveScore(lvl, tableName, 2);
              dbManager.showScores(lvl, tableName, scoreView);

        }
        else {
            dbManager.saveScore(lvl, tableName, -1);
            dbManager.showScores(lvl, tableName, scoreView);
            // make button different
            v.setBackgroundResource(R.drawable.redback);
        }
    }

    /**
     * OnClickListener for Answer buttons
     */
    View.OnClickListener checkIfRightAnsout = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            String ID = getID(view);
            switch (ID) {
                case "SpellingOut1":
                    handleScore(1, view);
                    break;
                case "SpellingOut2":
                    handleScore(2, view);
                    break;
                case "SpellingOut3":
                    handleScore(3, view);
                    break;
                case "SpellingOut4":
                    handleScore(4, view);
                    break;
                case "SpellingOut5":
                    handleScore(5, view);
                    break;
                case "SpellingOut6":
                    handleScore(6, view);
                    break;
            }
        }
    };

    /**
     * OnClickListener for newphoto button
     */
    View.OnClickListener newphotolistner = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            dbManager.saveScore(lvl, tableName, -1);
            dbManager.showScores(lvl, tableName, scoreView);
            setUpLevelTwo();
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