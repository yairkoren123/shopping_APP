package com.example.app2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.HashMap;

public class Update_item_Fragment extends Fragment {

    private Timestamp time;


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
        TextView textView = view.findViewById(R.id.textview_frag);
        textView.setText("sdads");

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arg = getArguments();

        String need = arg.getString("need");
        String username = arg.getString("username");
        int count = arg.getInt("count");
        String id = arg.getString("id");




        Shopping shopping = new Shopping();

        DocumentReference g = db.collection("shopping").document(id);
        g.getParent().whereEqualTo("firstName", username);
        shopping.setShopNeed(need);
        shopping.setFirstName(username);
        shopping.setCount(count);


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
                                Log.d("loy", "onSuccess: " + snapshots.getId());
                                time = shopping.getTimeAdded();
                                data += "Title: " + shopping.getFirstName() + " \n"
                                        + "Thought: " + shopping.getShopNeed() + "\n\n";

                                shopping.setTimeAdded(time);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_item_, container, false);
        return view;
    }



}


