package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.adapters.ItemsDetailsAdapter;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;
import com.example.marius.shoppingapp.classes.User;
import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;

import java.util.ArrayList;

public class ListDetailsActivity extends AppCompatActivity implements ItemListProvider.getShoppingListListener{
    ListView listView;
    ItemsDetailsAdapter adapter;
    ItemProvider provider;
    UserProvider userProvider;
    ItemListProvider itemListProvider;
    private String id_list;
    private TextView locatieTextView;
    private TextView descriereTextView;
    private Button addNewItem;
    private Button saveListButton;
    private Switch aSwitch;
    private ArrayList<Item> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_listDetails);
        toolbar.setTitle(getResources().getString(R.string.details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//initializare id list
        listView = findViewById(R.id.list_items_details_id);
        locatieTextView = findViewById(R.id.id_location_details);
        descriereTextView = findViewById(R.id.id_description_details);
        addNewItem = findViewById(R.id.add_new_item_details_id);
        saveListButton = findViewById(R.id.save_list_details_button);
        aSwitch = findViewById(R.id.switch1);

        userProvider = new UserProvider();
        itemListProvider = new ItemListProvider(this);
        Intent intent = getIntent();
        id_list = intent.getStringExtra("listKey");
        System.out.println(id_list);
        itemListProvider.getShoppingListById(id_list,userProvider.getUserId());
        Item item;
        items = new ArrayList<>();
        item = new Item();
        items.add(item);
        provider = new ItemProvider();
        provider.getItems(id_list);
        items = provider.getItemArrayList();
        Item item1 = new Item();
        items.add(item1);
        adapter = new ItemsDetailsAdapter(this,R.layout.item_list_details,items);
        listView.setAdapter(adapter);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this,ShoppingListActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.add(null);
            }
        });
    }

    @Override
    public void finishGetShoppingList(ShoppingList shoppingList) {

        getSupportActionBar().setTitle(shoppingList.getNume());
        locatieTextView.setText(shoppingList.getLocation());
        descriereTextView.setText(shoppingList.getDescription());
    }
}
