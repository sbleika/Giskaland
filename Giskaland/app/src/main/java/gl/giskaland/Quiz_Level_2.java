package gl.giskaland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Quiz_Level_2 extends ActionBarActivity {
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    RadioGroup.LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__level_2);




            popUp = new PopupWindow(this);
            layout = new LinearLayout(this);
            mainLayout = new LinearLayout(this);
            tv = new TextView(this);
            but = new Button(this);

            but.setText("Click Me");
            but.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (click) {
                        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                        popUp.update(50, 50, 300, 80);
                        click = false;
                    } else {
                        popUp.dismiss();
                        click = true;
                    }
                }

            });
            params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            tv.setText("Hi this is a sample text for popup window");
            layout.addView(tv, params);
            popUp.setContentView(layout);
            // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
            mainLayout.addView(but, params);
            setContentView(mainLayout);
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz__level_2, menu);
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
