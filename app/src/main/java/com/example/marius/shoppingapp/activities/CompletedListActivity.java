package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;
import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;

import java.util.ArrayList;

public class CompletedListActivity extends AppCompatActivity implements ItemListProvider.getShoppingListListener,ItemProvider.getItemsListener{
    UserProvider userProvider;
    ItemListProvider itemListProvider;
    ItemProvider provider;
    private LinearLayout linearView;
    private Toolbar toolbar;
    private String id_list;
    private TextView locatieTextView;
    private TextView descriereTextView;
    private TextView textView;
    private String ShoppingListTitle;
    private ArrayList<Item> items;
    private TextInputEditText numeInput;
    private TextInputEditText cantitateInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_compListBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id_list = intent.getStringExtra("listKey");
        linearView = findViewById(R.id.list_items_comp_id);
        locatieTextView = findViewById(R.id.id_location_comp);
        descriereTextView = findViewById(R.id.id_description_comp);
        textView = findViewById(R.id.titlu_comp_id);

        userProvider = new UserProvider();
        itemListProvider = new ItemListProvider(this);

        itemListProvider.getShoppingListById(id_list);
        provider = new ItemProvider(this);
        provider.getItems(id_list);
        provider.getItemArrayList();
        numeInput = findViewById(R.id.item_name_details_id);
        cantitateInput = findViewById(R.id.item_quantity_details_id);
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
    public void finishGetShoppingList(ShoppingList shoppingList) {
        ShoppingListTitle = shoppingList.getNume();
        //toolbar.setTitle(ShoppingListTitle);
        //getSupportActionBar().setTitle(ShoppingListTitle);
        //this.setSupportActionBar(toolbar);
        textView.setText(shoppingList.getNume());
        locatieTextView.setText(shoppingList.getLocation());
        descriereTextView.setText(shoppingList.getDescription());
    }


    public void setDataTOCardView(Item i)
    {

        CardView cardView = (CardView) linearView.getChildAt(items.indexOf(i));
        ConstraintLayout constraintLayout = (ConstraintLayout) cardView.getChildAt(0);
        TextInputLayout TextItemName = (TextInputLayout) constraintLayout.getChildAt(0);
        TextInputLayout TextItemQuantity = (TextInputLayout) constraintLayout.getChildAt(1);
        FrameLayout frameLayout = (FrameLayout) TextItemName.getChildAt(0);
        EditText editTextName = (EditText) frameLayout.getChildAt(0);
        editTextName.setText(i.getName().toString());
        FrameLayout frameLayout2 = (FrameLayout) TextItemQuantity.getChildAt(0);
        EditText editTextQuantity = (EditText) frameLayout2.getChildAt(0);
        editTextQuantity.setText(""+i.getQuantity());
    }
    @Override
    public void getItemListener(ArrayList<Item> items) {
        this.items = items;
        for (Item item1:items)
        {
            View v = LayoutInflater.from(CompletedListActivity.this).inflate(R.layout.item_list_comp,null);
            linearView.addView(v);
            setDataTOCardView(item1);
        }
        this.items.clear();
    }
}
