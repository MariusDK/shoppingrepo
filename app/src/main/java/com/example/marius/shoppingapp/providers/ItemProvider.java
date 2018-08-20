package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemProvider {
    private DatabaseReference databaseReference;

    public ItemProvider()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("items");
    }
    public String addItem(String ItemName, int Quantity)
    {
        Item item = new Item(ItemName,Quantity);
        String itemId = databaseReference.push().getKey();
        databaseReference.child(itemId).setValue(item);
        return itemId;
    }
    public Item getItem(String itemID)
    {
        final Item[] item = new Item[1];
        databaseReference.child(itemID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item[0] = dataSnapshot.getValue(Item.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return item[0];
    }
    public void deleteItem(String ItemId)
    {
        databaseReference.child(ItemId).removeValue();
    }
    public void updateItem(String ItemName, int Quantity,String ItemId)
    {
        Item item = new Item(ItemName,Quantity);
        databaseReference.child(ItemId).setValue(item);
    }
}
