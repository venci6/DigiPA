package com.example.liz.digipa;

import android.app.Activity;
import android.os.Bundle;

import android.app.FragmentManager;
import android.app.FragmentTransaction;


public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Create the fragment
        RegisterFragment registerFragment = new RegisterFragment();

        // Install the Register fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.accountDetails, registerFragment);
        fragmentTransaction.commit();
    }
}
