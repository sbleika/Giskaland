package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class FirstLevelActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlevel);
        Button ButtonMath;
        Button ButtonSpelling;
        Button ButtongameOne;
        Button ButtongameTwo;

        ButtonMath = (Button) findViewById(R.id.buttonMathLevel1);
        ButtonMath.setOnClickListener(Math_level_1_ClickListener);
        ButtonSpelling = (Button) findViewById(R.id.buttonWhatLetter);
        ButtonSpelling.setOnClickListener(What_Letter_ClickListener);
        //ButtongameOne = (Button) findViewById(R.id.buttonGameOne);
        //ButtongameOne.setOnClickListener(gotoThirdClickListener);
        //ButtongameTwo = (Button) findViewById(R.id.buttonGameTwo);
        //ButtongameTwo.setOnClickListener(gotoThirdClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_firstlevel, menu);
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

    /** takki 1
     *
     */
    View.OnClickListener Math_level_1_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            Math_Level_1();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void Math_Level_1(){
        startActivity(new Intent(this, Math_level_1.class));

    };

    /** takki 2
     *
     */
    View.OnClickListener What_Letter_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            What_Letter();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void What_Letter(){
        startActivity(new Intent(this, Spelling_level_1.class));

    };

}
