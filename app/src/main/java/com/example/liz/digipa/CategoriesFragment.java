package com.example.liz.digipa;

import android.app.Fragment;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.TextView;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Charlene on 11/11/2014.
 */
public class CategoriesFragment extends Fragment  {
    private ArrayAdapter<String> adapter;
    static List<String> categories;
    static List<String> colors;
    public String selectedCategory;
    int posSelected;
    String currSelectedCategory;
    Spinner categorySpinner;
    int numCategories;
    public static String categorySelected;
    /*
        To be used in: Creating/Editing Events/Tasks & in Settings
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoriesfragment, container, false);

        categorySpinner = (Spinner) v.findViewById(R.id.categoriesSpinner);
        categories = new ArrayList<String>();
        colors = new ArrayList<String>();


        // get the categories & their assigned colors from database
        DPADataHandler handler = new DPADataHandler(getActivity());
        handler.open();

        Cursor categoryCursor = handler.returnCategories();
        try {
            categoryCursor.moveToFirst();

            int categoryTitleIndex = categoryCursor.getColumnIndex(DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE);

            do {
                categories.add(categoryCursor.getString(categoryTitleIndex));
                colors.add(categoryCursor.getString(categoryTitleIndex + 1));
            } while (categoryCursor.moveToNext());

        } finally {
            categoryCursor.close();
        }

        numCategories = categories.size();
        categories.add("Create a new Category");

        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // if Editing something; need to load what was previously selected
        Bundle bundle = this.getArguments();
        if(bundle!= null) {
            currSelectedCategory = bundle.getString("SELECTED_CATEGORY");
        }
        categorySpinner.setSelection(adapter.getPosition(currSelectedCategory));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long i) {
                if(pos==numCategories) {
                    // TODO "create a new Category"
                    // need a dialog fragment....allow user to insert category name (must be unique) and a color
                    // will need some sort of color displayer
                    // insert into database
                    // refresh the fragment with the thing
                    Log.v("CategoriesFragment", "Clicked on create a new category! I'm not an actual category, so just going to select Default for now");
                    categorySelected = parent.getItemAtPosition(0).toString();
                } else {
                    posSelected = pos;
                    categorySelected = parent.getItemAtPosition(pos).toString();

                    // when a category is selected, display its color in the spinner
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor(colors.get(pos)));
                }
                Log.v("Categories Fragment", " category selected=" + categorySelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                categorySelected = "Default";
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("CATEGORY_POST", posSelected);
    }

}
