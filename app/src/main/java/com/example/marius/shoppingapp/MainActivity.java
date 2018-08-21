package com.example.marius.shoppingapp;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;
import com.example.marius.shoppingapp.ui.LoginFragment;
import com.example.marius.shoppingapp.ui.RegisterFragment;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements LoginFragment.onLoginListener, UserProvider.UserListener{
    private FragmentManager fragmentManager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private UserProvider provider;
    private ItemListProvider itemListProvider;
    private ItemProvider itemProvider;
    private boolean resultLogin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        provider = new UserProvider();
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
        if (resultLogin == true)
        {
            //Next page
        }
    }

    @Override
    public void onClickRegisterTextListenr() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        registerFragment = new RegisterFragment();
        fragmentTransaction.add(R.id.continer, registerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnSignInListener(boolean resultLogin) {
        this.resultLogin = resultLogin;
    }

    @Override
    public void OnRegisterInListener(boolean result) {

    }


}
