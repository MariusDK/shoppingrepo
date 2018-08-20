package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.ShoppingList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListProvider {
    private DatabaseReference mDatabase;

    public ItemListProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference("shoppinglists");
    }

    public String createList(String name)
    {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setNume(name);
        String listID = mDatabase.push().getKey();
        mDatabase.child(listID).setValue(shoppingList);
        return listID;
    }
    public void addItemToList(String listName, String itemId)
    {
        ArrayList<String> items = new ArrayList<>();
        items.add(itemId);
        for (String i:items)
        {
            mDatabase.child(listName).child(i).setValue(true);
        }
    }
    public ArrayList<String> getAllItems(String listName)
    {
        DatabaseReference listRef = mDatabase.child(listName);
        final ArrayList<String> items = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String item = ds.getKey();
                    items.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        listRef.addListenerForSingleValueEvent(eventListener);
        return items;
    }
    public ArrayList<String> getAllLists(final String idUser)
    {
        final ArrayList<String> lists = new ArrayList<>();
        DatabaseReference ref = mDatabase.child(idUser);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ShoppingList listName = ds.getValue(ShoppingList.class);
                    lists.add(listName.getNume());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        return lists;
    }
    public void deleteList(String ListId)
    {
        mDatabase.child(ListId).removeValue();
    }
    public void deleteItemFromList(final String ItemId,String listName)
    {
        DatabaseReference listRef = mDatabase.child(listName);
        final ArrayList<String> items = new ArrayList<>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    ds.getRef().child(ItemId).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        listRef.addListenerForSingleValueEvent(eventListener);
    }
}
