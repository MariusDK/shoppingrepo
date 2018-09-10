package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private FloatingActionButton buttonAdd;
    private ProgressBar progressBar;
    private ArrayList<ShoppingList> InCompletedList;
    private ArrayList<ShoppingList> CompletedList;
    private TextView currentListText;
    private TextView completeListText;
    private TextView noDataTextView;
    private FragmentManager fragmentManager;
    private ItemListProvider providerList;

    ArrayList<ShoppingList> shoppingLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        provider = new UserProvider();
        fragmentManager = getSupportFragmentManager();
        providerList = new ItemListProvider(this);
        listViewIncomplet = findViewById(R.id.list_id);
        listViewComplete = findViewById(R.id.list_items_id);
        adapter1 = new ListAdapter(this);
        adapter2 = new ListAdapter(this);
        listViewIncomplet.setAdapter(adapter1);
        listViewComplete.setAdapter(adapter2);
        progressBar = findViewById(R.id.determinateBar);
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        buttonAdd = findViewById(R.id.floatingActionButton2);
        completeListText = findViewById(R.id.completed_list_id_text);
        currentListText = findViewById(R.id.current_list_id_text);
        noDataTextView = findViewById(R.id.nodData_id);
        completeListText.setVisibility(View.INVISIBLE);
        currentListText.setVisibility(View.INVISIBLE);
        noDataTextView.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_list_page_id);
        toolbar.setTitle(getResources().getString(R.string.listTitle));
        this.setSupportActionBar(toolbar);
        shoppingLists = new ArrayList<>();
        providerList.getShoppingLists(provider.getUserId());
        //listViewIncomplet.setClickable(true);
        listViewIncomplet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //int position = listViewIncomplet.getSelectedItemPosition();
                ShoppingList shoppingList = (ShoppingList) listViewIncomplet.getItemAtPosition(i);
                Intent intent = new Intent(ShoppingListActivity.this, ListDetailsActivity.class);
                intent.putExtra("listKey", shoppingList.getIdList());
                intent.putExtra("titleShoppingList",shoppingList.getNume());
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this, AddShoppingListActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
            completeListText.setVisibility(View.INVISIBLE);
            currentListText.setVisibility(View.INVISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
            //Toast toast = Toast.makeText(this,getResources().getString(R.string.noData), Toast.LENGTH_LONG);
            //toast.setGravity(Gravity.CENTER, 0, 0);
            //toast.show();
        }
        else {
            completeListText.setVisibility(View.VISIBLE);
            currentListText.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.INVISIBLE);
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
