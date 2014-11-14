package com.example.liz.digipa;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Charlene on 11/11/2014.
 */
public class RegisterFragment extends Fragment implements ValidationListener {
    private final String TAG = month.class.getSimpleName();

    // Form Validation
    @Required(order = 1)
    private EditText regUname;

    @Required(order = 2)
    @Email(order = 3, message ="Not a valid email format.")
    private EditText regEmail;

    @Required(order = 4)
    private EditText regFname;

    @Required(order = 5)
    private EditText regLname;

    @Password(order  = 6)
    @TextRule(order = 7, minLength = 5, message = "Password must be at least 5 characters.")
    private EditText regPWD;

    @ConfirmPassword(order = 8)
    private EditText regConfirm;

    private Validator validator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.registerfragment, container, false);

        regUname = (EditText) v.findViewById(R.id.register_username);
        regPWD = (EditText) v.findViewById(R.id.register_password);
        regConfirm = (EditText) v.findViewById(R.id.register_confirm);
        regFname = (EditText) v.findViewById(R.id.register_fname);
        regLname = (EditText) v.findViewById(R.id.register_lname);
        regEmail = (EditText) v.findViewById(R.id.register_email);

        Button registerBtn = (Button) v.findViewById(R.id.btn_register);
        Button cancelBtn = (Button) v.findViewById(R.id.btn_cancel_register);

        validator = new Validator(this);
        validator.setValidationListener(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        return v;
    }

    /*
        Form Validation: Called when ALL views pass ALL validations
     */
    public void onValidationSucceeded() {
        Log.v(TAG, "Success");
        Toast.makeText(getActivity(), "Thank you for registering!", Toast.LENGTH_SHORT).show();

        // insert into database using databasehelper???? or sharedpreferences????
        createAccount();
    }

    /*
        Form Validation: Called when a Rule fails
     */
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();

        if(failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(message);
        } else {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(){
        String username = regUname.getText().toString();
        String password = regPWD.getText().toString();
        String firstName = regFname.getText().toString();
        String lastName = regLname.getText().toString();
        String email = regEmail.getText().toString();


//        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("Username", username);
//        editor.putString("Password", password);
//        editor.putString("First Name", firstName);
//        editor.putString("Last Name", lastName);
//        editor.putString("Email", email);
//        editor.commit();

        //.getBaseContext()?

        DPADataHandler mHelper = new DPADataHandler(getActivity());
        //mHelper.initializeCategories();

        Intent viewCalendar = new Intent(getActivity(), month.class);
        startActivity(viewCalendar);
        getActivity().finish();
    }

    public void goBack(){
        Intent returnToLogin = new Intent(getActivity(), Login.class);
        startActivity(returnToLogin);
    }
}
