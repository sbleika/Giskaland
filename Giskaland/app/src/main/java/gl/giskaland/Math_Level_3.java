package gl.giskaland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    int SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_level_3);

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
        Multiply = false;
        Divide = false;
        //********************************************************************

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
        // random from 0 to 10
        int randomNum = ((int) (Math.random()*11));

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
            randomNum = ((int) (Math.random() * (11)));
            // we dont want the same outcome 2x in a row
            while (((IBnum1value + randomNum) == last_num) || (IBnum1value % randomNum != 0)) {
                randomNum = ((int) (Math.random() * (11)));
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
