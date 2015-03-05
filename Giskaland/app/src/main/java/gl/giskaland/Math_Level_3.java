package gl.giskaland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class Math_Level_3 extends ActionBarActivity {

    // the numbers that we are working with
    int IBnum1value;
    int IBnum2value;

    // to know if we used multiply or divide
    Boolean Multiply;
    Boolean Divide;

    int last_num;
    String MyAns;
    int SCORE;

    /**
     * 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_level_3);

        Button reset;
        reset = (Button) findViewById(R.id.Newbutton);
        reset.setOnClickListener(rest);
        Button clear;
        clear = (Button) findViewById(R.id.Clearbutton);
        clear.setOnClickListener(clearnum);
        Button Answer;
        Answer = (Button) findViewById(R.id.Answerbutton);
        Answer.setOnClickListener(AnswerQ);

        makeRandom(); // new numbers Restart
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
            }
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
     * put new numbers to the buttons
     */
    public void makeRandom(){
        // reset all the global values
        //********************************************************************
        IBnum1value = -1;
        IBnum2value = -1;
        Multiply = false;
        Divide = false;
        //********************************************************************
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
            IBplus_min.setImageResource(R.drawable.multiply);
            Multiply = true;
            Divide = false;
        }
        else if (randomOP == 2) {
            IBplus_min.setImageResource(R.drawable.divide);
            Multiply = false;
            Divide = true;
        }
        //********************************************************************
        // set random number from one to nine to the two numbers to calculate
        setRandomnum1(IBnum1);
        setRandomnum2(IBnum2);
    }

    /**
     *
     * @param view
     */
    public void setRandomnum1(ImageView view){
        int randomNum = -1;
        // random from 0 to 10
        if(Multiply){
            randomNum = ((int) (Math.random()*11));
        }
        else if(Divide){
            randomNum = ((int) Math.ceil(Math.random() * (10)));
        }

        IBnum1value = randomNum;

        setIMG(randomNum, view);
    }

    /**
     *
     * @param view
     */
    public void setRandomnum2(ImageView view) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_math__level_3, menu);
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
