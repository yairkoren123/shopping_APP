package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {




    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String TEXT = "text";

    public static final ArrayList<String> colors = new ArrayList<>();



    // layout
    private TextView open_text_main;
    private EditText name_ed_main;
    private LottieAnimationView buttonMainStart;

    String message;
    private String text;


    // intents
    public static final String EXTRA_MESSAGE = "pass_name";
    public static final String ONE_MORE = "chenge_name";





    // fire base
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference journalRef = db.document("Journal/First Thoughts");
    private DocumentReference journalRef = db.collection("shopping")
            .document("First Thoughts");
    private CollectionReference collectionReference = db.collection("Journal");

    private DocumentReference journalRef1 = db.collection("shopping").document();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();



        open_text_main = findViewById(R.id.wellcom_main_text);
        name_ed_main = findViewById(R.id.enter_name_text_main);
        buttonMainStart = findViewById(R.id.button_main_enter);

        buttonMainStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // share p

                Intent intent = new Intent(MainActivity.this, Open_Activity.class);
                message = name_ed_main.getText().toString();
                if (message.isEmpty() || message == null || message == ""){
                    Toast.makeText(MainActivity.this, "cont be empty value", Toast.LENGTH_SHORT).show();

                }else {
                    saveData();
                    Log.d("shared", "loadData: " + name_ed_main.getText());


                    // sand the username
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                    finish();
                }

            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String change = intent.getStringExtra(ONE_MORE);
            Log.d("change", "onCreate: " + change);

            if (change != null) {
                Log.d("change", "onCreate:  no null " + change);

                if (change.equals("ture") || change == "true") {
                    Log.d("change", "onCreate:  1 " + change);

                }

            } else {
                Log.d("change", "onCreate: else ");

                loadData();
                updateViews();
            }
        }
    }

    public void updateViews() {
        if (!text.equals(null) || text != "" || !text.equals("")){
            name_ed_main.setText(text);
            buttonMainStart.callOnClick();

        }
    }

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        // give the user name
        editor.putString(TEXT,message );

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        Log.d("shared", "loadData: "+ text);
    }



}



//        buttonMainStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Open_Activity.class);
//                String message = name_ed_main.getText().toString();
//                if (message.isEmpty() || message == null || message == ""){
//                    message = "IDK";
//                }
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
//                finish();
//            }
//        });


//                Intent intent = new Intent(MainActivity.this, Real_main_activity.class);
//                String message = name_ed_main.getText().toString();
//                if (message.isEmpty() || message == null || message == ""){
//                    message = "The man who did not enter his name ";
//                }
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
//                finish();

//            }
//        });
//
//        Map<String, Object> update = new HashMap<>();
//        update.put("capital", true);
//
////        HashMap<String,Object> tag = new HashMap<>();
////
////        tag.put("yair","b1obo");
//
//        journalRef.set(update);

        // ading user and item
//        Map<String, Object> update1 = new HashMap<>();
//        update1.put("y22air","b22obo");
//
//        journalRef1.set(update1);


