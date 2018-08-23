package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;

public class ListAdapter extends ArrayAdapter<ShoppingList>{

    public ListAdapter(Context context)
    {
        super(context, R.layout.cards_layout);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

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
        ShoppingList shoppingList = getItem(position);
        //Lista initializata
        holder.numeList.setText("Name: "+shoppingList.getNume());
        holder.locatieList.setText("Location: "+shoppingList.getLocation());
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

}
