package com.example.marius.shoppingapp;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;
import com.example.marius.shoppingapp.ui.LoginFragment;
import com.example.marius.shoppingapp.ui.RegisterFragment;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements LoginFragment.onLoginListener, UserProvider.UserListener, RegisterFragment.onRegisterListener{
    private FragmentManager fragmentManager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private UserProvider provider;
    private ItemListProvider itemListProvider;
    private ItemProvider itemProvider;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provider = new UserProvider(this);
        registerFragment = new RegisterFragment();
//        itemListProvider = new ItemListProvider();
//        itemProvider = new ItemProvider();
//        String email = "admin@gmail.com";
//        String password = "admin1234";
//        //provider.register(email,password);
//        provider.signIn(email,password);
//        //Toast.makeText(this,provider.getUserId(),Toast.LENGTH_SHORT).show();
//        //itemListProvider.createList("List 1","Kaufland","party2",provider.getUserId());
//        //String id_item = itemProvider.addItem("Ananas",1,"-LKRA_y9zMMHjroIEssw");
//
//        //itemListProvider.addItemToList("List 2",id_item,provider.getUserId());
//        //itemListProvider.getItems("List 2");
//        //itemProvider.updateItem("Pizza",30,"-LKREMNAm6hqtHqaLaVh","-LKRA_y9zMMHjroIEssw");
//        //itemProvider.deleteItem("-LKRBuvAwGf2Rg1NdKd2","-LKRA_y9zMMHjroIEssw");
//        //itemListProvider.deleteItemFromList("List 2","-LKQjcjIZSsttz3OPGsA")
//        ;
//        //itemListProvider.statusOff("List 1");
//        //itemProvider.getItems("-LKRA_y9zMMHjroIEssw");
//        //itemProvider.getItemArrayList();
//        //itemListProvider.getAllLists("jH1S3nW8HnhVAfQsVv5oRpnRQJj1");
//        itemListProvider.getShoppingLists("jH1S3nW8HnhVAfQsVv5oRpnRQJj1");


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.continer,loginFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onClickLoginListener(String email, String password) {
        provider.signIn(email,password);
    }

    @Override
    public void onClickRegisterTextListenr() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(loginFragment);
        fragmentTransaction.replace(R.id.continer, registerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnSignInListener() {
        Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnFailSignInListener(String msg) {
       Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRegisterListener() {
        Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnFailRegisterListener(String msg) {
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(registerFragment);
                fragmentTransaction.replace(R.id.continer, loginFragment);
                fragmentTransaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickRegisterListener(String email, String password) {
        provider.register(email,password);
    }
}
