package com.example.marius.shoppingapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marius.shoppingapp.R;
import com.example.marius.shoppingapp.adapters.ListAdapter;

import com.example.marius.shoppingapp.classes.Item;
import com.example.marius.shoppingapp.classes.ShoppingList;
import com.example.marius.shoppingapp.providers.FriendsProvider;

import com.example.marius.shoppingapp.providers.ItemListProvider;
import com.example.marius.shoppingapp.providers.UserProvider;
import com.example.marius.shoppingapp.utils.UIUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingListActivity extends AppCompatActivity implements ItemListProvider.getListListener, ListAdapter.deleteItemListener, UserProvider.getUserListener {
    private ListView listViewIncomplet;
    private UserProvider provider;
    private ListView listViewComplete;
    private ListAdapter adapter1;
    private ListAdapter adapter2;
    private FloatingActionButton buttonAdd;
    private ProgressBar progressBar;
    private ArrayList<ShoppingList> InCompletedList;
    private ArrayList<ShoppingList> CompletedList;
    private TextView currentListText;
    private TextView completeListText;
    private TextView noDataTextView;
    private FragmentManager fragmentManager;
    private ItemListProvider providerList;
    private ImageButton deleteListItem;
    private ArrayList<ShoppingList> listCompleta;
    private ArrayList<ShoppingList> listInCompleta;
    ArrayList<ShoppingList> shoppingLists;
    private int option=0;
    private ImageButton addFriendButton;
    private FriendsProvider friendsProvider;
    private FirebaseFirestore mFirestone;
    private String SearchIdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        InCompletedList = new ArrayList<>();
        CompletedList = new ArrayList<>();
        provider = new UserProvider();
        friendsProvider = new FriendsProvider();
        fragmentManager = getSupportFragmentManager();
        providerList = new ItemListProvider(this);
        listViewIncomplet = findViewById(R.id.list_id);
        listViewComplete = findViewById(R.id.list_items_id);
        adapter1 = new ListAdapter(this);
        adapter2 = new ListAdapter(this);
        listViewIncomplet.setAdapter(adapter1);
        listViewComplete.setAdapter(adapter2);
        progressBar = findViewById(R.id.determinateBar);
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
        buttonAdd = findViewById(R.id.floatingActionButton2);
        completeListText = findViewById(R.id.completed_list_id_text);
        currentListText = findViewById(R.id.current_list_id_text);
        noDataTextView = findViewById(R.id.nodData_id);
        addFriendButton = findViewById(R.id.addFriend);
        completeListText.setVisibility(View.INVISIBLE);
        currentListText.setVisibility(View.INVISIBLE);
        noDataTextView.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_list_page_id);
        toolbar.setTitle("");
        this.setSupportActionBar(toolbar);
        shoppingLists = new ArrayList<>();
        adapter2.clear();
        adapter1.clear();
        providerList.getShoppingLists(provider.getUserId());

        mFirestone = FirebaseFirestore.getInstance();

        listViewIncomplet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingList shoppingList = (ShoppingList) listViewIncomplet.getItemAtPosition(i);
                Intent intent = new Intent(ShoppingListActivity.this, ListDetailsActivity.class);
                intent.putExtra("listKey", shoppingList.getIdList());
                intent.putExtra("titleShoppingList",shoppingList.getNume());
                startActivity(intent);
                finish();
            }
        });
        listViewComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });

        listViewIncomplet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingListActivity.this, AddShoppingListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddFriendDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.sort_id:
                createSortDialog();
                break;
            case R.id.logout_id:
                logoutDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    public void createAddFriendDialog()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add,null);
        final EditText editText = view.findViewById(R.id.friendEmail);
        builder.setTitle("Add Friend");
        builder.setView(view);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressBar.setVisibility(View.VISIBLE);
                String email = editText.getText().toString();
                provider.setContext(ShoppingListActivity.this);
                provider.getRequestUserId(email);
                //friendsProvider.requestToFriend(email,provider.getCurrentUserEmail());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    public void createSortDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sort,null);
        builder.setTitle("Sorting methods");
        builder.setView(view);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (option==1)
                {
                    adapter1.ordChronological();
                    adapter1.notifyDataSetChanged();
                    adapter2.ordChronological();
                    adapter2.notifyDataSetChanged();
                }
                if (option==2)
                {
                    adapter1.ordNonChronological();
                    adapter1.notifyDataSetChanged();
                    adapter2.ordNonChronological();
                    adapter2.notifyDataSetChanged();
                }
                if (option==3)
                {
                    adapter1.ordLocation();
                    adapter1.notifyDataSetChanged();
                    adapter2.ordLocation();
                    adapter2.notifyDataSetChanged();
                }

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    public void handleRadioButtons(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

            switch (view.getId()) {
                case R.id.radioButton1:
                    if (checked)
                        option = 1;
                        break;
                case R.id.radioButton2:
                    if (checked)
                        option = 2;
                        break;
                case R.id.radioButton3:
                    if (checked)
                        option = 3;
                        break;
            }

    }

    @Override
    public void finishListener(ArrayList<ShoppingList> lists) {
        progressBar.setVisibility(View.INVISIBLE);

        shoppingLists = lists;
        if (shoppingLists.isEmpty()) {
            completeListText.setVisibility(View.INVISIBLE);
            currentListText.setVisibility(View.INVISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);

        }
        else {
            completeListText.setVisibility(View.VISIBLE);
            currentListText.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.INVISIBLE);
            for (ShoppingList shoppingList : shoppingLists) {
                if (shoppingList.isStatus())
                {
                    adapter1.add(shoppingList);
                }
                else
                {
                    adapter2.add(shoppingList);
                }

            }
        }
        UIUtils.setListViewHeightBasedOnItems(listViewComplete);
        UIUtils.setListViewHeightBasedOnItems(listViewIncomplet);
    }

    @Override
    public void deleteItemOnClick(final String id_list, String list_name) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete the " + list_name +" list?").setTitle(R.string.delete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                providerList.deleteList(id_list);
                adapter1.clear();
                adapter1.notifyDataSetChanged();
                adapter2.clear();
                adapter2.notifyDataSetChanged();
//                UIUtils.setListViewHeightBasedOnItems(listViewComplete);
//                UIUtils.setListViewHeightBasedOnItems(listViewIncomplet);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void logoutDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.logoutMessage).setTitle("Logout");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                provider.signOut();
                Intent intent = new Intent(ShoppingListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void setDataTOCardViewList(ShoppingList i)
    {
        CardView cardView = (CardView) listViewComplete.getChildAt(CompletedList.indexOf(i));
        ConstraintLayout constraintLayout = (ConstraintLayout) cardView.getChildAt(0);
        TextView TextItemName = (TextView) constraintLayout.getChildAt(0);
        TextView TextItemLocation = (TextView) constraintLayout.getChildAt(1);
        TextItemName.setText(i.getNume());
        TextItemLocation.setText(i.getLocation());
    }
    public void setDataTOCardViewListIN(ShoppingList i)
    {
        CardView cardView = (CardView) listViewIncomplet.getChildAt(InCompletedList.indexOf(i));
        ConstraintLayout constraintLayout = (ConstraintLayout) cardView.getChildAt(0);
        TextView TextItemName = (TextView) constraintLayout.getChildAt(0);
        TextView TextItemLocation = (TextView) constraintLayout.getChildAt(1);
        TextItemName.setText(i.getNume());
        TextItemLocation.setText(i.getLocation());
    }

    @Override
    public void finishGetUserIdListener(String id_user) {
        SearchIdUser = id_user;
        Map<String,Object> notificationMessage = new HashMap<>();
        notificationMessage.put("message", provider.getCurrentUserEmail());
        notificationMessage.put("from", provider.getUserId());

        mFirestone.collection("Users/"+SearchIdUser+"/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(ShoppingListActivity.this,"Notification Sent",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShoppingListActivity.this,"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
