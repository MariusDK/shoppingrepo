package com.example.marius.shoppingapp.ui;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.marius.shoppingapp.R;


public class LoginFragment extends Fragment {

    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Button login;
    private TextView registerText;
    private onLoginListener loginListener;
    public LoginFragment()
    {}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login,container,false);
        emailInput = v.findViewById(R.id.email_login_id);
        passwordInput = v.findViewById(R.id.password_login_id);
        login = v.findViewById(R.id.login_id);
        registerText = v.findViewById(R.id.register_text_id);
        ActionBar actionBar =  getActivity().getActionBar();
        actionBar.setTitle("Login");

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getEditText().getText().toString();
                String password = passwordInput.getEditText().getText().toString();
                loginListener.onClickLoginListener(email,password);
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginListener.onClickRegisterTextListenr();
            }
        });

    }
    public interface onLoginListener
    {
        public void onClickLoginListener(String email, String password);
        public void onClickRegisterTextListenr();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loginListener = (onLoginListener)context;
    }
}
