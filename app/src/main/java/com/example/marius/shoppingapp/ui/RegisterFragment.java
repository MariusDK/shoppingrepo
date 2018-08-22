package com.example.marius.shoppingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marius.shoppingapp.R;


public class RegisterFragment extends Fragment {
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private TextInputLayout confirmInput;
    private Button register;
    private onRegisterListener listener;


    public RegisterFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_register,container,false);
        emailInput = v.findViewById(R.id.email_register_id);
        passwordInput = v.findViewById(R.id.password_register_id);
        confirmInput = v.findViewById(R.id.confirm_register_id);
        register = v.findViewById(R.id.register_button_id);



        Toolbar toolbar = (Toolbar)v.findViewById(R.id.toolbar_register_id);
        toolbar.setTitle("Register");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getEditText().getText().toString();
                String password = passwordInput.getEditText().getText().toString();
                String confirm = confirmInput.getEditText().getText().toString();
                if ((email.equals("")||password.equals(""))||confirm.equals("")) {
                    Toast.makeText(getActivity(),"Empty fields",Toast.LENGTH_SHORT).show();


                }
                else
                {
                    if (checkIfPasswordMatch(password, confirm)) {
                        listener.onClickRegisterListener(email, password);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Passwords don't match!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }



    public boolean checkIfPasswordMatch(String password,String confirmPassword)
    {
        if (password.equals(confirmPassword))
        {
            return true;
        }
        else return false;
    }
    public interface onRegisterListener
    {
        public void onClickRegisterListener(String email,String password);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onRegisterListener)context;
    }
}
