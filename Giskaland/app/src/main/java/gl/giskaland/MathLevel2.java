package gl.giskaland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MathLevel2 extends ActionBarActivity {

    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    RadioGroup.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean POPupINACTIVE = true;

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

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // amke the popup
        PopUp();

        setContentView(R.layout.activity_math_level_2);

        makeRandom(); // new numbers Restart
    }

    /**
     *
     */
    public void PopUp(){
        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        tv = new TextView(this);
        but = new Button(this);
        but.setText("Reyna aftur");
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popUp.dismiss();
                POPupINACTIVE = true;
            }
        });
        params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("RANGT svar !!");
        tv.setTextSize(48.0F);
        tv.setTextColor(0xFFFFFFFF);
        layout.addView(tv, params);
        layout.addView(but, params);
        popUp.setContentView(layout);
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

        // random number from one to four
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
        if(Minus)return IBnum1value - randomNum;//Minus
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
        if(Minus)randomNum = ((int) (Math.random()* (IBnum1value+1)));
        else if(Plus)randomNum = ((int) (Math.random()*(10-IBnum1value+1)));
        // we dont want the same outcome 2x in a row
        while(cal(randomNum) == last_num){
            if(Minus)randomNum = ((int) (Math.random()* (IBnum1value+1)));
            else if(Plus)randomNum = ((int) (Math.random()*(10-IBnum1value+1)));;
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
            if (POPupINACTIVE) {
                if (isIBout1) {
                    makeRandom();
                    SCORE++;
                    saveScore();
                } else {
                    SCORE--;

                    if (POPupINACTIVE) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(0, 0, 850, 133);
                        POPupINACTIVE = false;
                    }
                }
            }
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
            if (POPupINACTIVE) {
                if (isIBout2) {
                    makeRandom();
                    SCORE++;
                    saveScore();
                } else {
                    SCORE--;

                    if (POPupINACTIVE) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(0, 0, 850, 133);
                        POPupINACTIVE = false;
                    }
                }
            }

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
            if (POPupINACTIVE) {
                if (isIBout3) {
                    makeRandom();
                    SCORE++;
                    saveScore();
                } else {
                    SCORE--;

                    if (POPupINACTIVE) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(0, 0, 850, 133);
                        POPupINACTIVE = false;
                    }
                }
            }
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
            if (POPupINACTIVE) {
                if (isIBout4) {
                    makeRandom();
                    SCORE++;
                    saveScore();
                } else {
                    SCORE--;

                    if (POPupINACTIVE) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(0, 0, 850, 133);
                        POPupINACTIVE = false;
                    }
                }

                System.out.println(SCORE);
            }
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