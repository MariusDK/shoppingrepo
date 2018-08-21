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
        ArrayList<String> list = new ArrayList();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setNume(name);
        Date date = new Date();
        long time = date.getTime();
        Timestamp timestamp = new Timestamp(time);
        shoppingList.setDate(timestamp.getTime());
        shoppingList.setLocation(location);
        shoppingList.setDescription(description);
        shoppingList.setId_user(userID);
        shoppingList.setItemList(list);
        shoppingList.setStatus(true);
        String listID = mDatabase.push().getKey();
        mDatabase.child(listID).setValue(shoppingList);
        return listID;
    }
    public void addItemToList(String listName, String itemId,String id_user)
    {
        mDatabase.child(listName).child(itemId).setValue(true);
    }


    public void getAllLists(final String id_user)
    {
        final ArrayList<String> lists = new ArrayList<>();
        DatabaseReference ref = mDatabase;
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showDataList(dataSnapshot,id_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }
    private void showDataList(DataSnapshot dataSnapshot, String id_user)
    {
        lists = new ArrayList();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            ShoppingList listName = ds.getValue(ShoppingList.class);
            listName.setIdList(ds.getKey());
            if (listName.getId_user().equals(id_user)) {
                lists.add(listName);
                System.out.println(listName.getIdList());
            }
        }

    }

    public void getShoppingLists(String id_user)
    {
        mDatabase.orderByChild("id_user").equalTo(id_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showDataList2(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showDataList2(DataSnapshot dataSnapshot)
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
    public ShoppingList get_List(String name, String id_user)
    {
        getAllLists(id_user);
        for (ShoppingList shoppingList:lists)
        {
            if (shoppingList.getNume().equals(name))
            {return shoppingList;}
        }
        return null;
    }



    public String getSelectedItemId() {
        return SelectedItemId;
    }

    public void deleteItemFromList(String listName,String idItem){

            mDatabase.child(listName).child(idItem).removeValue();
    }
    public void statusOn(String listName)
    {
        mDatabase.child(listName).child("status").setValue(true);
    }
    public void statusOff(String listName)
    {
        mDatabase.child(listName).child("status").setValue(false);
    }

}
