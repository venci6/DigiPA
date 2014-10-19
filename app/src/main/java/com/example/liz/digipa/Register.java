package com.example.liz.digipa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Register extends Activity {
    private Button btn_createAcct;
    private EditText regUname;
    private EditText regPWD;
    private EditText regFname;
    private EditText regLname;
    private EditText regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUname = (EditText)findViewById(R.id.register_username);
        regPWD = (EditText)findViewById(R.id.register_password);
        regFname = (EditText)findViewById(R.id.register_fname);
        regLname = (EditText)findViewById(R.id.register_lname);
        regEmail = (EditText)findViewById(R.id.register_email);

        btn_createAcct = (Button)findViewById(R.id.btn_register);
        btn_createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("Username", regUname.getText().toString());
                editor.putString("Password",regPWD.getText().toString());
                editor.putString("First Name",regFname.getText().toString());
                editor.putString("Last Name",regLname.getText().toString());
                editor.putString("Email",regEmail.getText().toString());
                editor.commit();

                Intent registerAcct = new Intent(Register.this, LandingPage.class);
                startActivity(registerAcct);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
