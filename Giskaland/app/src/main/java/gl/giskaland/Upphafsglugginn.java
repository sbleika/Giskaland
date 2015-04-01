package gl.giskaland;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Upphafsglugginn extends ActionBarActivity {
    int lvl;
    boolean playing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upphafsglugginn);
        //*****************************************************************
        // set up OnClickListeners for button
        ImageButton FirstButton;
        ImageButton SecondButton;
        ImageButton ThirdButton;
        FirstButton = (ImageButton) findViewById(R.id.buttonFirstLevel);
        FirstButton.setOnClickListener(gotoChooseGameForOne);
        SecondButton = (ImageButton) findViewById(R.id.buttonSecondLevel);
        SecondButton.setOnClickListener(gotoChooseGameForTwo);
        ThirdButton = (ImageButton) findViewById(R.id.buttonThirdLevel);
        ThirdButton.setOnClickListener(gotoChooseGameForTree);

        ImageButton Giski;
        Giski = (ImageButton) findViewById(R.id.Giski);
        Giski.setOnClickListener(giskiclicked);
        //*****************************************************************
    }



    /** Buttonlistener for if Giski is clicked
     *
     */
    View.OnClickListener giskiclicked  = new View.OnClickListener() {
        /**
         * ef smellt er a giska
         * @param v view
         */
        @Override
        public void onClick(View v) {
            // 
            ImageButton Giski;
            Giski = (ImageButton)findViewById(R.id.Giski);
            Giski.setImageResource(R.drawable.leikur);

            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hello);
            if (playing) {
                mp.stop();
                playing = false;
            } else {
                mp.start();
                playing = true;
            }

        }
    };

    /** Button for first age group
     *
     */
    View.OnClickListener gotoChooseGameForOne  = new View.OnClickListener() {
        /**
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            lvl = 1;
            ChooseGameForAllLevels();
        }
    };
    /** Button for second age group
     *
     *
     */
    View.OnClickListener gotoChooseGameForTwo  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {
            lvl = 2;
            ChooseGameForAllLevels();
        }
    };
    /** Button for third age group
     *
     *
     */
    View.OnClickListener gotoChooseGameForTree  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {
            lvl = 3;
            ChooseGameForAllLevels();
        }
    };

    /**
     * opens new activity for the right age group
     */
    private void ChooseGameForAllLevels(){
        Intent intent = new Intent(this, ChooseGameForAllLevels.class);
        Bundle b = new Bundle();
        if(lvl == 1)b.putInt("key", 1); // Indicating level 1
        if(lvl == 2)b.putInt("key", 2); // Indicating level 2
        if(lvl == 3)b.putInt("key", 3); // Indicating level 3
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upphafsglugginn, menu);
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
