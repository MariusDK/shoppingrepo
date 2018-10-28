package com.example.marius.shoppingapp.providers;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.example.marius.shoppingapp.classes.ShoppingList;
import com.example.marius.shoppingapp.classes.User;
import com.example.marius.shoppingapp.classes.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class UserProvider {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String Search_userId;
    private String userId;
    private UserListener listener;
    private getUserListener getUserListener;
    private FirebaseFirestore mFirestore;
    private String token_id;


    public UserProvider(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        listener = (UserListener)context;
    }

    public UserProvider() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void setContext(Context context)
    {
        getUserListener = (getUserListener)context;
    }
    public void register(final String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference("users");
                            String id_user = mDatabase.push().getKey();
                            UserData userData = new UserData();
                            userData.setEmail(email);
                            userData.setId_user(getUserId());
                            mDatabase.child(id_user).setValue(userData);
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


                    userId = mAuth.getCurrentUser().getUid();

                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            token_id = instanceIdResult.getToken();

                        }
                    });
                    Map<String, Object> tokenMap = new HashMap<>();
                    tokenMap.put("token_id",token_id);
                    mFirestore.collection("Users").document(userId).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listener.OnSuccesListener(); }
                            });
                            //listener.OnSuccesListener();
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
        userId = mAuth.getCurrentUser().getUid();
        mFirestore = FirebaseFirestore.getInstance();
        Map<String,Object> tokenMapRemove = new HashMap<>();
        tokenMapRemove.put("token_id", FieldValue.delete());
        mFirestore.collection("Users").document(userId).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAuth.signOut();
            }
        });
        //de eliminat
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
    public String getCurrentUserEmail()
    {
        return mAuth.getCurrentUser().getEmail();
    }
    public void getRequestUserId(String email)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showDataList(dataSnapshot);
                getUserListener.finishGetUserIdListener(Search_userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showDataList(DataSnapshot dataSnapshot)
    {

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            UserData data =  ds.getValue(UserData.class);
            Search_userId = data.getId_user();
            System.out.println("Aici este "+ Search_userId);
        }
    }
    public interface getUserListener
    {
        public void finishGetUserIdListener(String id_user);
    }
}
