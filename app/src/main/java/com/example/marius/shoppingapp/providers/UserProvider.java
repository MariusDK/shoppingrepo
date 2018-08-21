package com.example.marius.shoppingapp.providers;

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


    public UserProvider() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
    }

    public String addUser(String email, String password)
    {
        User user = new User(email,password);
        userId = mDatabase.push().getKey();
        mDatabase.child(userId).setValue(user);
        return userId;
    }
    public User getUser(String UserId)
    {
        final User[] u = new User[1];
        mDatabase.child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                u[0] = user;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return u[0];
    }

    public void updateUser(String UserId, String email, String password)
    {

        mDatabase.child(UserId).child("email").setValue(email);
        mDatabase.child(UserId).child("password").setValue(password);
    }
    public void removeUser(String UserId)
    {
        mDatabase.child(UserId).removeValue();
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

                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            System.out.println("Eroarea este: "+e.getMessage());
                            //Toast.makeText(this,"Authentification failed.", Toast.LENGTH_SHORT).show();
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
                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    System.out.println("Eroarea este: "+e.getMessage());
                    //Toast.makeText()
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
}
