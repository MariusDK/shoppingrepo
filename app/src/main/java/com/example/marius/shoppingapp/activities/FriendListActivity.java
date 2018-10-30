package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.adapters.ListFriendsAdapter;
import com.example.marius.shoppingapp.classes.UserData;
import com.example.marius.shoppingapp.providers.FriendsProvider;
import com.example.marius.shoppingapp.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity implements FriendsProvider.FriendListListener {
    private FriendsProvider friendsProvider;
    private ListView listView;
    private ListFriendsAdapter listFriendsAdapter;
    private List<UserData> userDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_friend_id);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userDataList = new ArrayList<>();
        listView = findViewById(R.id.list_friends_id);
        listFriendsAdapter = new ListFriendsAdapter(this);
        listView.setAdapter(listFriendsAdapter);
        friendsProvider = new FriendsProvider(this);
        friendsProvider.getAllFriend();

    }

    @Override
    public void getFirendList(List<UserData> list) {
        userDataList = list;
        for (UserData userData:userDataList)
        {
            listFriendsAdapter.add(userData);
        }
        UIUtils.setListViewHeightBasedOnItems(listView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                Intent intent = new Intent(this, ShoppingListActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
