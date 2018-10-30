package com.example.marius.shoppingapp.providers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Request;
import com.example.marius.shoppingapp.classes.User;
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
    private List<UserData> friendList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String id_user_request;
    private FriendListListener friendListListener;



    public FriendsProvider(FriendListListener friendListListener) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("friends");
        friendList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        this.friendListListener = friendListListener;
    }
    public FriendsProvider() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("friends");
        friendList = new ArrayList<>();
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
    public void getAllFriend()
    {

        databaseReference.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String id_user_friend = ds.getKey();
                    getUserDataById(id_user_friend);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getUserDataById(final String user_friend_id)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    UserData data = ds.getValue(UserData.class);
                    if (data.getId_user().equals(user_friend_id))
                    {
                        friendList.add(data);
                    }
                }
                friendListListener.getFirendList(friendList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface FriendListListener
    {
        public void getFirendList(List<UserData> list);
    }



}
