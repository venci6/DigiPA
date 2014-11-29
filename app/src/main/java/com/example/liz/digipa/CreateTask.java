package com.example.liz.digipa;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Charlene on 11/28/2014.
 */
public class CreateTask extends Activity {
    private final String TAG = CreateTask.class.getSimpleName();

    public static Button cancel, create;
    DPADataHandler handler;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        // Potentially passed a date (if creating task from daily view)
        Bundle extras;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                date= null;
            } else {
                date = extras.getString("DAY_TO_ADD_TO");
            }
        } else {
            date = (String) savedInstanceState.getSerializable("DAY_TO_ADD_TO");
        }

        // Create the fragment
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        if(date!=null) {
            Bundle bundle = new Bundle();
            bundle.putString("ADDING_TO_DAY", date);
            taskDetailsFragment.setArguments(bundle);
        }

        // Install the Event Details fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.CT_details, taskDetailsFragment);
        fragmentTransaction.commit();

        cancel = (Button) findViewById(R.id.CT_cancel);
        create = (Button) findViewById(R.id.CT_create);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if title is populated
                if(TaskDetailsFragment.title.getText().toString().length() > 0) {
                    if(addTask() != -1) {
                        Toast.makeText(getApplicationContext(),"Task successfully created!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Error creating event", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Title must be populated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private long addTask() {
        handler = new DPADataHandler(this);
        handler.open();

        Tasks task = TaskDetailsFragment.createTask();

        long result = handler.insertTask(task);


        handler.close();
        return result;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("DAY_TO_ADD_TO", date);
    }
}
