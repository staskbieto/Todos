package com.example.stask.myapplication;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stask on 31/05/17.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;


    //BaseDadesAdapter bd = new BaseDadesAdapter(context);
    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public ItemAdapter(Context context, Cursor cs) {
        items = new ArrayList<Item>();
        while (cs.moveToNext()) {
            boolean bl = (cs.getInt(cs.getColumnIndex(BaseDadesAdapter.KEY_DONE))) == 1 ? true : false;
            Item it = new Item(cs.getString(cs.getColumnIndex(BaseDadesAdapter.KEY_TITTLE)),bl,cs.getInt(cs.getColumnIndex(BaseDadesAdapter.KEY_ROWID)));
            items.add(it);
        }
        this.context = context;

    }

    public void addTask(Item it){
        items.add(it);
        this.notifyDataSetChanged();
    }

    public void addTask(String title,boolean oc){
        int id = MainActivity.db.insertTask(title,oc);
        Item it = new Item(title,oc,id);
        items.add(it);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // Set data into the view.
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        Item item = this.items.get(position);
        tvTitle.setText(item.getTitle());
        tvTitle.setTextColor(Color.BLACK);

        Button ck = (Button) rowView.findViewById(R.id.eliminar);
        ck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.db.removeTask(items.get(position).id);
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        Button passar = (Button) rowView.findViewById(R.id.changeState);
        passar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item it = items.get(position);
                items.remove(position);
                if(it.getDone()){
                    it.setDone(!it.getDone());
                    MainActivity.db.updateTask(it.id,it.getDone());
                    MainActivity.llistaTodo.addTask(it);
                    notifyDataSetChanged();
                }else{
                    it.setDone(!it.getDone());
                    MainActivity.db.updateTask(it.id,it.getDone());
                    MainActivity.llistaDone.addTask(it);
                    notifyDataSetChanged();
                }

            }
        });
        return rowView;
    }

}