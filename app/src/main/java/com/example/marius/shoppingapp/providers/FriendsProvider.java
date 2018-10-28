package com.example.marius.shoppingapp.providers;

import android.support.annotation.NonNull;

import com.example.marius.shoppingapp.classes.Request;
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


    public FriendsProvider() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        requestList = new ArrayList<>();
    }

    private void get_userId_byEmail(String email)
    {

    }

    public void requestToFriend(String emailFriend, String SenderEmail)
    {
        Request request = new Request();
        request.setReciver(emailFriend);
        request.setSender(SenderEmail);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("requests");
        String id_request = reference.push().getKey();
        reference.child(id_request).setValue(request);
    }



}
