package com.example.liz.digipa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends Activity {
    //TAG string used for debugging
    private static String TAG = "Register";
    //Welcomes user back when they've left or paused Register activity
    private static String mssg = "Welcome Back!";
    private boolean activityHasBeenPaused = false;

    //UI widgets for collecting user account information
    private EditText regUname;
    private EditText regPWD;
    private EditText regFname;
    private EditText regLname;
    private EditText regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate() Restoring previous state");
            /* restore state */
        } else {
            Log.d(TAG, "onCreate() No saved state available");
            /* initialize app */
        }

        //Display activity_register on screen
        setContentView(R.layout.activity_register);
    }

    public void submitInfo(View v){
        regUname = (EditText)findViewById(R.id.register_username);
        regPWD = (EditText)findViewById(R.id.register_password);
        regFname = (EditText)findViewById(R.id.register_fname);
        regLname = (EditText)findViewById(R.id.register_lname);
        regEmail = (EditText)findViewById(R.id.register_email);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Username", regUname.getText().toString());
        editor.putString("Password", regPWD.getText().toString());
        editor.putString("First Name", regFname.getText().toString());
        editor.putString("Last Name", regLname.getText().toString());
        editor.putString("Email", regEmail.getText().toString());
        editor.commit();

        Intent registerAcct = new Intent(Register.this, LandingPage.class);
        startActivity(registerAcct);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    //Clear password if user leaves activity
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        regPWD = (EditText)findViewById(R.id.register_password);
        regPWD.setSaveEnabled(false);
    }

    //aHBP variable set to true when activity has been previously paused (not just created)
    @Override
    protected void onRestoreInstanceState(Bundle state){
        super.onRestoreInstanceState(state);
        Log.d(TAG, "Inside onRestoreInstanceState()");
        activityHasBeenPaused = true;
    }

    //If activity has been paused, a welcome back message will be displayed once user returns
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        if(activityHasBeenPaused == true){
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, mssg, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void goBack(View v){
        Intent returnToLogin = new Intent(Register.this, Login.class);
        startActivity(returnToLogin);
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
