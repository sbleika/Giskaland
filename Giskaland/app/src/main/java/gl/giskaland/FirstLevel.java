package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FirstLevel extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlevel);
        //******************************************************************
        Button ButtonMathLevel1;
        Button ButtonWhatLetter;

        ButtonMathLevel1 = (Button) findViewById(R.id.buttonMathLevel1);
        ButtonMathLevel1.setOnClickListener(Math_level_1_ClickListener);
        ButtonWhatLetter = (Button) findViewById(R.id.buttonWhatLetter);
        ButtonWhatLetter.setOnClickListener(What_Letter_ClickListener);

        Button ButtonReturn;
        ButtonReturn = (Button) findViewById(R.id.buttonReturn);
        ButtonReturn.setOnClickListener(Return_ClickListener);
        //*******************************************************************
        }

    /** takki fyrir stardfradi
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
     * opnar nyja gluggan fyrir stardfradi
     */
    private void Math_Level_1(){
        Intent intent = new Intent(this, MathLevel1.class);
        Bundle b = new Bundle();
        b.putInt("key", 1); // Indicating level 1
        intent.putExtras(b);
        startActivity(intent);
        finish();
    };

    /** takki fyrir stafaleikinn
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
     * opnar nyja gluggan fyrir stafaleikinn
     */
    private void What_Letter(){
        Intent intent = new Intent(this, SpellingGame.class);
        Bundle b = new Bundle();
        b.putInt("key", 1); // Indicating level 1
        intent.putExtras(b);
        startActivity(intent);
        finish();
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
