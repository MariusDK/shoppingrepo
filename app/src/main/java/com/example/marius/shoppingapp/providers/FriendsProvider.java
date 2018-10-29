package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Request;
import com.example.marius.shoppingapp.classes.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsProvider {
    private DatabaseReference databaseReference;
    private List<Request> requestList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String id_user_request;



    public FriendsProvider() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("friends");
        requestList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
    }

    private void get_userId_byEmail(String email)
    {

    }

    public void addFriend(String email)
    {


        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    UserData data =  ds.getValue(UserData.class);
                    id_user_request = data.getId_user();
                    databaseReference.child(mAuth.getUid()).child(id_user_request).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
