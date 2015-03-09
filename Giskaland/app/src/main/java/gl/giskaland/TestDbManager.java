package gl.giskaland;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class TestDbManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db_manager);

        DbManager dbManager = new DbManager(this);

        // copy assets DB to app DB
        try {
            dbManager.createDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            dbManager.openDatabase();
        } catch (SQLiteException sqle) {
            throw sqle;
        }

        List<String> list = dbManager.getData("Questions", 0, 7);
        for (int i = 0; i < list.size(); i++)
            System.out.println("Questions " + list.get(i));

        dbManager.updateScore("MathScores", 0, 1, 100, false);

        list = dbManager.getData("MathScores", 0, 7);
        for (int i = 0; i < list.size(); i++)
            System.out.println("MathScores " + list.get(i));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_db_manager, menu);
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
