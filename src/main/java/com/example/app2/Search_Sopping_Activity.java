package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.app2.Fragments.Update_item_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Internal;

import java.util.ArrayList;

public class Search_Sopping_Activity extends AppCompatActivity {


    String need_shopping_search ;
    String theusername_search;
    int count_search ;
    Timestamp time_search ;
    String document_Id;


    public String KEY_TO_FRAG_need = "need";
    public String KEY_TO_FRAG_user = "username";
    public String KEY_TO_FRAG_count = "count";
    public String KEY_TO_FRAG_time = "time";
    public String KEY_TO_FRAG_document = "id";

    //layout



    //private ImageButton search_image_shopping;

    private LottieAnimationView search_image_shopping;
    private EditText search_shopping_et;
    private ListView listView_s;
    private CardView no_item_card;
    private ProgressBar progressBar_s;
    private ImageButton back_button_s;

    private FrameLayout frameLayout_frag;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference cool = db.collection("shopping")
            .document("First Thoughts");
    private CollectionReference collectionReference = db.collection("Journal");
    private CollectionReference collectionReference1 = db.collection("shopping");

    private DocumentReference shopping_data_add = db.collection("shopping").document();


    ArrayList<Shopping> shoppingArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sopping);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        search_image_shopping = findViewById(R.id.image_search_s);
        search_shopping_et = findViewById(R.id.search_et_s);
        listView_s = findViewById(R.id.list_view_s);
        no_item_card = findViewById(R.id.card_view_no_items);
        no_item_card.setVisibility(View.GONE);
        progressBar_s = findViewById(R.id.progressBar_s);
        frameLayout_frag = findViewById(R.id.mail_container);

        frameLayout_frag.setVisibility(View.GONE);
        progressBar_s.setVisibility(View.GONE);


        listView_s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Log.d("122", "onItemClick: " + shoppingArrayList.get(i).getShopNeed());
//                mainActivity.
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.google_map, finalMapFragment)
//                        .commit();
////                finalMapFragment.getMapAsync(mainActivity);

            }
        });


        search_image_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_image_shopping.playAnimation();
                no_item_card.setVisibility(View.GONE);
                progressBar_s.setVisibility(View.VISIBLE);

                shoppingArrayList = new ArrayList<>();
                String text_search = search_shopping_et.getText().toString().trim();
                hideKeyboard(Search_Sopping_Activity.this);
                if (text_search.isEmpty() || text_search == "" || text_search.equals(null)){
                    getThoughts(text_search);
                    msg("pls Enter a item");
                    no_item_card.setVisibility(View.GONE);
                }else {
                    getThoughts(text_search);


                }
            }
        });





    }
    private void msg(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG)
                .show();
    }


    public void getThoughts(String text_search) {
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
                                Log.d("loy", "onSuccess: " + snapshots.getId());
                                document_Id = snapshots.getId();
                                need_shopping_search = shopping.getShopNeed();
                                theusername_search = shopping.getFirstName();
                                count_search = shopping.getCount();
                                time_search = shopping.getTimeAdded();

                                shoppingArrayList.add(shopping);
                                data += "Title: " + shopping.getFirstName() + " \n"
                                        + "Thought: " + shopping.getShopNeed() + "\n\n" ;
                            }


                            //recTitle.setText(journal.getTitle());
                        }
                        if (data == ""){
                            no_item_card.setVisibility(View.VISIBLE);
                            frameLayout_frag.setVisibility(View.GONE);
                            Log.d("where", "onSuccess: ");

                            getSupportFragmentManager().beginTransaction().replace(R.id.mail_container,new Update_item_Fragment());


                        }else {
                            frameLayout_frag.setVisibility(View.VISIBLE);
                            getFragment();
                        }

                        if (shoppingArrayList != null){
                            CustomAdaper customAdaper = new CustomAdaper();
                            listView_s.setAdapter(customAdaper);
                        }else {
                            no_item_card.setVisibility(View.VISIBLE);
                            Log.d("where1", "onSuccess: ");

                        }
                        Log.d("dont", "onSuccess: " + data);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        if (shoppingArrayList != null){
            CustomAdaper customAdaper = new CustomAdaper();
            listView_s.setAdapter(customAdaper);
        }else {
            no_item_card.setVisibility(View.VISIBLE);
            Log.d("where1", "onSuccess: ");

        }
        progressBar_s.setVisibility(View.GONE);
        search_shopping_et.setText("");
    }
    private void getFragment(){
//        FragmentManager fm = getSupportFragmentManager();
//        Update_item_Fragment fragment = new Update_item_Fragment();

        String time_search_string = String.valueOf(time_search);

        Bundle b = new Bundle();
        KEY_TO_FRAG_need = "need";
        KEY_TO_FRAG_user = "username";
        KEY_TO_FRAG_count = "count";
        KEY_TO_FRAG_time = "time";
        KEY_TO_FRAG_document = "id";

        b.putString(KEY_TO_FRAG_need,need_shopping_search);
        b.putString(KEY_TO_FRAG_user,theusername_search);
        b.putInt(KEY_TO_FRAG_count,count_search);
        b.putString(KEY_TO_FRAG_document,document_Id);
        b.putString(KEY_TO_FRAG_time,time_search_string);


        Update_item_Fragment fragment = new Update_item_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mail_container,fragment)
                .commit();
        fragment.setArguments(b);


    }

    class CustomAdaper extends BaseAdapter implements AdapterView.OnItemClickListener {

        public ArrayList<Shopping> arrayList =  new ArrayList<>();


        @Override
        public int getCount() {
            return shoppingArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.list_item_single,null);


            TextView username = (TextView) view.findViewById(R.id.single_username);
            TextView need = (TextView) view.findViewById(R.id.single_need_shooping);
            TextView time = (TextView) view.findViewById(R.id.single_time);
            TextView count = (TextView)  view.findViewById(R.id.single_count);
            Log.d("count", "getView: "  +count.getText());



//            if (citysArrayList1.get(position).getCity() == null) {
//                textView.setText("not ");
//            } else {
//                textView.setText(values[position]); // citysArrayList1.get(postion).getData - error
//            }

            username.setText(shoppingArrayList.get(position).getFirstName());
            need.setText(shoppingArrayList.get(position).getShopNeed());
            count.setText(String.valueOf(shoppingArrayList.get(position).getCount()));

            String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                    shoppingArrayList.get(position).getTimeAdded().getSeconds() * 1000);

            time.setText(timeAgo);


            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("121", "onItemClick: " + id);

            Log.d("121", "onItemClick: " + position);
        }
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


}