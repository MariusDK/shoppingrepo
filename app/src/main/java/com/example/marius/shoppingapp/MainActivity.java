package com.example.marius.shoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    UserProvider provider;
    ItemListProvider itemListProvider;
    ItemProvider itemProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provider = new UserProvider();
        itemListProvider = new ItemListProvider();
        itemProvider = new ItemProvider();
        String email = "admin@gmail.com";
        String password = "admin1234";
        //provider.register(email,password);
        provider.signIn(email,password);
        //Toast.makeText(this,provider.getUserId(),Toast.LENGTH_SHORT).show();
        //itemListProvider.createList("List 1","Kaufland","party2",provider.getUserId());
        //String id_item = itemProvider.addItem("Ananas",1,"-LKRA_y9zMMHjroIEssw");

        //itemListProvider.addItemToList("List 2",id_item,provider.getUserId());
        //itemListProvider.getItems("List 2");
        //itemProvider.updateItem("Pizza",30,"-LKREMNAm6hqtHqaLaVh","-LKRA_y9zMMHjroIEssw");
        //itemProvider.deleteItem("-LKRBuvAwGf2Rg1NdKd2","-LKRA_y9zMMHjroIEssw");
        //itemListProvider.deleteItemFromList("List 2","-LKQjcjIZSsttz3OPGsA")
        ;
        //itemListProvider.statusOff("List 1");
        //itemProvider.getItems("-LKRA_y9zMMHjroIEssw");
        //itemProvider.getItemArrayList();
        //itemListProvider.getAllLists("jH1S3nW8HnhVAfQsVv5oRpnRQJj1");
        itemListProvider.getShoppingLists("jH1S3nW8HnhVAfQsVv5oRpnRQJj1");
    }
}
