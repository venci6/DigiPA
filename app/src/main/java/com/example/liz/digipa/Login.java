package com.example.liz.digipa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends Activity {

    private EditText username, password;

    private Button mSignInButton;
    private Button mCreateAcctButton;

    String psswrd, usrNme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editUserName);
        password = (EditText)findViewById(R.id.editPassword);

        TextWatcher textWatcher = new TextWatcher() {
            @Override public void afterTextChanged(Editable s) {
                enableLoginButton();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }
            @Override public void onTextChanged(CharSequence s, int start, int count,int after) {  }
        };

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        mSignInButton = (Button)findViewById(R.id.sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences settings = getSharedPreferences("UserInfo", 0);
//
//                psswrd = (password).getText().toString();
//                usrNme = (username).getText().toString();
//                String storedUsrNme = settings.getString("Username", "");
//                String storedPsswrd = settings.getString("Password", "");
//                if (storedUsrNme.equals(usrNme) && storedPsswrd.equals(psswrd) && !usrNme.isEmpty()) {
                    Intent logIntoHome = new Intent(Login.this, month.class);
                    startActivity(logIntoHome);
//                }
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


    private void enableLoginButton() {
        if(username.getText().length() > 0 && password.getText().length() > 0) {
            mSignInButton.setEnabled(true);
        } else {
            mSignInButton.setEnabled(false);
        }
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
