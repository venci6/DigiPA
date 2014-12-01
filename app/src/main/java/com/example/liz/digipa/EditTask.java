package com.example.liz.digipa;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Charlene on 11/28/2014.
 */
public class EditTask extends Activity {
    private final String TAG = EditTask.class.getSimpleName();


    public static Button cancel, edit;

    DPADataHandler handler;

    int taskId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);

        Bundle extras;
        // Passed string of Date to view
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                taskId=-1;
            } else {
                taskId= extras.getInt("ID");
            }
        } else {
            taskId = savedInstanceState.getInt("ID");
        }

        // Create the fragment
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", taskId);
        taskDetailsFragment.setArguments(bundle);

        // Install the Register fragment
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.findFragmentByTag("et_fragment")==null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.ET_details, taskDetailsFragment, "et_fragment");
            fragmentTransaction.commit();
        }

        cancel = (Button) findViewById(R.id.ET_cancel);
        edit = (Button) findViewById(R.id.ET_edit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if title is populated
                if(TaskDetailsFragment.title.getText().toString().length() > 0) {
                    long result;
                    if((result = updateTask()) == 1) {
                        Toast.makeText(getApplicationContext(), "Task successfully updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Warning: Updated " + result + " tasks", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Title must be populated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private long updateTask() {
        handler = new DPADataHandler(this);
        handler.open();

        Tasks task= TaskDetailsFragment.createTask();

        long result = handler.updateTaskFromId(taskId, task);

        handler.close();
        return result;
    }

}
