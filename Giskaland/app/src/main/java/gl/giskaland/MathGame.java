package gl.giskaland;

import android.database.sqlite.SQLiteException;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.PopupWindow;
        import android.widget.RadioGroup;
        import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;


public class MathGame extends ActionBarActivity {
    // to know what level we are working with
    int lvl;

    //******************************
    // for the popUp window
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    RadioGroup.LayoutParams params;
    boolean POPupINACTIVE = true;
    //******************************

    // the numbers that we are working with
    int IBnum1value;
    int IBnum2value;
    // the right number of items for level 1
    int IMGvalue;

    int LASTans;
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

    String MyAns;

    int last_num;

    // DbManager for usage inside this activity
    DbManager dbManager;
    /**
     *
     * @param savedInstanceState v
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        if(lvl == 1)
            setContentView(R.layout.activity_math_level_1);
        else if (lvl == 2)
            setContentView(R.layout.activity_math_level_2);
        else if (lvl == 3)
            setContentView(R.layout.activity_math_level_3);


        initDbManager();
        showScores(lvl);

        System.out.println("PAST THE initDbManager()");

        // make the popup
        PopUp();
        // Set up buttons for level 3
        if(lvl == 3)SetUpButtons();


        makeRandom(); // new numbers Restart
    }

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

        //********************************************************************
    }

    /**
     *
     */
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

    /**
     *
     */
    public void PopUp(){
        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        tv = new TextView(this);
        params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("RANGT svar !!");
        tv.setTextSize(48.0F);
        tv.setTextColor(0xFFFFFFFF);
        layout.addView(tv, params);
        popUp.setContentView(layout);
    }

    /**
     * put new numbers to the buttons
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
        IBnum1value = -1;
        IBnum2value = -1;
        Multiply = false;
        Divide = false;
        //********************************************************************

        if (lvl==1)SetUplevel1();
        if (lvl==2)SetUplevel2();
        if (lvl==3)SetUplevel3();

    }

    /**
     *
     */
    public void SetUplevel3(){
        ClearText();
        // set up the buttons
        //********************************************************************
        ImageButton IBnum1;
        IBnum1 = (ImageButton) findViewById(R.id.num1button);
        ImageButton IBnum2;
        IBnum2 = (ImageButton) findViewById(R.id.num2button);

        ImageButton IBplus_min;
        IBplus_min = (ImageButton) findViewById(R.id.OPbutton);
        //********************************************************************

        // set the operator to
        //********************************************************************
        int randomOP = (int) Math.ceil(Math.random()*2);
        if (randomOP == 1) {
            //multiply
            IBplus_min.setImageResource(R.drawable.multiply);
            Multiply = true;
            Divide = false;
        }
        else if (randomOP == 2) {
            //DIVIDE
            IBplus_min.setImageResource(R.drawable.divide);
            Multiply = false;
            Divide = true;
        }
        //********************************************************************
        // set random number from one to nine to the two numbers to calculate
        setRandomnum1(IBnum1);
        setRandomnum2LevelTree(IBnum2);
    }

    /**
     *
     */
    public void SetUplevel2(){
        // set up the buttons
        //********************************************************************
        ImageButton IBnum1;
        IBnum1 = (ImageButton) findViewById(R.id.IBnum1);
        ImageButton IBnum2;
        IBnum2 = (ImageButton) findViewById(R.id.IBnum2);
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
        ImageButton IBplus_min;
        IBplus_min = (ImageButton) findViewById(R.id.IMG);
        //********************************************************************

        // set the operator to plus or minus
        //********************************************************************
        double randomOP = (int) Math.ceil(Math.random()*2);
        if (randomOP == 1) {
            IBplus_min.setImageResource(R.drawable.plus);
            Plus = true;
            Minus = false;
        }
        else if (randomOP == 2) {
            IBplus_min.setImageResource(R.drawable.minus);
            Plus = false;
            Minus = true;
        }
        //********************************************************************
        // set random number from one to nine to the two numbers to calculate
        setRandomnum1(IBnum1);
        setRandomnum2LevelTwo(IBnum2);

        int num = -1;
        if (Minus)num = IBnum1value - IBnum2value;
        if (Plus)num = IBnum1value + IBnum2value;

        // set random numbers to the 4 options
        setTheOptions(num);
    }

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
        //********************************************************************

        setRandomnumIMG(IMG);

        // set random numbers to the four options
        setTheOptions(IMGvalue);
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
     *pictures and correct answers for counting
     * @param view v
     */
    public void setRandomnumIMG(ImageView view){
        // random from 1 to 4
        int IMGnum = ((int) Math.ceil(Math.random()*4));
        while(IMGnum == last_num){
            IMGnum = ((int) Math.ceil(Math.random()*4));
        }
        last_num = IMGnum;
        //todo setja inni gagnagrunn til ad tetta se ekki hradkodad i kodan
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
     *
     * @param view v
     */
    public void setRandomnum1(ImageView view){
        // random from 0 to 10
        int randomNum;
        if(Divide)randomNum = ((int) Math.ceil(Math.random() * (10)));

        else randomNum = ((int) (Math.random()*11));
        while (LASTans == randomNum){
            randomNum = ((int) (Math.random()*11));
            if(Divide)randomNum = ((int) Math.ceil(Math.random() * (10)));
        }
        //make it the lastans so we dont get it next time
        LASTans = randomNum;
        IBnum1value = randomNum;

        setIMG(randomNum, view);
    }

    /**
     *
     * @param randomNum v
     * @return v
     */
    public int cal(int randomNum) {
        if(Minus)return IBnum1value - randomNum;//Minus
        else return IBnum1value + randomNum;//PLUS
    }

    /**
     *
     * @param view v
     */
    public void setRandomnum2LevelTwo(ImageView view){
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
        setIMG(randomNum, view);
    }
    /**
     *
     * @param view v
     */
    public void setRandomnum2LevelTree(ImageView view) {
        int randomNum = -1;
        //
        if (Multiply) {
            //
            randomNum = ((int) (Math.random() * (11)));
            // we dont want the same outcome 2x in a row
            while ((IBnum1value * randomNum) == last_num) {
                randomNum = ((int) (Math.random() * (IBnum1value + 1)));
            }

            last_num = (IBnum1value * randomNum);
        } else if (Divide) {
            // from 0 to 10-the nub-mber we are adding to
            randomNum = ((int) Math.ceil(Math.random() * (10)));
            // we dont want the same outcome 2x in a row
            while (((IBnum1value + randomNum) == last_num) || (IBnum1value % randomNum != 0)) {
                randomNum = ((int) Math.ceil(Math.random() * (10)));
            }
            last_num = (IBnum1value + randomNum);
        }
        IBnum2value = randomNum;
        setIMG(randomNum, view);
    }

    /**
     *
     * @param randomNum v
     * @param view v
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
        String[] score;
        score = new String[]{
                allSpellingScores.get((lvl * 2) - 1),   // Tmp score
                allSpellingScores.get(lvl * 2)    // Total score
        };
        return score;
    }

    public void initScores() {
        dbManager.updateScore("MathScores", 0, lvl, 0, true);
    }

    public void showScores(int lvl) {
        String[] newScores = getScore();

        // Default value
        TextView scoreView = (TextView)findViewById(R.id.TextMathLevel1score);
        if (lvl == 2)
            scoreView = (TextView)findViewById(R.id.TextMathLevel2score);
        else if (lvl == 3)
            scoreView = (TextView)findViewById(R.id.TextMathLevel3score);

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
            new CountDownTimer(1500,1000){
                /**
                 * breytum bakgrunni a takkanum a medan vid teljum nidur
                 * @param millisUntilFinished timi eftir
                 */
                @Override
                public void onTick(long millisUntilFinished){
                    if (POPupINACTIVE) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(0, 0, 850, 133);
                        POPupINACTIVE = false;
                    }
                }

                /**
                 * breytum bakgrunni a takkanum i upprunalegt astandi tegar 3 sekundur eru lidnar
                 */
                @Override
                public void onFinish(){
                    popUp.dismiss();
                    POPupINACTIVE = true;
                }
            }.start();
        }
        showScores(lvl);
    }
    /**
     *
     */
    View.OnClickListener AnswerQ = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            EditText editText;
            editText = (EditText) findViewById(R.id.answerView);

            int Ans = Integer.parseInt(editText.getText().toString());
            if(Ans == IBnum1value*IBnum2value || Ans == IBnum1value/IBnum2value){
                makeRandom();
                saveScore(2);
            }
            else {
                saveScore(-1);
                new CountDownTimer(1500,1000){
                    /**
                     * breytum bakgrunni a takkanum a medan vid teljum nidur
                     * @param millisUntilFinished timi eftir
                     */
                    @Override
                    public void onTick(long millisUntilFinished){
                        if (POPupINACTIVE) {
                            popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                            popUp.update(0, 0, 850, 133);
                            POPupINACTIVE = false;
                        }
                    }

                    /**
                     * breytum bakgrunni a takkanum i upprunalegt astandi tegar 3 sekundur eru lidnar
                     */
                    @Override
                    public void onFinish(){
                        popUp.dismiss();
                        POPupINACTIVE = true;
                    }
                }.start();
                ClearText();
            }
            showScores(lvl);
            System.out.println("answer");
        }
    };
    /**
     *
     */
    View.OnClickListener rest = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {

            makeRandom();
            System.out.println("newwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        }
    };
    /**
     *
     */
    View.OnClickListener clearnum = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            ClearText();
        }
    };

    public void ClearText(){
        EditText editText;
        editText = (EditText) findViewById(R.id.answerView);

        editText.setText("0");

        System.out.println("clear");
    }
    /**
     *
     */
    View.OnClickListener Numlistner = new View.OnClickListener() {
        /**
         *
         */
        @Override
        public void onClick(View view) {
            EditText editText;
            editText = (EditText) findViewById(R.id.answerView);

            String viewId = view.getResources().getResourceName(view.getId());
            String ID = viewId.substring(viewId.lastIndexOf('_') + 1);

            MyAns = editText.getText().toString();
            if(MyAns.equals("0")){
                editText.setText(ID);
            }else if (Integer.parseInt(MyAns)<200 && MyAns.length()<3){
                editText.append(ID);
            }
            System.out.println(ID);
        }
    };
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
        getMenuInflater().inflate(R.menu.menu_math__level_2, menu);
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