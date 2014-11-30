package com.example.liz.digipa;

import android.app.Fragment;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Charlene on 11/11/2014.
 */
public class CategoriesFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayAdapter<String> adapter;
    List<String> categories;
    List<Integer> colors;
    public String selectedCategory;
    String currSelectedCategory;
    Spinner categorySpinner;
    public static String categorySelected;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoriesfragment, container, false);

        categorySpinner = (Spinner) v.findViewById(R.id.categoriesSpinner);

        DPADataHandler handler = new DPADataHandler(getActivity());
        handler.open();
        Cursor categoryCursor = handler.returnCategories();
        categoryCursor.moveToFirst();
        categories = new ArrayList<String>();
        colors = new ArrayList<Integer>();
        Log.v("categories fragoment", Arrays.toString(categoryCursor.getColumnNames()) + "count " + categoryCursor.getCount());
        int categoryTitleIndex = categoryCursor.getColumnIndex(DigiPAContract.COLUMN_NAME_CATEGORY);
        do {
            categories.add(categoryCursor.getString(categoryTitleIndex));
            colors.add(categoryCursor.getInt(categoryTitleIndex+1));
        } while (categoryCursor.moveToNext());

        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        Bundle bundle = this.getArguments();
        
        if(bundle!= null) {
            currSelectedCategory = bundle.getString("SELECTED_CATEGORY");
        }
        categorySpinner.setSelection(adapter.getPosition(currSelectedCategory));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long i) {
                categorySelected = parent.getItemAtPosition(pos).toString();
                Log.v("categories fragoment", " category selected" + categorySelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                categorySelected = "Default";
            }
        });
        return v;
    }
    /*
    Context context, Uri uri, String[] projection,
    String selection, String[] selectionArgs, String sortOrder)

    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        CursorLoader cursorLoader = new CursorLoader(
                this,
                DigiPAContract.CONTENT_URI,
                ShopperProvider.TAG_COLUMNS,
                null,
                null,
                null);
        return cursorLoader;
    }
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        mAdapter.swapCursor(arg1);
    }

    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }
    private void fillSpinner() {

    }*/

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        selectedCategory = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}