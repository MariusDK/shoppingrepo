package com.example.marius.shoppingapp.providers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemProvider {
    private DatabaseReference databaseReference;
    private ArrayList<Item> itemArrayList;
    private String id_list_selected;
    private getItemsListener listener;

    public ItemProvider()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("items");
        itemArrayList = new ArrayList<>();
    }
    public ItemProvider(Context context)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("items");
        itemArrayList = new ArrayList<>();
        listener = (getItemsListener) context;
    }

    public String addItem(String ItemName, int Quantity,String ListKey)
    {
        Item item = new Item(ItemName,Quantity);

        String itemId = databaseReference.push().getKey();
        databaseReference.child(ListKey).child(itemId).setValue(item);
        return itemId;
    }
    public Item getItem(String itemID, String listID)
    {
        final Item[] item = new Item[1];
        databaseReference.child(listID).child(itemID).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         item[0] = dataSnapshot.getValue(Item.class);


         }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
             }
           }
        );
        return item[0];
    }
    public void deleteItem(String ItemId, String listId)
    {
        databaseReference.child(listId).child(ItemId).removeValue();
    }
    public void updateItem(String ItemName, int Quantity,String ItemId,String id_list)
    {
        Item item = new Item(ItemName,Quantity);
        databaseReference.child(id_list).child(ItemId).setValue(item);
    }
    public void getItems(String id_List)
    {

        id_list_selected = id_List;
        databaseReference.child(id_List).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showDataList(dataSnapshot);
                listener.getItemListener(itemArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showDataList(DataSnapshot snapshot)
    {
        for (DataSnapshot ds : snapshot.getChildren())
        {
            Item item = ds.getValue(Item.class);
            itemArrayList.add(item);
            //System.out.println(item.toString());
        }
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }
    public void addListItems(ArrayList<Item> items,String id_list)
    {
        for (Item item:items)
        {
            if (((item.getQuantity()!=0))&&(!item.getName().equals(""))) {
                String itemId = databaseReference.push().getKey();

                databaseReference.child(id_list).child(itemId).setValue(item);
            }
        }
    }
    public void deleteListItems(ArrayList<Item> items,String id_list)
    {
        databaseReference.child(id_list).removeValue();
    }
    public interface getItemsListener
    {
        public void getItemListener(ArrayList<Item> items);
    }


}
