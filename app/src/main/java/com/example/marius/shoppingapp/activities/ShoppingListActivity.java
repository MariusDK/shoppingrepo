package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.adapters.ListAdapter;

import com.example.marius.shoppingapp.classes.ShoppingList;
import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.UserProvider;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity implements ItemListProvider.getListListener {
    private ListView listViewIncomplet;
    private UserProvider provider;
    private ListView listViewComplete;
    private ListAdapter adapter1;
    private ListAdapter adapter2;

    private ProgressBar progressBar;
    private ArrayList<ShoppingList> InCompletedList;
    private ArrayList<ShoppingList> CompletedList;
    private TextView currentListText;
    private TextView completeListText;
    private TextView noDataTextView;
    private ItemListProvider providerList;
    ArrayList<ShoppingList> shoppingLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        provider = new UserProvider();
        providerList = new ItemListProvider(this);
        listViewIncomplet = findViewById(R.id.list_id);
        listViewComplete = findViewById(R.id.list_id_completed);
        adapter1 = new ListAdapter(this);
        adapter2 = new ListAdapter(this);
        listViewIncomplet.setAdapter(adapter1);
        listViewComplete.setAdapter(adapter2);
        progressBar = findViewById(R.id.determinateBar);
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();

        completeListText = findViewById(R.id.completed_list_id_text);
        currentListText = findViewById(R.id.current_list_id_text);
        noDataTextView = findViewById(R.id.nodData_id);
        noDataTextView.setAlpha(0.0f);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_list_page_id);
        toolbar.setTitle(getResources().getString(R.string.listTitle));
        this.setSupportActionBar(toolbar);
        shoppingLists = new ArrayList<>();
        providerList.getShoppingLists(provider.getUserId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.sort_id:
                break;
            case R.id.logout_id:
                provider.signOut();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void finishListener(ArrayList<ShoppingList> lists) {
        progressBar.setVisibility(View.INVISIBLE);
        shoppingLists = lists;
        if (shoppingLists.isEmpty()) {
            completeListText.setAlpha(0.0f);
            currentListText.setAlpha(0.0f);
            noDataTextView.setAlpha(1.0f);
            Toast toast = Toast.makeText(this,getResources().getString(R.string.noData), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            completeListText.setAlpha(1.0f);
            currentListText.setAlpha(1.0f);
            noDataTextView.setAlpha(0.0f);
            for (ShoppingList shoppingList : shoppingLists) {
                if (shoppingList.isStatus())
                {
                    adapter1.add(shoppingList);
                }
                else
                {
                    adapter2.add(shoppingList);
                }
            }
        }
    }
}
