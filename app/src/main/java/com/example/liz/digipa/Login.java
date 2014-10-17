package com.example.liz.digipa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity {

    private Button mSignInButton;
    private Button mCreateAcctButton;

    EditText editPsswrd, editUsrNme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSignInButton = (Button)findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntoHome = new Intent(Login.this, LandingPage.class);
                startActivity(logIntoHome);
            }
        });
        mCreateAcctButton = (Button)findViewById(R.id.create_acct);
        mCreateAcctButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
                Intent register = new Intent(Login.this, Register.class);
               startActivity(register);
           }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
