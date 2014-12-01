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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoriesfragment, container, false);

        categorySpinner = (Spinner) v.findViewById(R.id.categoriesSpinner);

        DPADataHandler handler = new DPADataHandler(getActivity());
        handler.open();
        Cursor categoryCursor = handler.returnCategories();
        categoryCursor.moveToFirst();
        categories = new ArrayList<String>();
        colors = new ArrayList<String>();

        Log.v("categories fragoment", Arrays.toString(categoryCursor.getColumnNames()) + "count " + categoryCursor.getCount());
        int categoryTitleIndex = categoryCursor.getColumnIndex(DigiPAContract.DPACategory.COLUMN_NAME_CATEGORY_TITLE);
        do {
            categories.add(categoryCursor.getString(categoryTitleIndex));
            colors.add(categoryCursor.getString(categoryTitleIndex + 1));
        } while (categoryCursor.moveToNext());
        numCategories = categories.size();
        categories.add("Create a new Category");


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
                if(pos==numCategories) {
                    // TODO "create a new Category"
                    // need a dialog fragment....allow user to insert category name (must be unique) and a color
                    // will need some sort of color displayer
                    // insert into database
                    // refresh the fragment with the thing
                    Log.v("CategoriesFragment", "hi");
                    categorySelected = parent.getItemAtPosition(0).toString();
                } else {
                    posSelected = pos;
                    categorySelected = parent.getItemAtPosition(pos).toString();

                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor(colors.get(pos)));
                }
                Log.v("categories fragoment", " category selected" + categorySelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                categorySelected = "Default";
            }
        });
        return v;

        // int color = CategoriesFragment.colors.get(CategoriesFragment.categories.indexOf("Birthday"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("CATEGORY_POST", posSelected);
    }

}