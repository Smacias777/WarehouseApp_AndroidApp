package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    AutoCompleteTextView textsearch;
    RecyclerView listData;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Items");

        listData = (RecyclerView)findViewById(R.id.listData);
        textsearch = (AutoCompleteTextView)findViewById(R.id.textSearch);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listData.setLayoutManager(layoutManager);

        populateSearch();

    }

    private void populateSearch() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ArrayList<String> names = new ArrayList<>();
                    for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                        String n = dataSnapshot.child("name").getValue(String.class);
                        names.add(n);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, names);
                    textsearch.setAdapter(adapter);
                    textsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selection = parent.getItemAtPosition(position).toString();
                            getName(selection);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }


    private void getName(String selection) {

        Query  query = ref.orderByChild("name").equalTo(selection);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    ArrayList<Item> list = new ArrayList<>();
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Item nameInfo = new Item(ds.child("name").getValue(String.class), ds.child("type").getValue(String.class), ds.child("brand").getValue(String.class),
                                ds.child("condition").getValue(String.class), ds.child("quantity").getValue(String.class), ds.child("price").getValue(String.class),
                                ds.child("color").getValue(String.class), ds.child("comments").getValue(String.class));
                        list.add(nameInfo);
                    }
                    MyAdapter adapter = new MyAdapter(list, Search.this);
                    listData.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(eventListener);

    }




    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private ArrayList<Item> list;
        private Context mContext;

        public MyAdapter(ArrayList<Item> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Item item = list.get(position);
            holder.name.setText(item.getName());
            holder.brand.setText(item.getBrand());
            holder.quantity.setText(item.getQuantity());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView type;
            TextView brand;
            TextView condition;
            TextView quantity;
            TextView price;
            TextView color;
            TextView comments;

            public MyViewHolder(View view){
                super(view);

                name = itemView.findViewById(R.id.itemName);
                brand = itemView.findViewById(R.id.brand);
                quantity = itemView.findViewById(R.id.quantity);
            }
        }
    }
}