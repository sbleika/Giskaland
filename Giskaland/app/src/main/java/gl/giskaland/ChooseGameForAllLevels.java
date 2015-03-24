package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class ChooseGameForAllLevels extends ActionBarActivity {
    int lvl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the lvl
        Bundle b = getIntent().getExtras();
        lvl = b.getInt("key");

        if(lvl == 1)
            setContentView(R.layout.activity_firstlevel);
        else if (lvl == 2)
            setContentView(R.layout.activity_second_level);
        else if (lvl == 3)
            setContentView(R.layout.activity_third_level);
        //******************************************************************
        ImageButton ButtonMath;
        ImageButton ButtonSpelling;
        ImageButton ButtonBrainPuzzle;

        ButtonMath = (ImageButton) findViewById(R.id.buttonMath);
        ButtonMath.setOnClickListener(Math_ClickListener);
        ButtonSpelling = (ImageButton) findViewById(R.id.buttonSpellThePic);
        ButtonSpelling.setOnClickListener(SpellThePic_ClickListener);
        if(lvl != 1) {
            ButtonBrainPuzzle = (ImageButton) findViewById(R.id.buttonBrainPuzzle);
            ButtonBrainPuzzle.setOnClickListener(BrainPuzzleClickListener);
        }
        //*******************************************************************
    }

    /**
     * math level 2 listener
     */
    View.OnClickListener Math_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a
         * @param v view
         */
        @Override
        public void onClick(View v) {
            Math();
        }
    };

    /**
     * opens activity
     */
    private void Math(){
        Intent intent = new Intent(this, MathGame.class);
        Bundle b = new Bundle();
        if(lvl == 1)b.putInt("key", 1); // Indicating level 1
        if(lvl == 2)b.putInt("key", 2); // Indicating level 2
        if(lvl == 3)b.putInt("key", 3); // Indicating level 3
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    /**
     * spelling game
     */
    View.OnClickListener SpellThePic_ClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a
         * @param v view
         */
        @Override
        public void onClick(View v) {

            SpellThePic();
        }
    };

    /**
     * open the activity for spelling game
     */
    private void SpellThePic(){
        Intent intent = new Intent(this, SpellingGame.class);
        Bundle b = new Bundle();
        if(lvl == 1)b.putInt("key", 1); // Indicating level 1
        if(lvl == 2)b.putInt("key", 2); // Indicating level 2
        if(lvl == 3)b.putInt("key", 3); // Indicating level 3
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

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
        if(lvl == 1)b.putInt("key", 1); // Indicating level 1
        if(lvl == 2)b.putInt("key", 2); // Indicating level 2
        if(lvl == 3)b.putInt("key", 3); // Indicating level 3
        intent.putExtras(b);
        startActivity(intent);
        finish();
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
}