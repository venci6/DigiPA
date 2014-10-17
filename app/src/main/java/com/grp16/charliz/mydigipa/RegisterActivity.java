package com.grp16.charliz.mydigipa;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Charlene on 10/17/2014.
 */
public class RegisterActivity extends Activity {

    EditText username;
    EditText email;
    EditText fname;
    EditText lname;
    EditText password;
    Button btn_register;
    Button btn_cancel_register;
    TextView register_err_msg;


    // JSON Response node names
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_ERROR = "error";
    private static final String KEY_ERROR_MSG = "error_msg";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CREATED_AT = "created_at";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //importing all assets
        username = (EditText) findViewById(R.id.register_username);
        email = (EditText) findViewById(R.id.register_email);
        fname = (EditText) findViewById(R.id.register_fname);
        lname = (EditText) findViewById(R.id.register_lname);
        password = (EditText) findViewById(R.id.register_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_cancel_register = (Button) findViewById(R.id.btn_cancel_register);
        register_err_msg =



    }
}
