package com.example.a6000832.ganreg.Auxiliares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.a6000832.ganreg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose Eduardo Martin.
 */

//Esta clase es para, en un Spinner, mostrar TODAS las sugerencias que contengan lo que ya se ha escrito en un autocompletetextview

public class CustomArrayAdapter extends ArrayAdapter<String> {

    Context context;
    int resource, textViewResourceId;
    List<String> items, tempItems, sugerencias;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, List<String> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items);
        sugerencias = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.autocomplete_item, parent, false);
        }
        String names = items.get(position);
        if (names != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(names);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    //Redefino el filtro personalizado que usará este adaptador para la búsqueda de coincidencias
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((String) resultValue);
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
/*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
 */
        }

        //Cada vez que escriba un caracter nuevo, refrescaré los resultados del array de resultados
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<String> filterList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String names : filterList) {
                    add(names);
                    notifyDataSetChanged();
                }
            }
        }
    };
}