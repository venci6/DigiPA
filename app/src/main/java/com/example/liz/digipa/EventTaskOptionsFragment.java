package com.example.liz.digipa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by Charlene on 11/14/2014.
 */
public class EventTaskOptionsFragment extends DialogFragment {
    int id;

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        // Passed string of Date to view

        id = getArguments().getInt("ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_options);
        builder.setItems(R.array.eventtask_options_array, new DialogInterface.OnClickListener() {
            public void onClick (DialogInterface dialog, int which) {
                if(which == 0) {
                    Log.v("options fragment", "clicked on edit! editing " + id);
                } else {
                    Log.v("options fragment", "clicked on delete!");
                }
            }
        });



        return builder.create();
    }
}
