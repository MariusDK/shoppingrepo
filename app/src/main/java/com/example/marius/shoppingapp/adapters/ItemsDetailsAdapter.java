package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.classes.Item;

import java.util.ArrayList;

public class ItemsDetailsAdapter extends ArrayAdapter<Item>{
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Item> items;

    public ItemsDetailsAdapter(@NonNull Context context, int resource,ArrayList<Item> items) {
        super(context, resource);
        this.mContext = context;
        this.items = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_list_details,null);

            holder = new ViewHolder();
            holder.deleteItemButton = convertView.findViewById(R.id.delete_item_button);
            holder.numeInputItem = convertView.findViewById(R.id.item_name_details_id);
            holder.quantityInputItem = convertView.findViewById(R.id.item_quantity_details_id);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = items.get(position);
        holder.quantityInputItem.getEditText().setText(item.getQuantity()+"");
        holder.numeInputItem.getEditText().setText(item.getName());

        holder.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.quantityInputItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    final int position = view.getId();
                    final TextInputLayout Caption = (TextInputLayout) view;
                    items.get(position).setQuantity(Integer.parseInt(Caption.getEditText().getText().toString()));
                }
            }
        });
        holder.numeInputItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    final int position = view.getId();
                    final TextInputLayout Caption = (TextInputLayout) view;
                    items.get(position).setName(Caption.getEditText().getText().toString());
                }
            }
        });
        return convertView;
    }
    static class ViewHolder {
        TextInputLayout numeInputItem;
        TextInputLayout quantityInputItem;
        ImageButton deleteItemButton;
    }
}
