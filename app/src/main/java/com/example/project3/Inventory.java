package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory extends AppCompatActivity {

    private static int val = 0;
    private TextView text;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> finalList = new ArrayList<>();

    private DatabaseReference reff;
    private ListView theList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        theList = findViewById(R.id.theList);
        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database


        //the following retrieves "Items" children, which are the names of the items
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String name = ds.getKey();

                    //------------------------------------------------------------------------------------------------------------------------
                    // we are now referencing the child's child by its name (Ex: Items>>iphone 8)
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Items").child(name);
                    ValueEventListener eventListener2 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds2 : snapshot.getChildren() )
                            {
                                final String name2 = ds2.getKey();
                                //-----------------------------------------------------------------------------------------------------------
                                DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Items").child(name).child(name2);
                                ValueEventListener eventLister3 = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for(DataSnapshot ds3 : snapshot2.getChildren())
                                        {
                                            String name3 = ds3.getKey();
                                            // ----------------------------------------------------------------------------------------------
                                            DatabaseReference r = FirebaseDatabase.getInstance().getReference().child("Items").child(name).child(name2).child(name3);
                                            r.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot ds4 : snapshot.getChildren())
                                                    {
                                                        String details = ds4.getValue().toString();
                                                     //   Toast.makeText(Inventory.this,"|"+ details+"|", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct

                                                      //  newList = new DateFormatSymbols().getMonths();
                                                        list.add(details);
                                                        checkList(list);
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                }
                                            });
                                            // ----------------------------------------------------------------------------------------------
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                };
                                re.addListenerForSingleValueEvent(eventLister3);
                                //-------------------------------------------------------------------------------------------------------------
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    };
                    ref.addListenerForSingleValueEvent(eventListener2);
                    //---------------------------------------------------------------------------------------------------------------------------
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        };
        reff.addListenerForSingleValueEvent(eventListener);
    }



    /**
     * Every single value from the database is passed through this method.
     * After every 8 elements, it combines them together as a single string.
     * Where one item is 8 elements. After being combined each item would have
     * a single string to be displayed. These strings are then placed into 'finalList'.
     * @param arr arraylist containing specific properties for each item from the database
     */
    public void checkList(ArrayList<String> arr)
    {
        ArrayList<String> newArray = new ArrayList<>();
        String value = "";

        val++;
        if(val%8 == 0)
        {
            for(int i = 0; i < 8; i++)
            {
                value = value + arr.get(i) + " \n";
            }
            arr.clear();
            finalList.add(value);
        }
    }

    /**
     * Called when the display button is clicked.
     * It adds the 'finalList' into the arrayAdpter in order for it to be placed within the ListView.
     * @param obj
     */
    public void testing(View obj)
    {
        Toast.makeText(Inventory.this, "Items: "+finalList.size(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct\
        ArrayAdapter<String> arry = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, finalList);
        theList.setAdapter(arry);
    }



}
/*

  ArrayList<String> newArray = new ArrayList<>();
        ArrayList<String> newlyFormatedValues = new ArrayList<>();

        // the outer loop, loops the number of items found in the database (8 properties for each)
        for(int i = 0; i < arr.size()/8; i++)
        {
            String val = ""; // this variable will contain a large string of all the properties of a single item
            newArray.clear();
            // will loops through all eight of every item's properties
            for(int j = 0; j < 8; j++) {
                // retrieve the every 8 elements (every 8 elements are the property of a single item)
                newArray.add(list.get(j));
                list.remove(i); // will remove it so that we the other elements can be retrieved without changing the index
            }
            // this for loop combines the properies into a single string to be placed into the ListView
            // "val" - contains this new string value
            for(int k = 0; k < newArray.size(); k++)
            {
                val = val + " " +newArray.get(i);
            }
            newlyFormatedValues.add(val);
        }

----------------------------------------------------------------------------
   reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    String name = (String) childDataSnapshot.child("iphone 8").getValue();
                    //Do the same for other properties.
                    Toast.makeText(Inventory.this,"|"+ name+"|", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

---------------------------------------------------------------------------------

reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, String> someMap = (Map<String, String>) snapshot.getValue();


         //       Toast.makeText(Inventory.this, "name", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
             //  String val = someMap.get(key.get(0));

                /*
                for(DataSnapshot val : snapshot.getChildren())
                {
                    Item item = val.getValue(Item.class);
                    list.add(item);
                    Toast.makeText(Inventory.this, list.get(0).getName(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct

                }
                Collection<String> collection = someMap.values();
                Object [] group = collection.toArray(); // retrieves the names of the phones in the database


                for(int i = 0; i < group.length; i++)
                {
                    Toast.makeText(Inventory.this, group[i].toString(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct


                }

            }
@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });

*/