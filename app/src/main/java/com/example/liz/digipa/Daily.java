package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static java.security.AccessController.getContext;


public class Daily extends Activity {

    TextView dateHeading;
    String dateChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        Bundle extras;


        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                dateChosen= null;
            } else {
                dateChosen= extras.getString("DAY_TO_VIEW");
            }
        } else {
            dateChosen= (String) savedInstanceState.getSerializable("DAY_TO_VIEW");
        }


        dateHeading = (TextView)findViewById(R.id.date);
        dateHeading.setText(dateChosen);

        instantiateEvents(dateChosen);
    }

    public boolean instantiateEvents(String date){
        DigiPAOpenHelper mHelper = new DigiPAOpenHelper(getBaseContext());
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
