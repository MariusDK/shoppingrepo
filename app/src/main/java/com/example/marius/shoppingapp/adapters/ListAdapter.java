package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.activities.CompletedListActivity;
import com.example.marius.shoppingapp.activities.ListDetailsActivity;
import com.example.marius.shoppingapp.activities.ShoppingListActivity;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;

public class ListAdapter extends ArrayAdapter<ShoppingList>{

    private Context context;
    private deleteItemListener deleteItemListener;
    public ListAdapter(Context context)
    {
        super(context, R.layout.cards_layout);
        this.context = context;
        deleteItemListener = (deleteItemListener)context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.cards_layout, parent,false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ShoppingList shoppingList = getItem(position);
        //Lista initializata
        holder.numeList.setText("Name: "+shoppingList.getNume());
        holder.locatieList.setText("Location: "+shoppingList.getLocation());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shoppingList.isStatus()) {
                    Intent intent = new Intent(context, ListDetailsActivity.class);
                    intent.putExtra("listKey", shoppingList.getIdList());
                    context.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, CompletedListActivity.class);
                    intent.putExtra("listKey", shoppingList.getIdList());
                    context.startActivity(intent);
                }
            }
        });
        holder.deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemListener.deleteItemOnClick(shoppingList.getIdList(),shoppingList.getNume());
            }
        });
        return convertView;
    }


    static class ViewHolder{
        ImageButton deleteListButton;
        TextView numeList;
        TextView locatieList;


        ViewHolder(View view)
        {
            deleteListButton = (ImageButton)view.findViewById(R.id.deleteListButton);
            numeList = (TextView)view.findViewById(R.id.numeListaId);
            locatieList = (TextView)view.findViewById(R.id.locatieListaId);
        }

    }
    public interface deleteItemListener
    {
        public void deleteItemOnClick(String id_list, String list_name);
    }



}
