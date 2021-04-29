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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        button.findViewById(R.id.menuButton);
        txtName = findViewById(R.id.itemTxt);
        txtBrand = findViewById(R.id.brand_input);
        txtQuant = findViewById(R.id.quantity_input);
        txtPrice = findViewById(R.id.price_input);
        txtColor = findViewById(R.id.color_input);
        txtCondition = findViewById(R.id.condition);
        txtComments = findViewById(R.id.comments);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        list = new ArrayList<>();
        final ArrayList<String> list1 = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("Items");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (final DataSnapshot dataSnapshot : snapshot.getChildren()){

                    final String user = dataSnapshot.getKey();
                    //list.add(user);

                    //Toast.makeText(Search.this, "Failed" + dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Items").child(user);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(final DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                final String user1 = dataSnapshot1.getKey();

                                //Toast.makeText(Search.this, "Failed" + dataSnapshot1.getKey(),Toast.LENGTH_SHORT).show();


                                DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Items").child(user).child(user1);
                                ValueEventListener eventListener1 = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot2 : snapshot.getChildren()){
                                            String user2 = dataSnapshot2.getKey();

                                            //Toast.makeText(Search.this, "Failed" + dataSnapshot2.getKey(),Toast.LENGTH_SHORT).show();

                                            DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Items").child(user).child(user1)
                                                    .child(user2);

                                            ValueEventListener eventListener2 = new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot dataSnapshot3 : snapshot.getChildren()){
                                                        txtName.setText(dataSnapshot3.child("name").getValue().toString());
                                                        txtBrand.setText(dataSnapshot3.child("brand").getValue().toString());
                                                        txtQuant.setText(dataSnapshot3.child("quantity").getValue().toString());
                                                        txtPrice.setText(dataSnapshot3.child("price").getValue().toString());
                                                        txtColor.setText(dataSnapshot3.child("color").getValue().toString());
                                                        txtCondition.setText(dataSnapshot3.child("condition").getValue().toString());
                                                        txtComments.setText(dataSnapshot3.child("comments").getValue().toString());

                                                        /*
                                                        list1.add(item);

                                                        val++;
                                                        if(val%8 == 0) {
                                                            String name = list1.get(4);
                                                            String brand = list1.get(0);
                                                            String quant = list1.get(6);


                                                            Item product = new Item(name, brand, quant);

                                                            //Toast.makeText(Search.this, ""+ product.getQuantity(), Toast.LENGTH_SHORT).show();

                                                            list.add(product);

                                                            Toast.makeText(Search.this, ""+ list.toString(), Toast.LENGTH_SHORT).show();

                                                            list1.clear();
                                                        }
                                                        */


                                                        //Toast.makeText(Search.this, dataSnapshot3.getValue().toString(),Toast.LENGTH_SHORT).show();
                                                    }
                                                    //myAdapter.notifyDataSetChanged();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            };
                                            r.addListenerForSingleValueEvent(eventListener2);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                };
                                re.addListenerForSingleValueEvent(eventListener1);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    ref.addListenerForSingleValueEvent(eventListener);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}