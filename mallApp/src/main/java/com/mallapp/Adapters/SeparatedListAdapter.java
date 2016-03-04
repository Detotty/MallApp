package com.mallapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mallapp.View.R;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by appster on 10/29/2015.
 */
public class SeparatedListAdapter extends BaseAdapter {

    public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
    public HeaderArrayAdapter headersAdapter;
    public final static int TYPE_SECTION_HEADER = 0;
    public String titleText[];
    private Context context;
    @SuppressWarnings("unused")
    private LayoutInflater mInflater;

    public SeparatedListAdapter(Context context, int size) {
        this.context = context;
        titleText = new String[size];
        mInflater = LayoutInflater.from(context);
    }

    public void addSection(String section, Adapter adapter, int sectionCounter) {
        titleText[sectionCounter] = section;
        headersAdapter = new HeaderArrayAdapter(context, titleText);
        this.sections.put(section, adapter);
    }

    public Object getItem(int position) {
        for (Object section : this.sections.keySet()) {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            /** check if position inside this section */
            if (position == 0)
                return section;
            if (position < size)
                return adapter.getItem(position - 1);

            /** otherwise jump into next section */
            position -= size;
        }
        return null;
    }

    public int getCount() {
        /** total together all sections, plus one for each section header */
        int total = 0;
        for (Adapter adapter : this.sections.values())
            total += adapter.getCount() + 1;
        return total;
    }

    public int getViewTypeCount() {
        /** assume that headers count as one, then total all sections */
        int total = 1;
        for (Adapter adapter : this.sections.values())
            total += adapter.getViewTypeCount();
        return total;
    }

    public int getItemViewType(int position) {
        int type = 1;
        for (Object section : this.sections.keySet()) {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            /** check if position inside this section */
            if (position == 0)
                return TYPE_SECTION_HEADER;
            if (position < size)
                return type + adapter.getItemViewType(position - 1);

            /** otherwise jump into next section */
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isEnabled(int position) {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int sectionnum = 0;
        for (Object section : this.sections.keySet()) {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;
            /** check if position inside this section */
            if (position == 0) {
                return headersAdapter.getView(sectionnum, convertView, parent);
            }
            if (position < size)
                return adapter.getView(position - 1, convertView, parent);

            /** otherwise jump into next section */
            position -= size;
            sectionnum++;
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public class HeaderArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public HeaderArrayAdapter(Context context, String[] values) {
            super(context, R.layout.list_header, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater
                    .inflate(R.layout.list_header, parent, false);
            TextView list_header_title = (TextView) rowView
                    .findViewById(R.id.list_header_title);
            list_header_title.setText(values[position]);
            return rowView;
        }
    }

}
