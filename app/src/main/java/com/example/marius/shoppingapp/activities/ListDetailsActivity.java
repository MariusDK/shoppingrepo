package com.example.marius.shoppingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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

public class ListDetailsActivity extends AppCompatActivity implements ItemListProvider.getShoppingListListener,ItemProvider.getItemsListener{
    private LinearLayout linearView;
    ItemsDetailsAdapter adapter;
    ItemProvider provider;
    UserProvider userProvider;
    ItemListProvider itemListProvider;
    private String ShoppingListTitle;
    private TextView textView;
    private boolean list_status = true;
    private String id_list;
    private TextView locatieTextView;
    private TextView descriereTextView;
    private Button addNewItem;
    private Button saveListButton;
    private Switch aSwitch;
    private ArrayList<Item> items;
    private TextInputEditText numeInput;
    private TextInputEditText cantitateInput;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar_listDetails);
        //toolbar.setTitle(getResources().getString(R.string.details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id_list = intent.getStringExtra("listKey");
        //getSupportActionBar().setTitle(ShoppingListTitle);
        //toolbar.setTitle(ShoppingListTitle);
        linearView = findViewById(R.id.list_items_details_id);
        locatieTextView = findViewById(R.id.id_location_details);
        descriereTextView = findViewById(R.id.id_description_details);
        addNewItem = findViewById(R.id.add_new_item_details_id);
        saveListButton = findViewById(R.id.save_list_details_button);
        aSwitch = findViewById(R.id.switch1);
        progressBar = findViewById(R.id.progressBar_details);
        textView = findViewById(R.id.titlu_id);
        progressBar.setVisibility(View.INVISIBLE);
        userProvider = new UserProvider();
        itemListProvider = new ItemListProvider(this);

        System.out.println(id_list);
        itemListProvider.getShoppingListById(id_list,userProvider.getUserId());
        Item item;
        items = new ArrayList<>();
        item = new Item();
        items.add(item);
        provider = new ItemProvider(this);
        provider.getItems(id_list);
        provider.getItemArrayList();
        numeInput = findViewById(R.id.item_name_details_id);
        cantitateInput = findViewById(R.id.item_quantity_details_id);
        if (aSwitch!=null)
        {
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.println("afisaza valoare "+b);
                    if (b)
                    {
                        list_status = false;
                    }
                    else
                    {
                        list_status = true;
                    }
                }
            });
        }

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
                View v = LayoutInflater.from(ListDetailsActivity.this).inflate(R.layout.item_list_details,null);
                linearView.addView(v);
            }
        });
        saveListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                itemListProvider.statusChanger(id_list,list_status);
                int size = linearView.getChildCount();
                for (int i =0;i<size;i++)
                {
                    CardView cardView = (CardView) linearView.getChildAt(i);
                    ConstraintLayout constraintLayout = (ConstraintLayout) cardView.getChildAt(0);
                    TextInputLayout TextItemName = (TextInputLayout)constraintLayout.getChildAt(0);
                    TextInputLayout TextItemQuantity = (TextInputLayout)constraintLayout.getChildAt(1);
                    FrameLayout frameLayout =(FrameLayout) TextItemName.getChildAt(0);
                    EditText editTextName = (EditText) frameLayout.getChildAt(0);
                    String itemName = editTextName.getText().toString();
                    System.out.println(itemName);
                    FrameLayout frameLayout2 =(FrameLayout) TextItemQuantity.getChildAt(0);
                    EditText editTextQuantity = (EditText) frameLayout2.getChildAt(0);
                    String itemQuantity = editTextQuantity.getText().toString();
                    System.out.println(itemQuantity);
                    Item item = new Item();
                    item.setName(itemName);
                    item.setQuantity(Integer.parseInt(itemQuantity));
                    items.add(item);
                }
                provider.deleteListItems(items,id_list);
                provider.addListItems(items,id_list);
                Intent intent = new Intent(ListDetailsActivity.this,ShoppingListActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
            View v = LayoutInflater.from(ListDetailsActivity.this).inflate(R.layout.item_list_details,null);
            linearView.addView(v);
            setDataTOCardView(item1);
        }
        this.items.clear();
    }
}
