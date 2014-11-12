package com.example.liz.digipa;

import android.app.Fragment;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;

import java.sql.SQLData;

/**
 * Created by Charlene on 11/11/2014.
 */
public class CategoriesFragment extends Fragment  {
    private SimpleCursorAdapter mAdapter;
    private LoaderManager loaderManager;
    private CursorLoader cursorLoader;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoriesfragment, container, false);

//        DPADataHandler db = new DPADataHandler(getActivity());
//
//        loaderManager = getLoaderManager();
//        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_spinner_item, null,
//                new String[] {DigiPAContract.COLUMN_NAME_CATEGORY}, new int[] {android.R.id.text1},0);
//        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner spinner = (Spinner) v.findViewById(R.id.categoriesSpinner);
//        spinner.setAdapter(mAdapter);

//        Cursor cursor_Names =  db.returnCategories();
//        CursorLoader
//        CursorAdapter mAdapter = new CursorAdapter(getActivity(), android.R.layout.simple_spinner_item, cursor_Names, columns, to);

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
}