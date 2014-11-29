package com.example.liz.digipa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Charlene on 11/14/2014.
 */
public class EventTaskOptionsFragment extends DialogFragment {
    static int itemId, eventTaskMode;
    // 1 - event; 2 - task

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        // Passed string of Date to view

        itemId = getArguments().getInt("ID");
        eventTaskMode = getArguments().getInt("EVENT_OR_TASK");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_options);
        builder.setItems(R.array.eventtask_options_array, new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                if (which == 0) {
                    Log.v("options fragment", "clicked on edit! editing " + itemId);
                    switch (eventTaskMode) {
                        case 1:
                            Intent editEvent = new Intent(getActivity(), EditEvent.class);
                            editEvent.putExtra("ID", itemId);
                            startActivity(editEvent);
                            break;
                        case 2:
                            Intent editTask = new Intent(getActivity(), EditTask.class);
                            editTask.putExtra("ID", itemId);
                            startActivity(editTask);
                            break;
                        default:
                            Log.v("fragment", "unexpected eventTaskMode="+eventTaskMode);
                    }

                } else {
                    Log.v("options fragment", "clicked on delete!");
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    confirmDeleteDialogFragment fragment = new confirmDeleteDialogFragment();

                    fragment.show(getFragmentManager(),"confirmDeleteDialog");
                }
            }
        });

        return builder.create();
    }

    public static class confirmDeleteDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //final int eventToDeleteId = getArguments().getInt("ID");
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Add the buttons

            builder.setMessage("Please confirm that you would like to delete this.");

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    DPADataHandler handler = new DPADataHandler(getActivity());
                    handler.open();

                    int result = -1;

                    switch(eventTaskMode) {
                        case 1:
                            result = handler.deleteEvent(itemId);
                            break;
                        case 2:
                            result = handler.deleteTask(itemId);
                            break;

                    }

                    if(result!=1) {
                        Toast.makeText(getActivity(),"Warning: deleted " + result, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Delete successful!", Toast.LENGTH_SHORT).show();
                    }
                    handler.close();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog

                }
            });

            return builder.create();
        }
    }
}
