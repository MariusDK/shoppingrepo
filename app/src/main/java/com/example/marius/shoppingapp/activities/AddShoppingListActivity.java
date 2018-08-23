package com.example.marius.shoppingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ListView listView;
    private ArrayAdapter<Item> arrayAdapter;
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
        listView = findViewById(R.id.list_items_id);
        items = new ArrayList<>();
        addAdapter = new ItemsAddAdapter(this, items);
        //arrayAdapter = new ArrayAdapter<Item>(this,R.layout.cards_layout_items_add,items);
        //listView.setAdapter(arrayAdapter);
        numeInput = findViewById(R.id.item_name_id);
        cantitateInput = findViewById(R.id.item_quantity_id);

        provider = new UserProvider();
        Item item = new Item();

        items.add(item);

        listView.setAdapter(addAdapter);
        //addAdapter.swapItems(items);
        listProvider= new ItemListProvider();
    }




    @Override
    public void onResume() {
        super.onResume();
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AddShoppingListActivity.this,""+items.size(),Toast.LENGTH_SHORT).show();
                Item item = new Item();
                //item = addAdapter.getItem();
                items.add(item);
                listView.setAdapter(addAdapter);
                //items.add(item);
//                addAdapter.add(item);
                //addAdapter.notifyDataSetChanged();
//                items.add(item);
//                arrayAdapter.notifyDataSetChanged();

            }
        });
        saveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nume = listNameInput.getEditText().getText().toString();
                String id_user=provider.getUserId();
                String description = listDescriptionInput.getEditText().getText().toString();
                String location = listLocationInput.getEditText().getText().toString();
                //String id_list=listProvider.createList(nume,location,description,id_user);
                //Item item = new Item();
                //item = addAdapter.getItem();
                //items.add(item);
                items = addAdapter.getList();
//                items = addAdapter.getList();
//                for (int i=0;i<items.size();i++)
        //        {
//                   Item item = items.get(i);
//                   System.out.print(item.toString());
////                    itemProvider.addItem(item.name,item.quantity,id_list);
//                }


            }
        });
    }

}
