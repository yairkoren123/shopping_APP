package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    // layout
    private TextView open_text_main;
    private EditText name_ed_main;
    private Button buttonMainStart;

    // intents
    public static final String EXTRA_MESSAGE = "pass_name";


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
                Intent intent = new Intent(MainActivity.this, Open_Activity.class);
                String message = name_ed_main.getText().toString();
                if (message.isEmpty() || message == null || message == ""){
                    message = "IDK";
                }
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
                finish();


//                Intent intent = new Intent(MainActivity.this, Real_main_activity.class);
//                String message = name_ed_main.getText().toString();
//                if (message.isEmpty() || message == null || message == ""){
//                    message = "The man who did not enter his name ";
//                }
//                intent.putExtra(EXTRA_MESSAGE, message);
//                startActivity(intent);
//                finish();

            }
        });
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




    }
}