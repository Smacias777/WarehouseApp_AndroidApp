package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
    Button button;
    EditText txtName, txtBrand, txtQuant, txtPrice, txtColor, txtCondition, txtComments;
    DatabaseReference database;
    ArrayList<Item> list;
    private int val=0;

    /**
     * Initialize values, and add eventListeners for the activity's functionality
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        button = findViewById(R.id.homeBtn);
        txtName = findViewById(R.id.itemTxt);
        txtBrand = findViewById(R.id.brand_input);
        txtQuant = findViewById(R.id.quantity_input);
        txtPrice = findViewById(R.id.price_input);
        txtColor = findViewById(R.id.color_input);
        txtCondition = findViewById(R.id.condition);
        txtComments = findViewById(R.id.comments);

        /**
         * create an onClickListener for when the button is pressed to go back to the main menu
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
            }
        });

        list = new ArrayList<>();
        final ArrayList<String> list1 = new ArrayList<>();

        // establish a connection to the database
        database = FirebaseDatabase.getInstance().getReference("Items");

        // get intent from MyAdapter class which holds the data for an item inside string key
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        database.orderByKey().equalTo(key);

        // split up the data
        String b[] = key.split(",");
        final String name = b[0];
        String brand = b[1];
        String quantity = b[2];
        String price = b[3];
        String color = b[4];
        String condition = b[5];
        String comments = b[6];

        // set the data to the corresponding textView box
        txtName.setText(name);
        txtBrand.setText(brand);
        txtQuant.setText(quantity);
        txtPrice.setText("$" + price);
        txtColor.setText(color);
        txtCondition.setText(condition);
        txtComments.setText(comments);

    }
}
