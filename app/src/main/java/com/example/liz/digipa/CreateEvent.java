package com.example.liz.digipa;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Charlene on 11/28/2014.
 */
public class CreateEvent extends Activity {
    private final String TAG = CreateEvent.class.getSimpleName();


    public static Button cancel, create;

    DPADataHandler handler;

    String date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        // Potentially passed a date (if creating event from daily view)
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
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        FragmentManager fragmentManager = getFragmentManager();

        if(date!=null) {
            Bundle bundle = new Bundle();
            bundle.putString("ADDING_TO_DAY", date);
            eventDetailsFragment.setArguments(bundle);
        }

        if(fragmentManager.findFragmentByTag("ce_fragment")==null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.CE_details, eventDetailsFragment, "ce_fragment");
            fragmentTransaction.commit();
        }


        cancel = (Button) findViewById(R.id.CE_cancel);
        create = (Button) findViewById(R.id.CE_create);
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
                if(EventDetailsFragment.title.getText().toString().length() > 0) {

                    if(addEvent() != -1) {
                        Toast.makeText(getApplicationContext(),"Event successfully created!", Toast.LENGTH_SHORT).show();
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


    private long addEvent() {
        handler = new DPADataHandler(this);
        handler.open();

        Events event = EventDetailsFragment.createEvent();

        long result = handler.insertEvent(event);


        handler.close();
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("DAY_TO_ADD_TO", date);
    }

}
