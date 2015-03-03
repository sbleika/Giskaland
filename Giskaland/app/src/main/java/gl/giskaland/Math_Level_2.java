package gl.giskaland;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Math_Level_2 extends ActionBarActivity {
    // the numbers that we are working with
    int IBnum1value;
    int IBnum2value;
    // to make sure we only get one of each
    int IBout1value;
    int IBout2value;
    int IBout3value;
    int IBout4value;
    // to know if we used plus or minus
    Boolean Plus;
    Boolean Minus;
    // to know where the right answer is
    Boolean isIBout1 = false;
    Boolean isIBout2 = false;
    Boolean isIBout3 = false;
    Boolean isIBout4 = false;

    int last_num;

    int SCORE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math__level_2);

        makeRandom(); // new numbers Restart
    }

    /**
     * put new numbers to the buttons
     */
    public void makeRandom(){
        // reset all the global values
        //********************************************************************
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
        //********************************************************************

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
        IBplus_min = (ImageButton) findViewById(R.id.IBplusmin);
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
        setRandomnum2(IBnum2);

        // set random numbers to the 4 options
        setIBout();
    }

    /**
     * set new random numbers to the options and the correct answers
     */
    public void setIBout(){

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
            setImageTO(IBout1);
            isIBout1 = true;
            setRandom(IBout4);
            setRandom(IBout2);
            setRandom(IBout3);
        }

        if(randomOut == 2){
            setImageTO(IBout2);
            isIBout2 = true;
            setRandom(IBout1);
            setRandom(IBout4);
            setRandom(IBout3);
        }

        if(randomOut == 3){
            setImageTO(IBout3);
            isIBout3 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout4);
        }

        if(randomOut == 4){
            setImageTO(IBout4);
            isIBout4 = true;
            setRandom(IBout1);
            setRandom(IBout2);
            setRandom(IBout3);
        }
    }

    /**
     *
     * @param view
     */
    public void setImageTO(ImageView view){
        int num = -1;
        if (Minus)num = IBnum1value - IBnum2value;
        if (Plus)num = IBnum1value + IBnum2value;

        String viewId = view.getResources().getResourceName(view.getId());
        String ID = viewId.substring(viewId.lastIndexOf('/') + 1);

        if(ID.equals("IBout1")) {
            isIBout1 = true;
            IBout1value = num;
        }
        else if (ID.equals("IBout2")){
            isIBout2 = true;
            IBout2value = num;
        }
        else if (ID.equals("IBout3")){
            isIBout3 = true;
            IBout3value = num;
        }
        else if (ID.equals("IBout4")){
            isIBout4 = true;
            IBout4value = num;
        }

        setIMG(num, view);
    }

    /**
     *
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
     *
     * @param view
     */
    public void setRandomnum1(ImageView view){
        // random from 0 to 10
        int randomNum = ((int) (Math.random()*11));

        IBnum1value = randomNum;

        setIMG(randomNum, view);
    }

    /**
     *
     * @param randomNum
     * @return
     */
    public int cal(int randomNum) {
        if(Minus)return IBnum1value - randomNum;
        else return IBnum1value + randomNum;//PLUS

    }

    /**
     *
     * @param view
     */
    public void setRandomnum2(ImageView view){
        int randomNum = -1;
        // the second number can not be larger than the first number if ew have minus
        // from 0 to the number we are subtracting from
        randomNum = ((int) (Math.random()* (IBnum1value+1)));
        // we dont want the same outcome 2x in a row
        while(cal(randomNum) == last_num){
            randomNum = ((int) (Math.random()* (IBnum1value+1)));
        }
        last_num = cal(randomNum);

        IBnum2value = randomNum;
        setIMG(randomNum, view);
    }

    /**
     *
     * @param randomNum
     * @param view
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

    public void saveScore(){

    }

    public void getScore(){

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
            if(isIBout1){
                makeRandom();
                SCORE++;
                saveScore();
            }
            else SCORE--;

            System.out.println(SCORE);
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
            if(isIBout2){
                makeRandom();
                SCORE++;
                saveScore();
            }
            else SCORE--;

            System.out.println(SCORE);
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
            if(isIBout3){
                makeRandom();
                SCORE++;
                saveScore();
            }
            else SCORE--;

            System.out.println(SCORE);
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
            if(isIBout4){
                makeRandom();
                SCORE++;
                saveScore();
            }
            else SCORE--;

            System.out.println(SCORE);
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