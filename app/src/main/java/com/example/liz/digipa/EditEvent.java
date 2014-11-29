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
public class EditEvent extends Activity {
    private final String TAG = CreateEvent.class.getSimpleName();


    public static Button cancel, edit;

    DPADataHandler handler;

    int eventId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        Bundle extras;
        // Passed string of Date to view
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                eventId=-1;
            } else {
                eventId= extras.getInt("ID");
            }
        } else {
            eventId = savedInstanceState.getInt("ID");
        }

        // Create the fragment
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", eventId);
        eventDetailsFragment.setArguments(bundle);

        // Install the Register fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.EE_details, eventDetailsFragment);
        fragmentTransaction.commit();

        cancel = (Button) findViewById(R.id.EE_cancel);
        edit = (Button) findViewById(R.id.EE_edit);

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
                if(EventDetailsFragment.title.getText().toString().length() > 0) {
                    long result;
                    if((result = updateEvent()) == 1) {
                        Toast.makeText(getApplicationContext(), "Event successfully updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Warning: Updated " + result + " events", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Title must be populated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private long updateEvent() {
        handler = new DPADataHandler(this);
        handler.open();

        Events event = EventDetailsFragment.createEvent();

        long result = handler.updateEventFromId(eventId, event);

        handler.close();
        return result;
    }

}
