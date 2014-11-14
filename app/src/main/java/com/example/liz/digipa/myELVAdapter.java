package com.example.liz.digipa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Created by Liz on 11/14/2014.
 */
public class myELVAdapter extends BaseExpandableListAdapter {
    private Context context;
    String[] parentArr;
    String[][] childrenArr;

    public myELVAdapter(Context context) {
        this.context=context;
        this.parentArr = new String[15];
        this.childrenArr = new String[15][15];
    }

    @Override
    public int getGroupCount() {
        return parentArr.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return childrenArr[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupIndex, boolean childIndex, View view, ViewGroup viewGroup) {
        TextView tvParent = new TextView(context);
        tvParent.setText(parentArr[groupIndex]);
        return tvParent;
    }

    @Override
    public View getChildView(int groupIndex, int childIndex, boolean b, View view, ViewGroup viewGroup) {
        TextView tvChild = new TextView(context);
        tvChild.setText(childrenArr[groupIndex][childIndex]);

        return tvChild;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}
