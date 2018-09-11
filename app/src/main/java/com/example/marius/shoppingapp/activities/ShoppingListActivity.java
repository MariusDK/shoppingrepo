package com.example.marius.shoppingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
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

public class ShoppingListActivity extends AppCompatActivity implements ItemListProvider.getListListener, ListAdapter.deleteItemListener {
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
    private ImageButton deleteListItem;

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
        toolbar.setTitle("");
        this.setSupportActionBar(toolbar);
        shoppingLists = new ArrayList<>();
        providerList.getShoppingLists(provider.getUserId());
        listViewIncomplet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                logoutDialog();
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

    @Override
    public void deleteItemOnClick(final String id_list, String list_name) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the " + list_name +" list?").setTitle(R.string.delete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                providerList.deleteList(id_list);
                adapter1.clear();
                adapter1.notifyDataSetChanged();
                adapter2.clear();
                adapter2.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void logoutDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.logoutMessage).setTitle("Logout");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                provider.signOut();
                Intent intent = new Intent(ShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
