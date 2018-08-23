package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;

import java.util.ArrayList;

public class ItemsAddAdapter extends BaseAdapter {
    private ArrayList<Item> list;
    private LayoutInflater inflater;
    public ItemsAddAdapter(Context context,ArrayList<Item> items)
    {
        super();
        inflater = LayoutInflater.from(context);
        list = items;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null)
        {
            //LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cards_layout_items_add,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.ItemName.getEditText().setText(list.get(position).getName());
        //holder.ItemQuantity.getEditText().setText(""+list.get(position).getQuantity());
        return convertView;
    }


    static class ViewHolder
    {
        TextInputLayout ItemName;
        TextInputLayout ItemQuantity;

        ViewHolder(View view)
        {
            ItemName = (TextInputLayout)view.findViewById(R.id.item_name_id);
            ItemQuantity = (TextInputLayout)view.findViewById(R.id.item_quantity_id);
        }
    }
    public void addList(Item item)
    {
        list.add(item);
    }
    public void swapItems(ArrayList<Item> items) {
        this.list = items;
        notifyDataSetChanged();
    }
}
