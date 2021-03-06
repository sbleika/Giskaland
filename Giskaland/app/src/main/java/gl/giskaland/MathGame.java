package gl.giskaland;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.media.MediaPlayer;

public class MathGame extends ActionBarActivity {
    // to know what level we are working with
    int lvl;

    // the numbers that we are working with
    int IBnum1value;
    int IBnum2value;
    // the right number of items for level 1
    int IMGvalue;
    //the answer from last game to not get the same
    int LASTans = 0;
    int NEXTans = 0;
    // to make sure we only get one of each
    int IBout1value;
    int IBout2value;
    int IBout3value;
    int IBout4value;
    // to know if we used plus or minus or multiply or divide
    Boolean Multiply;
    Boolean Divide;
    Boolean Plus;
    Boolean Minus;
    // to know where the right answer is
    Boolean isIBout1 = false;
    Boolean isIBout2 = false;
    Boolean isIBout3 = false;
    Boolean isIBout4 = false;

    //the last number used in calculations
    int last_num;

    // DbManager for usage inside this activity
    DbManager dbManager;

    // the name of the images fer level 1
    List<List<String>> allQuestions;
    int nrQuestions = 0;

    String tableName = "MathScores";

    TextView scoreView;
    TextView quiz;
    String Answer;
    String RightAnswerText;
    Toast toast;

    /**
     * on opening the activity
     * @param savedInstanceState s
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");
        // get the right layout according to level
        if(lvl == 1)
            setContentView(R.layout.activity_math_level_1);
        else if (lvl == 2)
            setContentView(R.layout.activity_math_level_2);
        else if (lvl == 3)
            setContentView(R.layout.activity_math_level_3);
        getSupportActionBar().hide();

        scoreView = (TextView)findViewById(R.id.TextMathLevel1score);
        if (lvl == 2)
            scoreView = (TextView)findViewById(R.id.TextMathLevel2score);
        else if (lvl == 3)
            scoreView = (TextView)findViewById(R.id.TextMathLevel3score);

        dbManager = new DbManager(this);
        // get all the images for level 1
        allQuestions = dbManager.getAllInfo(2, "MathImgs");
        nrQuestions = allQuestions.size();

        dbManager.initDbManager(lvl, tableName);
        dbManager.showScores(lvl, tableName, scoreView);

        // Set up buttons for level 3
        if(lvl == 3)SetUpButtons();

        makeRandom(); // new numbers for the games
    }

    /**
     * set the buttons for math level 3
     */
    public void SetUpButtons(){
        Button reset;
        reset = (Button) findViewById(R.id.Newbutton);
        reset.setOnClickListener(rest);
        Button clear;
        clear = (Button) findViewById(R.id.Clearbutton);
        clear.setOnClickListener(clearnum);
        Button Answer;
        Answer = (Button) findViewById(R.id.Answerbutton);
        Answer.setOnClickListener(AnswerQ);

        // set up the buttons
        //********************************************************************
        ImageButton zero;
        zero = (ImageButton) findViewById(R.id.zerobutton_0);
        zero.setOnClickListener(Numlistner);
        ImageButton one;
        one = (ImageButton) findViewById(R.id.onebutton_1);
        one.setOnClickListener(Numlistner);
        ImageButton two;
        two = (ImageButton) findViewById(R.id.twobutton_2);
        two.setOnClickListener(Numlistner);
        ImageButton tree;
        tree = (ImageButton) findViewById(R.id.treebutton_3);
        tree.setOnClickListener(Numlistner);
        ImageButton four;
        four = (ImageButton) findViewById(R.id.fourbutton_4);
        four.setOnClickListener(Numlistner);
        ImageButton five;
        five = (ImageButton) findViewById(R.id.fivebutton_5);
        five.setOnClickListener(Numlistner);
        ImageButton six;
        six = (ImageButton) findViewById(R.id.sixbutton_6);
        six.setOnClickListener(Numlistner);
        ImageButton seven;
        seven = (ImageButton) findViewById(R.id.sevenbutton_7);
        seven.setOnClickListener(Numlistner);
        ImageButton eigth;
        eigth = (ImageButton) findViewById(R.id.eigthbutton_8);
        eigth.setOnClickListener(Numlistner);
        ImageButton nine;
        nine = (ImageButton) findViewById(R.id.ninebutton_9);
        nine.setOnClickListener(Numlistner);

        zero.setBackgroundResource(android.R.drawable.btn_default);
        one.setBackgroundResource(android.R.drawable.btn_default);
        two.setBackgroundResource(android.R.drawable.btn_default);
        tree.setBackgroundResource(android.R.drawable.btn_default);
        four.setBackgroundResource(android.R.drawable.btn_default);
        five.setBackgroundResource(android.R.drawable.btn_default);
        six.setBackgroundResource(android.R.drawable.btn_default);
        seven.setBackgroundResource(android.R.drawable.btn_default);
        eigth.setBackgroundResource(android.R.drawable.btn_default);
        nine.setBackgroundResource(android.R.drawable.btn_default);

        one.setEnabled(true);
        two.setEnabled(true);
        tree.setEnabled(true);
        four.setEnabled(true);
        five.setEnabled(true);
        six.setEnabled(true);
        seven.setEnabled(true);
        eigth.setEnabled(true);
        nine.setEnabled(true);
        zero.setEnabled(true);

        clear.setEnabled(true);
        Answer.setEnabled(true);
        reset.setEnabled(true);
        //********************************************************************
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
     * make the games
     */
    public void makeRandom(){
        // reset all the global values level 1 and 2
        //********************************************************************
        IMGvalue = -1;
        IBnum1value = -1;
        IBnum2value = -1;
        IBout1value = -1;
        IBout2value = -1;
        IBout3value = -1;
        IBout4value = -1;
        Plus = false;
        Minus = false;
        isIBout1 = false;
        isIBout2 = false;
        isIBout3 = false;
        isIBout4 = false;
        // reset all the global values for level 3
        //********************************************************************
        Multiply = false;
        Divide = false;
        //********************************************************************

        if (lvl==1)SetUplevel1();
        if (lvl==2)SetUplevel2();
        if (lvl==3)SetUplevel3();
    }

    /**
     * get a random image for the game and set the options
     */
    public void SetUplevel1(){
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
        IBout1.setEnabled(true);
        IBout2.setEnabled(true);
        IBout3.setEnabled(true);
        IBout4.setEnabled(true);

        IBout1.setBackgroundResource(android.R.drawable.btn_default);
        IBout2.setBackgroundResource(android.R.drawable.btn_default);
        IBout3.setBackgroundResource(android.R.drawable.btn_default);
        IBout4.setBackgroundResource(android.R.drawable.btn_default);

        //********************************************************************
        // set a random img to be used
        setRandomnumIMG(IMG);

        // set random numbers to the four options
        setTheOptions(IMGvalue);
    }

    /**
     * set a random problem with plus or minus to solve and the options
     */
    public void SetUplevel2(){
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
        IBout1.setEnabled(true);
        IBout2.setEnabled(true);
        IBout3.setEnabled(true);
        IBout4.setEnabled(true);

        IBout1.setBackgroundResource(android.R.drawable.btn_default);
        IBout2.setBackgroundResource(android.R.drawable.btn_default);
        IBout3.setBackgroundResource(android.R.drawable.btn_default);
        IBout4.setBackgroundResource(android.R.drawable.btn_default);

        //********************************************************************

        TextView quiz;
        quiz = (TextView) findViewById(R.id.QuestionText);
        quiz.setTextSize(100);
        quiz.setText(" ");

        // set random number from one to nine to the two numbers to calculate
        setRandomnum1(quiz);

        // set the operator to plus or minus
        //********************************************************************
        double randomOP = (int) Math.ceil(Math.random()*2);
        if (randomOP == 1) {
            quiz.append(" + ");
            Plus = true;
            Minus = false;
        }
        else if (randomOP == 2) {
            quiz.append(" - ");
            Plus = false;
            Minus = true;
        }
        //********************************************************************

        setRandomnum2LevelTwo(quiz);
        // make the calculation
        int num = -1;
        if (Minus)num = IBnum1value - IBnum2value;
        if (Plus)num = IBnum1value + IBnum2value;
        Answer = String.valueOf(num);

        // set random numbers to the 4 options
        setTheOptions(num);
    }

    /**
     * set a random problem with multiply or divide
     */
    public void SetUplevel3(){
        SetUpButtons();
        quiz = (TextView) findViewById(R.id.QuestionText);
        quiz.setTextSize(100);
        quiz.setText(" ");
        ClearText();

        // set the operator to
        //********************************************************************
        int randomOP = (int) Math.ceil(Math.random()*2);
        if (randomOP == 1) {
            //multiply
            Multiply = true;
            Divide = false;
        }
        else if (randomOP == 2) {
            //DIVIDE
            Multiply = false;
            Divide = true;
        }
        //********************************************************************
        // set random number from one to nine to the first number to calculate
        setRandomnum1(quiz);

        if(Multiply)quiz.append(" * ");
        if(Divide)  quiz.append(" / ");

        // set random number that will work with the first number
        setRandomnum2LevelThree(quiz);
    }

    /**
     *Setting correct value to the button used to call the function
     * @param view v
     */
    public void setAnswer(ImageView view, int num){
        String ID = getID(view);

        switch (ID) {
            case "IBout1":
                isIBout1 = true;
                IBout1value = num;
                break;
            case "IBout2":
                isIBout2 = true;
                IBout2value = num;
                break;
            case "IBout3":
                isIBout3 = true;
                IBout3value = num;
                break;
            case "IBout4":
                isIBout4 = true;
                IBout4value = num;
                break;
        }
        setIMG(num, view);
    }

    /**
     * set new random numbers to the options and the correct answers
     */
    public void setTheOptions(int num){

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
            setAnswer(IBout1, num);
            isIBout1 = true;
            setRandom(IBout4);
            setRandom(IBout2);
            setRandom(IBout3);
        }

        if(randomOut == 2){
            setAnswer(IBout2, num);
            isIBout2 = true;
            setRandom(IBout1);
            setRandom(IBout4);
            setRandom(IBout3);
        }

        if(randomOut == 3){
            setAnswer(IBout3, num);
            isIBout3 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout4);
        }

        if(randomOut == 4){
            setAnswer(IBout4, num);
            isIBout4 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout3);
        }
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
     * set a random image and correct answers for counting
     * @param view v
     */
    public void setRandomnumIMG(ImageView view){
        while (NEXTans == LASTans) NEXTans = randomIndex();

        List<String> aQuestion = allQuestions.get(NEXTans);

        // number of items on image
        IMGvalue = Integer.parseInt(aQuestion.get(1));
        // Path of the img
        String IMGname = aQuestion.get(0);

        // make the view have the random image
        int resID = getResources().getIdentifier(IMGname , "drawable", "gl.giskaland");

        // set the letter to the button
        view.setImageResource(resID);
        LASTans = NEXTans;
    }

    /**
     * set a random number to the bottun
     * @param view v
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


        String ID = getID(view);

        switch (ID) {
            case "IBout1":
                IBout1value = randomNum;
                break;
            case "IBout2":
                IBout2value = randomNum;
                break;
            case "IBout3":
                IBout3value = randomNum;
                break;
            case "IBout4":
                IBout4value = randomNum;
                break;
        }
        setIMG(randomNum, view);
    }

    /**
     * set a random number to the first option
     * @param quiz v
     */
    public void setRandomnum1(TextView quiz){
        // random from 0 to 10
        int randomNum;

        // number from 1 to 100 and the answer from a quiz is alwes from 1 to 10
        if(Divide)randomNum = ((int) Math.ceil(Math.random() * (10)))*((int) Math.ceil(Math.random() * (9)));

        // number from zero to ten
        else randomNum = ((int) (Math.random()*11));
        // we do not want the same as last time
        while (LASTans == randomNum){
            randomNum = ((int) (Math.random()*11));
            if(Divide)randomNum = ((int) Math.ceil(Math.random() * (10)))*((int) Math.ceil(Math.random() * (9)));
        }
        //make it the lastans so we dont get it next time
        LASTans = randomNum;
        IBnum1value = randomNum;

        quiz.append(Integer.toString(randomNum));
    }

    /**
     * calculate the answer from randomnum and ibnum1value
     * @param randomNum a int from 0 to 9
     * @return the answer
     */
    public int cal(int randomNum) {
        if(Minus)return IBnum1value - randomNum;//Minus
        else return IBnum1value + randomNum;//PLUS
    }

    /**
     * set the second number to a number that will work with the first number in level 2
     * @param quiz v
     */
    public void setRandomnum2LevelTwo(TextView quiz){
        int randomNum = -1;
        // the second number can not be larger than the first number if ew have minus
        // from 0 to the number we are subtracting from
        if(Minus)randomNum = ((int) (Math.random()* (IBnum1value+1)));
        else if(Plus)randomNum = ((int) (Math.random()*(10-IBnum1value+1)));
        // we dont want the same outcome 2x in a row
        while(cal(randomNum) == last_num){
            if(Minus)randomNum = ((int) (Math.random()* (IBnum1value+1)));
            else if(Plus)randomNum = ((int) (Math.random()*(10-IBnum1value+1)));
        }
        last_num = cal(randomNum);

        IBnum2value = randomNum;
        quiz.append(Integer.toString(randomNum));
    }
    /**
     * get a random number to use as the second number in calculations for level 3
     * @param quiz view for the button
     */
    public void setRandomnum2LevelThree(TextView quiz) {
        int randomNum = -1;
        if (Multiply) {
            //
            randomNum = ((int) Math.ceil(Math.random() * (10)));
            // we dont want the same outcome 2x in a row
            while ((IBnum1value * randomNum) == last_num) {
                randomNum = ((int) Math.ceil(Math.random() * (IBnum1value + 1)));
            }
            last_num = (IBnum1value * randomNum);
        } else if (Divide) {
            // from 0 to 10-the nub-mber we are adding to
            randomNum = ((int) Math.ceil(Math.random() * (10)));
            // we dont want the same outcome 2x in a row and get a hole number and te answer is only one digit
            while (((IBnum1value / randomNum) == last_num) || (IBnum1value % randomNum != 0) || ((IBnum1value / randomNum) > 9)) {
                randomNum = ((int) Math.ceil(Math.random() * (10)));
            }
            last_num = (IBnum1value / randomNum);
        }
        IBnum2value = randomNum;
        quiz.append(Integer.toString(randomNum));
    }

    /**
     * set a image to a button
     * @param randomNum the number to set too the button
     * @param view view for the button to be used
     */
    public void setIMG(int randomNum, ImageView view){
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
            view.setImageResource(R.drawable.three);
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
            view.setImageResource(R.drawable.eight);
        }
        else if (randomNum == 9) {
            view.setImageResource(R.drawable.nine);
        }
        else if (randomNum == 10) {
            view.setImageResource(R.drawable.ten);
        }
    }


    /**
     * check if we have the right answer and handle the aftermath
     * @param index the number of the button
     */
    public void handleScore(int index, View v) {
        Boolean[] allMathOut = {isIBout1, isIBout2, isIBout3, isIBout4};

        if (allMathOut[index]) {
            v.setBackgroundResource(R.drawable.greenback);
            ImageButton IBout1;
            IBout1 = (ImageButton) findViewById(R.id.IBout1);
            ImageButton IBout2;
            IBout2 = (ImageButton) findViewById(R.id.IBout2);
            ImageButton IBout3;
            IBout3 = (ImageButton) findViewById(R.id.IBout3);
            ImageButton IBout4;
            IBout4 = (ImageButton) findViewById(R.id.IBout4);
            IBout1.setEnabled(false);
            IBout2.setEnabled(false);
            IBout3.setEnabled(false);
            IBout4.setEnabled(false);
            quiz = (TextView) findViewById(R.id.QuestionText);

            if(lvl>1)
                RightAnswerText = "Já " + quiz.getText() + " er " + Answer + "!!";
            else
                RightAnswerText = "Já það er rétt hjá þér!!";

            toast = Toast.makeText(getApplicationContext(), RightAnswerText, Toast.LENGTH_SHORT);
            toast.show();
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.yeah);
            {
                mp.start();
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    makeRandom();// you have won
                }
            }, 2300);

            dbManager.saveScore(lvl, tableName, 2);
        }
        else {
            v.setBackgroundResource(R.drawable.redback);
            dbManager.saveScore(lvl, tableName, -1);
        }
        dbManager.showScores(lvl, tableName, scoreView);
    }
    
    /**
     * listener for the answer button
     */
    View.OnClickListener AnswerQ = new View.OnClickListener() {
        /**
         * check if the anwser is rigth
         */
        @Override
        public void onClick(View view) {
            EditText editText;
            editText = (EditText) findViewById(R.id.answerView);
            // the asnwer from the user
            int Ans = Integer.parseInt(editText.getText().toString());
            if(((Ans == IBnum1value*IBnum2value)&& Multiply) || ((Ans == IBnum1value/IBnum2value) && Divide)){
                RightAnswerText = "Já það er rétt hjá þér!!";

                toast = Toast.makeText(getApplicationContext(), RightAnswerText, Toast.LENGTH_SHORT);
                toast.show();
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.yeah);
                {
                    mp.start();
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        makeRandom();// you have won
                    }
                }, 2300);
                dbManager.saveScore(lvl, tableName, 2);
            }
            else {
                dbManager.saveScore(lvl, tableName, -1);
                RightAnswerText = "Nei ekki rétt hjá þér!!";

                Toast.makeText(getApplicationContext(), RightAnswerText,
                        Toast.LENGTH_SHORT).show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        ClearText();
                    }
                }, 2300);
            }
            dbManager.showScores(lvl, tableName, scoreView);
            System.out.println("answer");
        }
    };
    /**
     * if we click the rest button
     */
    View.OnClickListener rest = new View.OnClickListener() {
        /**
         * reset the game
         */
        @Override
        public void onClick(View view) {
            makeRandom();
        }
    };
    /**
     * if we press the clear button
     */
    View.OnClickListener clearnum = new View.OnClickListener() {
        /**
         * clear the answer text
         */
        @Override
        public void onClick(View view) {
            ClearText();
        }
    };

    /**
     * clear the answer text
     */
    public void ClearText(){
        EditText editText;
        editText = (EditText) findViewById(R.id.answerView);

        editText.setText("0");

        System.out.println("clear");
    }

    /**
     * disable the buttons
     */
    public void disablebuttons(){
        ImageButton zero;
        zero = (ImageButton) findViewById(R.id.zerobutton_0);
        ImageButton one;
        one = (ImageButton) findViewById(R.id.onebutton_1);
        ImageButton two;
        two = (ImageButton) findViewById(R.id.twobutton_2);
        ImageButton tree;
        tree = (ImageButton) findViewById(R.id.treebutton_3);
        ImageButton four;
        four = (ImageButton) findViewById(R.id.fourbutton_4);
        ImageButton five;
        five = (ImageButton) findViewById(R.id.fivebutton_5);
        ImageButton six;
        six = (ImageButton) findViewById(R.id.sixbutton_6);
        ImageButton seven;
        seven = (ImageButton) findViewById(R.id.sevenbutton_7);
        ImageButton eigth;
        eigth = (ImageButton) findViewById(R.id.eigthbutton_8);
        ImageButton nine;
        nine = (ImageButton) findViewById(R.id.ninebutton_9);

        Button reset;
        reset = (Button) findViewById(R.id.Newbutton);
        Button clear;
        clear = (Button) findViewById(R.id.Clearbutton);
        Button Answer;
        Answer = (Button) findViewById(R.id.Answerbutton);

        one.setEnabled(false);
        two.setEnabled(false);
        tree.setEnabled(false);
        four.setEnabled(false);
        five.setEnabled(false);
        six.setEnabled(false);
        seven.setEnabled(false);
        eigth.setEnabled(false);
        nine.setEnabled(false);
        zero.setEnabled(false);

        clear.setEnabled(false);
        Answer.setEnabled(false);
        reset.setEnabled(false);
    }

    /**
     * handle if we press a number button
     */
    View.OnClickListener Numlistner = new View.OnClickListener() {
        /**
         * set the answer text with what we pressed
         */
        @Override
        public void onClick(View view) {
            EditText editText;
            editText = (EditText) findViewById(R.id.answerView);

            String viewId = view.getResources().getResourceName(view.getId());
            String ID = viewId.substring(viewId.lastIndexOf('_') + 1);

            String MyAns = editText.getText().toString();
            if(MyAns.equals("0")){
                editText.setText(ID);
                int Ans = Integer.parseInt(ID);
                if(((Ans == IBnum1value*IBnum2value)&& Multiply) || ((Ans == IBnum1value/IBnum2value) && Divide)){
                    view.setBackgroundResource(R.drawable.greenback);
                    disablebuttons();
                    RightAnswerText = "Já það er rétt hjá þér!!";

                    toast = Toast.makeText(getApplicationContext(), RightAnswerText, Toast.LENGTH_SHORT);
                    toast.show();
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.yeah);
                    {
                        mp.start();
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            makeRandom();// you have won
                        }
                    }, 2300);
                    dbManager.saveScore(lvl, tableName, 2);
                }
            }else if (MyAns.length()<3){
                editText.append(ID);
            }
            System.out.println(ID);
        }
    };
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // listers for the level one and two answers buttons
    View.OnClickListener checkIfRightAnsout1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handleScore(0, view);
        }
    };

    View.OnClickListener checkIfRightAnsout2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handleScore(1, view);
        }
    };

    View.OnClickListener checkIfRightAnsout3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handleScore(2, view);
        }
    };

    View.OnClickListener checkIfRightAnsout4 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            handleScore(3, view);
        }
    };
    ///////////////////////////////////////////////////////////////////////////////////////////////
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