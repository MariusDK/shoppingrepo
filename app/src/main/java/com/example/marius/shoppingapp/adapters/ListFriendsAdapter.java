package com.example.marius.shoppingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.classes.UserData;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsAdapter extends ArrayAdapter<UserData> {
    private Context context;
    private deleteItemListener deleteItemListener;
    private List<UserData> lists = new ArrayList<>();

    public ListFriendsAdapter(Context context) {
        super(context, R.layout.card_friend_list);
        this.context = context;
    }


    @Override
    public void add(@Nullable UserData object) {
        lists.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Nullable
    @Override
    public UserData getItem(int position) {
        return lists.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.cards_layout, parent, false);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UserData userData = getItem(position);
        holder.emailList.setText("Email: "+userData.getEmail());
        holder.deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteItemListener.deleteItemOnClick();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        ImageButton deleteListButton;
        TextView emailList;


        ViewHolder(View view) {
            deleteListButton = (ImageButton) view.findViewById(R.id.deleteFriendId);
            emailList = (TextView) view.findViewById(R.id.email_friend_id);
        }

    }

    public interface deleteItemListener {
        public void deleteItemOnClick(String id_list, String list_name);
    }

    @Override
    public void clear() {
        super.clear();
        lists.clear();
    }
}
