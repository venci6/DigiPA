package com.example.liz.digipa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Liz on 11/14/2014.
 */
public class myELVAdapter extends BaseExpandableListAdapter {
    private Context context;

    public List<String> listHeaders; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChildren;

    
    public myELVAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.context=context;
        this.listHeaders = listDataHeader;
        this.listDataChildren = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this.listDataChildren.get(this.listHeaders.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }



    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {

        //        TextView tvChild = new TextView(context);
        //        tvChild.setText(childrenArr[groupIndex][childIndex]);

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.dailyListItem);

        txtListChild.setText(childText);
        if(childPosition==0) {
            txtListChild.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //        return childrenArr[i].length;
        return this.listDataChildren.get(this.listHeaders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //        TextView tvParent = new TextView(context);
    //        tvParent.setText(parentArr[groupIndex]);
    //        return tvParent;
        //String itemId = (String) getGroup(groupPosition);
        String headerTitle = (String) getChild(groupPosition, 0);
        //String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.dailyListHeader);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
