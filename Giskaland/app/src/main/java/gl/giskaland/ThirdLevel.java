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
        Button ButtonMath;
        Button ButtonSpelling;
        Button ButtonBrainPuzzle;
        Button ButtongameTwo;

        ButtonMath = (Button) findViewById(R.id.buttonMathLevel2);
        ButtonMath.setOnClickListener(Math_level_3_ClickListener);
        ButtonSpelling = (Button) findViewById(R.id.buttonSpellThePicLevel3);
        ButtonSpelling.setOnClickListener(SpellThePicLevel3_ClickListener);
        ButtonBrainPuzzle = (Button) findViewById(R.id.buttonBrainPuzzlelv3);
        ButtonBrainPuzzle.setOnClickListener(BrainPuzzleLv3ClickListener);
        //ButtongameTwo = (Button) findViewById(R.id.buttonGameTwo);
        //ButtongameTwo.setOnClickListener(gotoThirdClickListener);

        Button ButtonReturn;
        ButtonReturn = (Button) findViewById(R.id.buttonReturn);
        ButtonReturn.setOnClickListener(Return_ClickListener);
    }
    public void openGameActivity (View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }


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

    /** takki 1
     *
     */
    View.OnClickListener Math_level_3_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            Math_Level_3();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void Math_Level_3(){
        startActivity(new Intent(this, Math_Level_3.class));

    };

    /** takki 2
     *
     */
    View.OnClickListener SpellThePicLevel3_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            SpellThePicLevel3();
        }
    };

    /**
     * opnar nyja gluggan nothafi
     */
    private void SpellThePicLevel3(){
        startActivity(new Intent(this, Spelling_level_3.class));

    };

    /** takki 3
     *
     */
    View.OnClickListener BrainPuzzleLv3ClickListener  = new View.OnClickListener() {
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
        startActivity(new Intent(this, Quiz_Level_3.class));

    };

    /** takki til að fara a upphafskja
     *
     */
    View.OnClickListener Return_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            Return();
        }
    };

    /**
     * fer til baka i upphafsskja
     */
    private void Return(){
        startActivity(new Intent(this, Upphafsglugginn.class));

    };

}
