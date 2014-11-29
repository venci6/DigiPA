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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        // Create the fragment
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_task, menu);
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
