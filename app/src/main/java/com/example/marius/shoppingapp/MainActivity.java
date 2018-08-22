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
    private FragmentTransaction fragmentTransaction;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        provider = new UserProvider(this);
        registerFragment = new RegisterFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
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
        fragmentTransaction.remove(loginFragment);
        fragmentTransaction.replace(R.id.continer, registerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void OnFailListener(String msg) {
       Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnSuccesListener() {
        Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
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
