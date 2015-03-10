package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class ThirdLevel extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_level);
        //**************************************************************************
        // set up OnClickListeners
        Button ButtonMath;
        Button ButtonSpelling;
        Button ButtonBrainPuzzle;

        ButtonMath = (Button) findViewById(R.id.buttonMathLevel3);
        ButtonMath.setOnClickListener(Math_level_3_ClickListener);
        ButtonSpelling = (Button) findViewById(R.id.buttonSpellThePicLevel3);
        ButtonSpelling.setOnClickListener(SpellThePicLevel3_ClickListener);
        ButtonBrainPuzzle = (Button) findViewById(R.id.buttonBrainPuzzlelv3);
        ButtonBrainPuzzle.setOnClickListener(BrainPuzzleLv3ClickListener);

        Button ButtonReturn;
        ButtonReturn = (Button) findViewById(R.id.buttonReturn);
        ButtonReturn.setOnClickListener(Return_ClickListener);

        //**************************************************************************
    }

    /** math level 3
     *
     */
    View.OnClickListener Math_level_3_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a takkann er kallad a fallid til að opna
         * @param v view
         */
        @Override
        public void onClick(View v) {
            Math_Level_3();
        }
    };

    /**
     * opens new activity
     */
    private void Math_Level_3(){
        startActivity(new Intent(this, MathLevel3.class));

    };

    /**
     *  spell the pic
     */
    View.OnClickListener SpellThePicLevel3_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a takkann er kallad a fallid til að opna
         * @param v view
         */
        @Override
        public void onClick(View v) {

            SpellThePicLevel3();
        }
    };

    /**
     * opens new activity
     */
    private void SpellThePicLevel3(){
        Intent intent = new Intent(this, SpellingGame.class);
        Bundle b = new Bundle();
        b.putInt("key", 3); // Indicating level 3
        intent.putExtras(b);
        startActivity(intent);
        finish();
    };

    /** brain puzzle
     *
     */
    View.OnClickListener BrainPuzzleLv3ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a takkann er kallad a fallid til að opna
         * @param v view
         */
        @Override
        public void onClick(View v) {
            BrainPuzzle();
        }
    };

    /**
     * opens new activity
     */
    private void BrainPuzzle(){
        startActivity(new Intent(this, QuizLevel3.class));

    };

    /** Return button
     *
     */
    View.OnClickListener Return_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a takkann er kallad a fallid til að opna
         * @param v view
         */
        @Override
        public void onClick(View v) {
            Return();
        }
    };

    /**
     * Return to start
     */
    private void Return(){
        startActivity(new Intent(this, Upphafsglugginn.class));

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_third_level, menu);
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
