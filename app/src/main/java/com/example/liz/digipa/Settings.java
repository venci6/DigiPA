package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Charlene on 11/14/2014.
 * Updated by Liz 11/21/2014.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    //When checkbox is clicked to enable FB sync, set EditText fields to prompt for auth. info
    public void onGetFBEvents(View v) {
        if(((CheckBox) v).isChecked()) {
            EditText getUName = (EditText)findViewById(R.id.FBusername);
            getUName.setHint(R.string.FBUNameHint);
            EditText getPword = (EditText)findViewById(R.id.FBpassword);
            getPword.setHint(R.string.FBPasswordHint);
        }
    }
}
