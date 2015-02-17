package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SecondLevel extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);
        Button ButtonMath;
        Button ButtonSpelling;
        Button ButtonBrainPuzzle;
        Button ButtongameTwo;

        ButtonMath = (Button) findViewById(R.id.buttonMathLevel2);
        ButtonMath.setOnClickListener(Math_level_2_ClickListener);
        ButtonSpelling = (Button) findViewById(R.id.buttonSpellThePicLevel2);
        ButtonSpelling.setOnClickListener(SpellThePicLevel2_ClickListener);
        ButtonBrainPuzzle = (Button) findViewById(R.id.buttonBrainPuzzle);
        ButtonBrainPuzzle.setOnClickListener(BrainPuzzleClickListener);
        //ButtongameTwo = (Button) findViewById(R.id.buttonGameTwo);
        //ButtongameTwo.setOnClickListener(gotoThirdClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second_level, menu);
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
    View.OnClickListener Math_level_2_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            Math_Level_2();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void Math_Level_2(){
        startActivity(new Intent(this, Math_Level_2.class));

    };

    /** takki 2
     *
     */
    View.OnClickListener SpellThePicLevel2_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            SpellThePicLevel2();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void SpellThePicLevel2(){
        startActivity(new Intent(this, Spelling_level_2.class));

    };

    /** takki 3
     *
     */
    View.OnClickListener BrainPuzzleClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {
            BrainPuzzle();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void BrainPuzzle(){
        startActivity(new Intent(this, Quiz_Level_2.class));

    };

}
