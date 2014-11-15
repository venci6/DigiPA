package com.example.liz.digipa;

import android.app.Activity;
import android.content.Context;
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


public class Login extends Activity implements View.OnClickListener {

    private EditText username, password;

    private Button mSignInButton;
    private Button mCreateAcctButton;
    SharedPreferences sharedpreferences;
    public static final String MYPREFERENCES = "MyPrefs";
    public static final String name = "nameKey";
    public static final String pass = "passwordKey";

    String psswrd, usrNme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if logged in...go to monthly view
        sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        if(sharedpreferences.contains(name) && sharedpreferences.contains(pass)) {
            Intent goToCalendar = new Intent(Login.this, month.class);
            startActivity(goToCalendar);
            finish();
        } else {
            setContentView(R.layout.activity_login);

            username = (EditText) findViewById(R.id.editUserName);
            password = (EditText) findViewById(R.id.editPassword);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    enableLoginButton();
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int count, int after) {
                }
            };

            username.addTextChangedListener(textWatcher);
            password.addTextChangedListener(textWatcher);


            mSignInButton = (Button) findViewById(R.id.sign_in);
            mCreateAcctButton = (Button) findViewById(R.id.create_acct);

            mSignInButton.setOnClickListener(this);
            mCreateAcctButton.setOnClickListener(this);
        }
    }


    private void enableLoginButton() {
        if(username.getText().length() > 0 && password.getText().length() > 0) {
            mSignInButton.setEnabled(true);
        } else {
            mSignInButton.setEnabled(false);
        }
    }

    @Override
    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.sign_in:
                /* at some point will need to verify username / password
                String storedUsrNme = settings.getString("Username", "");
                String storedPsswrd = settings.getString("Password", "");
                if (storedUsrNme.equals(usrNme) && storedPsswrd.equals(psswrd)) {
                    Intent logIntoHome = new Intent(Login.this, month.class);
                    startActivity(logIntoHome);
                }*/


                SharedPreferences.Editor editor = sharedpreferences.edit();
                psswrd = password.getText().toString();
                usrNme = username.getText().toString();

                editor.putString(name, usrNme);
                editor.putString(pass, psswrd);
                editor.commit();

                Intent logIntoHome = new Intent(Login.this, month.class);
                startActivity(logIntoHome);
                finish();
                break;
            case R.id.create_acct:
                Intent register = new Intent(Login.this, Register.class);
                startActivity(register);
                break;
        }
    }
}
