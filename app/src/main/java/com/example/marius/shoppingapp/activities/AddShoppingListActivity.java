package com.example.marius.shoppingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.adapters.ItemsAddAdapter;
import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;

import java.util.ArrayList;


public class AddShoppingListActivity extends AppCompatActivity {
    private ItemsAddAdapter addAdapter;
    private TextInputLayout listNameInput;
    private TextInputLayout listLocationInput;
    private TextInputLayout listDescriptionInput;
    private UserProvider provider;
    private ItemListProvider listProvider;
    private Button addNewItem;
    private Button saveList;
    private ItemProvider itemProvider;
    private ArrayList<Item> items;
    private LinearLayout linearView;
    private CardView cardView;
    private Toolbar toolbar;
    private TextInputLayout numeInput;
    private TextInputLayout cantitateInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shopping_list);
        listNameInput = findViewById(R.id.listName_id);
        listLocationInput = findViewById(R.id.Location_id);
        listDescriptionInput = findViewById(R.id.description_id);
        addNewItem = findViewById(R.id.add_item_id);
        saveList = findViewById(R.id.save_list_id);
        linearView = findViewById(R.id.list_items_id);
        toolbar = findViewById(R.id.toolbar_add_sl_id);
        toolbar.setTitle(getResources().getString(R.string.AddShoppingList));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items = new ArrayList<>();
        View v = LayoutInflater.from(AddShoppingListActivity.this).inflate(R.layout.cards_layout_items_add,null);

        linearView.addView(v);
        numeInput = findViewById(R.id.item_name_id);
        cantitateInput = findViewById(R.id.item_quantity_id);
        itemProvider = new ItemProvider();
        provider = new UserProvider();
        listProvider= new ItemListProvider();
    }
    @Override
    public void onResume() {
        super.onResume();
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = LayoutInflater.from(AddShoppingListActivity.this).inflate(R.layout.cards_layout_items_add,null);
                linearView.addView(v);

            }
        });
        saveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String nume = listNameInput.getEditText().getText().toString();
               String id_user=provider.getUserId();
               String description = listDescriptionInput.getEditText().getText().toString();
               String location = listLocationInput.getEditText().getText().toString();
               String id_list=listProvider.createList(nume,location,description,id_user);
               int size = linearView.getChildCount();
               for (int i =0;i<size;i++)
                {

                    CardView cardView = (CardView) linearView.getChildAt(i);
                    ConstraintLayout constraintLayout = (ConstraintLayout) cardView.getChildAt(0);
                    TextInputLayout TextItemName = (TextInputLayout)constraintLayout.getChildAt(0);
                    TextInputLayout TextItemQuantity = (TextInputLayout)constraintLayout.getChildAt(1);
                    FrameLayout frameLayout =(FrameLayout) TextItemName.getChildAt(0);
                    TextInputEditText editTextName = (TextInputEditText) frameLayout.getChildAt(0);
                    String itemName = editTextName.getText().toString();
                    System.out.println(itemName);
                    FrameLayout frameLayout2 =(FrameLayout) TextItemQuantity.getChildAt(0);
                    TextInputEditText editTextQuantity = (TextInputEditText) frameLayout2.getChildAt(0);
                    String itemQuantity = editTextQuantity.getText().toString();
                    System.out.println(itemQuantity);
                    Item item = new Item();
                    item.setName(itemName);

                    if (!itemQuantity.equals("")) {
                        try
                        {
                            item.setQuantity(Integer.parseInt(itemQuantity));
                        }
                        catch (NumberFormatException nfe)
                        {
                            Toast.makeText(AddShoppingListActivity.this,"Invalid Quantity format! ",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        item.setQuantity(0);
                    }
                    items.add(item);
                }
                itemProvider.addListItems(items,id_list);
            }
        });
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
}
