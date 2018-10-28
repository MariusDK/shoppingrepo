package com.example.marius.shoppingapp.activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.ItemProvider;
import com.example.marius.shoppingapp.providers.UserProvider;
import com.example.marius.shoppingapp.fragments.LoginFragment;
import com.example.marius.shoppingapp.fragments.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;

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
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent = new Intent(MainActivity.this,ShoppingListActivity.class);
            startActivity(intent);
            finish();
        }

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
        settingFragment(registerFragment,loginFragment);
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
                settingFragment(loginFragment,registerFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickRegisterListener(String email, String password) {
        provider.register(email,password);
    }
    public void settingFragment(Fragment fragmentAdd,Fragment fragmentRemove)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragmentRemove);
        fragmentTransaction.replace(R.id.continer, fragmentAdd);
        fragmentTransaction.commit();
    }


}
