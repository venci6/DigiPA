package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by Charlene on 11/14/2014.
 * Updated by Liz 11/21/2014.
 */
public class Settings extends Activity {
    EditText getUName;
    EditText getPword;
    CheckBox FBcheck;
    Button FBsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        getUName = (EditText)findViewById(R.id.FBusername);
        getPword = (EditText)findViewById(R.id.FBpassword);
        FBcheck = (CheckBox)findViewById(R.id.checkbox_getFB);
        FBsync = (Button)findViewById(R.id.getEventsButton);
    }

    //When checkbox is clicked to enable FB sync, set EditText fields to prompt for auth. info
    public void onGetFBEvents(View v) {
        if(((CheckBox) v).isChecked()) {
            getUName.setHint(R.string.FBUNameHint);
            getPword.setHint(R.string.FBPasswordHint);
        }
    }

    public void onFButtonClick(View v){
        String fbUsername = getUName.getText().toString();
        String fbPassword = getPword.getText().toString();
    }
}
