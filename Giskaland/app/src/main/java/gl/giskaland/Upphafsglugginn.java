package gl.giskaland;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
//import android.app.ActionBar;
//import android.support.v7.app.ActionBar;

public class Upphafsglugginn extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upphafsglugginn);

        ImageButton FirstButton;
        ImageButton SecondButton;
        ImageButton ThirdButton;
        FirstButton = (ImageButton) findViewById(R.id.buttonFirstLevel);
        FirstButton.setOnClickListener(gotoFirstClickListener);
        SecondButton = (ImageButton) findViewById(R.id.buttonSecondLevel);
        SecondButton.setOnClickListener(gotoSecondClickListener);
        ThirdButton = (ImageButton) findViewById(R.id.buttonThirdLevel);
        ThirdButton.setOnClickListener(gotoThirdClickListener);

    }

    // Opens up the FirstLevel
    // The Button with the id 'buttonFirstLevel' triggers
    // this function.
    public void openFirstLevelActivity (View view) {
        Intent intent = new Intent(this, FirstLevel.class);
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

   /** takki fyrir fyrsta hop
     *
     */
    View.OnClickListener gotoFirstClickListener  = new View.OnClickListener() {
                /**
                 * ef smellt er a nemandi takkan er kallad a fallid firstbutton()
                 * @param v view
                 */
                @Override
                public void onClick(View v) {

                    firstbutton();
                }
            };

    /**
     * opnar nyja gluggan fyrir fyrsta hop
     */
    private void firstbutton(){
        startActivity(new Intent(this, FirstLevel.class));

    };

    /** takki fyrir annan hop
     *
     */
    View.OnClickListener gotoSecondClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid secondbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            secondbutton();
        }
    };

    /**
     * opnar nyja gluggan fyrir annan hop
     */
    private void secondbutton(){
        startActivity(new Intent(this, SecondLevel.class));

    };

    /** takki fyrir tridja hop
     *
     */
    View.OnClickListener gotoThirdClickListener  = new View.OnClickListener() {
        /**
         * ef smellt er a nemandi takkan er kallad a fallid thirdbutton()
         * @param v view
         */
        @Override
        public void onClick(View v) {

            thirdbutton();
        }
    };

    /**
     * opnar nyja gluggan fyrir tridja hop
     */
    private void thirdbutton(){
        startActivity(new Intent(this, ThirdLevel.class));

    };
}
