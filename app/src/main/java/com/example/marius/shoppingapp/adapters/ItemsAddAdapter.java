package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
    private Context context;
    private ArrayList<Item> list;
    private LayoutInflater inflater;
    private Item item;
    public ItemsAddAdapter(Context context, ArrayList<Item> list)
    {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cards_layout_items_add,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        item = list.get(position);
        holder.ItemName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setName(charSequence.toString());
                System.out.println(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.ItemQuantity.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                item.setQuantity(Integer.parseInt(charSequence.toString()));
                System.out.println(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    public void swapItems(ArrayList<Item> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    public ArrayList<Item> getList() {
        return list;
    }

    public Item getItem() {
        return item;
    }
}
