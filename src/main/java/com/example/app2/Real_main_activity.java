package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Real_main_activity extends AppCompatActivity implements ListShoppingAdapter.OnListItemClick {


    // layout
    private Button button;

    private ImageButton add_button_count,odd_button_count;

    private FloatingActionButton add_button_r;

    private TextView text_count_in_card;

    private TextView count_number;


    private EditText the_text_shopping_add_r;

    private RecyclerView mFirestoreList;

    private ListShoppingAdapter adapter;
    private int now_count_in_card = 0;

    private String need_add;


    // fire base


    private FirebaseFirestore firebaseFirestore;




    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference cool = db.collection("shopping")
            .document("First Thoughts");
    private CollectionReference collectionReference = db.collection("Journal");
    private CollectionReference collectionReference1 = db.collection("shopping");

    private DocumentReference shopping_data_add = db.collection("shopping").document();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        // layout
        button = findViewById(R.id.button);
        add_button_r = findViewById(R.id.add_button_real);
        the_text_shopping_add_r = findViewById(R.id.enter_item_real);
        mFirestoreList = findViewById(R.id.recycler_view_real);

        add_button_count = findViewById(R.id.plus_button_counts_real);
        odd_button_count = findViewById(R.id.minus_button_counts_real);
        text_count_in_card = findViewById(R.id.count_items_real_InCard);


        text_count_in_card.setText("0");

        add_button_count.setOnClickListener(v -> {
            now_count_in_card++;
            text_count_in_card.setText(now_count_in_card + "");

        });

        odd_button_count.setOnClickListener(v -> {
            now_count_in_card--;
            if (now_count_in_card < 0){
                now_count_in_card = 0;
            }
            text_count_in_card.setText(now_count_in_card + "");

        });




//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide();


        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d("TAG", "onCreate: " + username);


        firebaseFirestore = FirebaseFirestore.getInstance();


        //Query

        Query query = firebaseFirestore.collection("shopping");

        //recyclerOptions

        FirestoreRecyclerOptions<Shopping> options = new FirestoreRecyclerOptions.Builder<Shopping>()
                .setQuery(query, Shopping.class)
                .build();

        adapter = new ListShoppingAdapter(options, this);
//                = new FirestoreRecyclerAdapter<Shopping, ShoppingViewHolder>(options) {
//            @NonNull
//            @Override
//            public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_item_single,parent,false);
//
//                return new ShoppingViewHoler(view);
//            }

//            @Override
//            protected void onBindViewHolder(@NonNull Real_main_activity.ShoppingViewHolder holder
//                    , int position
//                    , @NonNull  Shopping model) {
//
//                holder.the_shooping_single.setText(model.getShopNeed());
//                holder.the_username_single.setText(model.getFirstName());
//                holder.the_time_single.setText(model.getTimeAdded() + "");
//
//
//            }

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


        // TRY


        // view Holder


        // data base :


        add_button_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(Real_main_activity.this);


                Map<String, Object> adding_to_list = new HashMap<>();

                need_add = the_text_shopping_add_r.getText().toString().trim();


                collectionReference1.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                String data = "";
                                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                                    Shopping shopping = snapshots.toObject(Shopping.class);

                                    if (shopping.getShopNeed().equals(need_add)) {
                                        shoppingArrayList.add(shopping);
                                        data += "Title: " + shopping.getFirstName() + " \n"
                                                + "Thought: " + shopping.getShopNeed() + "\n\n";
                                    }
                                }
                                if (data == "") {
                                    Log.d("where", "onSuccess: ");

                                    Shopping shopping = new Shopping();
                                    // set name
                                    shopping.setFirstName(username);
                                    // set need to add
                                    shopping.setShopNeed(need_add);
                                    //set time

                        //                String time_now = String.valueOf(new Timestamp(new Date()));
                        //                shopping.setTimeAdded(time_now);

                                    shopping.setTimeAdded(new Timestamp(new Date()));
                                    // set count
                                    shopping.setCount(Integer.parseInt(text_count_in_card.getText().toString().trim()));


                                    adding_to_list.put("item_here", need_add);
                                    shopping_data_add = db.collection("shopping").document();

                                    shopping_data_add.set(shopping);
                                    the_text_shopping_add_r.setText("");
                                    text_count_in_card.setText("0");


                                } else {
                                    msg("the item already on the list !!");
                                }
                                Log.d("dont", "onSuccess: " + data);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
    public ArrayList<Shopping> shoppingArrayList = new ArrayList<>();

    public void getThoughts_is_there(String text_search) {
        collectionReference1.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {


//                                    String title = documentSnapshot.getString(KEY_TITLE);
//                                    String thought = documentSnapshot.getString(KEY_THOUGHT);

//                             Log.d(TAG, "onSuccess: " + snapshots.getId());
                            Shopping shopping = snapshots.toObject(Shopping.class);

                            if (shopping.getShopNeed().equals(text_search)){
                                shoppingArrayList.add(shopping);
                                data += "Title: " + shopping.getFirstName() + " \n"
                                        + "Thought: " + shopping.getShopNeed() + "\n\n" ;
                            }


                            //recTitle.setText(journal.getTitle());
                        }
                        if (data == ""){
                            Log.d("where", "onSuccess: ");
                        }
                        Log.d("dont", "onSuccess: " + data);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.item1:
                msg("1");

                Intent intent = new Intent(this, Search_Sopping_Activity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item2:
                msg("2");
                break;
            case R.id.item3:
                msg("3");
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void msg(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG)
                .show();
    }




//    private class ShoppingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private TextView the_username_single;
//        private TextView the_time_single;
//        private TextView the_shooping_single;
//
//        private OnListItemClick onListItemClick;
//
//
//        public ShoppingViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            the_shooping_single = itemView.findViewById(R.id.single_need_shooping);
//            the_time_single = itemView.findViewById(R.id.single_time);
//            the_username_single = itemView.findViewById(R.id.single_username);
//
//            itemView.setOnClickListener(this); }


//        @Override
//        public void onClick(View v) {
//            onListItemClick.onItemClick();
//        }
//
//
//    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onItemClick(int position) {
        Log.d("cool", "onItemClick:  item click" + position);
    }


}