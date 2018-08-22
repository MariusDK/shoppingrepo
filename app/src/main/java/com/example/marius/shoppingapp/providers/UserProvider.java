package com.example.marius.shoppingapp.providers;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.marius.shoppingapp.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class UserProvider {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userId;
    private UserListener listener;


    public UserProvider(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        listener = (UserListener)context;
    }
    public UserProvider() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
    }
    public void register(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            userId = user.getProviderId();
                            System.out.println(user.getEmail());
                            listener.OnSuccesListener();


                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            System.out.println("Eroarea este: "+e.getMessage());
                            //Toast.makeText(this,"Authentification failed.", Toast.LENGTH_SHORT).show();
                            listener.OnFailListener(e.getMessage());
                        }
                    }
                });
    }
    public void signIn (String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    FirebaseUser user = mAuth.getCurrentUser();
                    userId = user.getProviderId();
                    System.out.println(user.getEmail());
                    listener.OnSuccesListener();
                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    System.out.println("Eroarea este: "+e.getMessage());
                    //Toast.makeText()
                    listener.OnFailListener(e.getMessage());
                }
            }
        });
    }
    public void signOut()
    {
        mAuth.signOut();
    }

    public String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }
    public interface UserListener
    {
        public void OnSuccesListener();
        public void OnFailListener(String msg);

    }
}
