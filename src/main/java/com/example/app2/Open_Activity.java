package com.example.app2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Open_Activity extends AppCompatActivity {

    GridLayout mainGrid;

    private String username;

    private TextView welcome_user_text;

    public static String getRandomChestItem(ArrayList<String> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        mainGrid =(GridLayout)findViewById(R.id.mainGrid);
        welcome_user_text = findViewById(R.id.title_view_open);

        Intent intent = getIntent();
        username = "";
        if (intent != null){
            username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            Log.d("username", "onCreate:open " + username);

        }

        ArrayList<String> welcome_list = new ArrayList<>();
        welcome_list.add("Welcome ");
        welcome_list.add("Hello , ");
        welcome_list.add("What up , ");

        welcome_user_text.setText(getRandomChestItem(welcome_list) + username);


        // set Event
        setSingleEvent(mainGrid);



    }
    private void setSingleEvent(GridLayout mainGrid){

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finali = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    msg(""+finali);
                    final Intent intent;
                    if (finali == 0){
                        //ITEMS
                        intent = new Intent(Open_Activity.this,Real_main_activity.class);
                        intent.putExtra(MainActivity.EXTRA_MESSAGE,username);
                    }else if (finali == 1){
                        //SEARCH
                        intent = new Intent(Open_Activity.this,Search_Sopping_Activity.class);

                    }else if (finali == 2){
                        // CHANGE NAME
                        intent = new Intent(Open_Activity.this,MainActivity.class);

                    }else if (finali == 3){
                        //LOGOUT
                        intent = new Intent(Open_Activity.this,Real_main_activity.class);

                    }else {
                        intent = new Intent(Open_Activity.this,MainActivity.class);

                    }
                    startActivity(intent);






                }
            });

        }
    }

    private void msg(String text){
        Toast.makeText(this,text,Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Open_Activity.super.onBackPressed();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}