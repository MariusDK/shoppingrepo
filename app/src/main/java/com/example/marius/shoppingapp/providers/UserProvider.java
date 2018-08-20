package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProvider {

    private DatabaseReference mDatabase;
    String userId;

    public UserProvider() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("users");

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

    public boolean login (String email,String password)
    {
        User user = new User(email,password);
       return  mDatabase.getKey().equals(user);
    }
}
