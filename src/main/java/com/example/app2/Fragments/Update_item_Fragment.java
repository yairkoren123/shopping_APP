package com.example.app2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.app2.R;
import com.example.app2.Search_Sopping_Activity;
import com.example.app2.Shopping;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Update_item_Fragment extends Fragment {

    private Timestamp time;


    private TextView item_tv,username_tv,count_tv,time_tv;

    private EditText edit_need,edit_username , edit_num;

    private LottieAnimationView delete;

    private LottieAnimationView save;
    private String id;

    private ArrayList<Shopping> shoppingArrayList = new ArrayList<>();


    String the_save_need , the_save_username;
    Editable save_count ;
    int the_save_count;








    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference cool = db.collection("shopping")
            .document("First Thoughts");
    private CollectionReference collectionReference = db.collection("Journal");
    private CollectionReference collectionReference1 = db.collection("shopping");

    private DocumentReference shopping_data_add = db.collection("shopping").document();


    public Update_item_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {

        delete = view.findViewById(R.id.deleted_button_frag);
        save = view.findViewById(R.id.save_button_frag);


        edit_need = view.findViewById(R.id.item_et_frag);
        edit_username = view.findViewById(R.id.username_et_frag);
        edit_num = view.findViewById(R.id.count_et_frag);

        time_tv = view.findViewById(R.id.time_tv_frag);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.playAnimation();
                // todo deleted item

                DocumentReference doc = FirebaseFirestore.getInstance().collection("shopping").document(id);

               doc.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       Log.d("del", "onSuccess: del _yes ");
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.d("del", "onSuccess: del_ no ");

                   }
               });

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.playAnimation();
                if (!shoppingArrayList.equals(null)){
                    // after search item
                    Shopping shopping = new Shopping();
                    shopping = shoppingArrayList.get(0);

                    the_save_need =  edit_need.getText().toString().trim();
                    the_save_username=  edit_username.getText().toString().trim();
                    save_count = edit_num.getText();

                    Log.d("savenow", "onClick: "+ save_count);


                    String s = String.valueOf(save_count);
                    the_save_count= Integer.parseInt(s);

                    Log.d("savenow", "onClick: "+ the_save_count);
                    // put values in the shopping class
                    shopping.setShopNeed(the_save_need);
                    shopping.setFirstName(the_save_username);
                    shopping.setCount(the_save_count);



                    // send the sopping class
                    DocumentReference doc = FirebaseFirestore.getInstance().collection("shopping").document(id);

                    doc.set(shopping).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("suc_save", "onSuccess: yes_save");
                            //Intent intent = new Intent(Update_item_Fragment.this,)

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("suc", "onSuccess: no_save");

                        }
                    });

                }





            }
        });





        Bundle arg = getArguments();

        String need = arg.getString("need");
        String username = arg.getString("username");
        int count = arg.getInt("count");
        id = arg.getString("id");




        Shopping shopping = new Shopping();

        DocumentReference g = db.collection("shopping").document(id);
        g.getParent().whereEqualTo("firstName", username);
        shopping.setShopNeed(need);
        shopping.setFirstName(username);
        shopping.setCount(count);


        edit_need.setText(need);
        edit_username.setText(username);
        edit_num.setText(String.valueOf(count));


        // set time to sopping :

        // before

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


                            if (shopping.getShopNeed().equals(need)) {
                                shoppingArrayList = new ArrayList<>();
                                shoppingArrayList.add(shopping);

                                Log.d("loy", "onSuccess: " + snapshots.getId());
                                time = shopping.getTimeAdded();




                                data += "Title: " + shopping.getFirstName() + " \n"
                                        + "Thought: " + shopping.getShopNeed() + "\n\n";

                                shopping.setTimeAdded(time);
                                String timeAgo = "";
                                timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                                        time.getSeconds() * 1000);
                                time_tv.setText(timeAgo);
                                shopping.setFirstName("Hila");




                                DocumentReference doc = FirebaseFirestore.getInstance().collection("shopping").document(id);

                                doc.set(shopping).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("suc", "onSuccess: yes");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("suc", "onSuccess: no");

                                    }
                                });
                                continue;
                            }


                            //recTitle.setText(journal.getTitle());
                        }
                        if (data == "") {
                            Log.d("empty", "onSuccess: ");

                        } else {

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        // after



        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_item_, container, false);
        return view;
    }



}


