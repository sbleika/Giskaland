package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class SecondLevel extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);
        //**********************************************************************
        // set up onclicklistners
        ImageButton ButtonMath;
        ImageButton ButtonSpelling;
        Button ButtonBrainPuzzle;

        ButtonMath = (ImageButton) findViewById(R.id.buttonMathLevel2);
        ButtonMath.setOnClickListener(Math_level_2_ClickListener);
        ButtonSpelling = (ImageButton) findViewById(R.id.buttonSpellThePicLevel2);
        ButtonSpelling.setOnClickListener(SpellThePicLevel2_ClickListener);
        ButtonBrainPuzzle = (Button) findViewById(R.id.buttonBrainPuzzle);
        ButtonBrainPuzzle.setOnClickListener(BrainPuzzleClickListener);

        //**********************************************************************

    }

    /**
     * math level 2 listener
     */
    View.OnClickListener Math_level_2_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a
         * @param v view
         */
        @Override
        public void onClick(View v) {
            Math_Level_2();
        }
    };

    /**
     * opens activity
     */
    private void Math_Level_2(){
        Intent intent = new Intent(this, MathGame.class);
        Bundle b = new Bundle();
        b.putInt("key", 2); // Indicating level 2
        intent.putExtras(b);
        startActivity(intent);
        finish();
    };

    /**
     * spelling game
     */
    View.OnClickListener SpellThePicLevel2_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a
         * @param v view
         */
        @Override
        public void onClick(View v) {

            SpellThePicLevel2();
        }
    };

    /**
     * open the activity for spelling game
     */
    private void SpellThePicLevel2(){
        Intent intent = new Intent(this, SpellingGame.class);
        Bundle b = new Bundle();
        b.putInt("key", 2); // Indicating level 2
        intent.putExtras(b);
        startActivity(intent);
        finish();
    };

    /**
     * brain puzzle listener
     */
    View.OnClickListener BrainPuzzleClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a
         * @param v view
         */
        @Override
        public void onClick(View v) {
            BrainPuzzle();
        }
    };

    /**
     * open activity
     */
    private void BrainPuzzle(){
        Intent intent = new Intent(this, QuizGame.class);
        Bundle b = new Bundle();
        b.putInt("key", 2); // Indicating level 2
        intent.putExtras(b);
        startActivity(intent);
        finish();
    };

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
}
