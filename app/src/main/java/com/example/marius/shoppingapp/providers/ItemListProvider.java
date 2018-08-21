package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ItemListProvider {
    private DatabaseReference mDatabase;
    private ArrayList<ShoppingList> lists;
    private ArrayList<String> items;
    private ArrayList<Item> itemsArrayList;
    private String SelectedItemId;
    private String SelectedItemName;

    public ItemListProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference("shoppinglists");
        items = new ArrayList<>();
    }

    public String createList(String name,String location,String description, String userID)
    {

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setNume(name);
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        shoppingList.setDate(timestamp.getTime());
        shoppingList.setLocation(location);
        shoppingList.setDescription(description);
        shoppingList.setId_user(userID);
        shoppingList.setStatus(true);
        String listID = mDatabase.push().getKey();
        mDatabase.child(listID).setValue(shoppingList);
        return listID;
    }
    public void addItemToList(String listName, String itemId,String id_user)
    {
        mDatabase.child(listName).child(itemId).setValue(true);
    }




    public void getShoppingLists(String id_user)
    {
        mDatabase.orderByChild("id_user").equalTo(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showDataList(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showDataList(DataSnapshot dataSnapshot)
    {
        lists = new ArrayList();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            ShoppingList listName = ds.getValue(ShoppingList.class);
            listName.setIdList(ds.getKey());
            lists.add(listName);
            System.out.println(listName.getIdList());
        }
    }

    public String getSelectedItemId() {
        return SelectedItemId;
    }

    public void deleteItemFromList(String listName,String idItem){

            mDatabase.child(listName).child(idItem).removeValue();
    }
    public void statusChanger(String listName, boolean value)
    {
        mDatabase.child(listName).child("status").setValue(value);

    }

}
