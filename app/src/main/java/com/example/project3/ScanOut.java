package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows users to scan out items, these items are places on a different section of the database
 */
public class ScanOut extends AppCompatActivity {

    private final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;
    private DatabaseReference reff; // will reference database bracket that contains removed items
    private DatabaseReference ref; // will reference database bracket that contains added items

    @Override
    /**
     * Analyses the scanned QR code to see if it is one that was generated within the app. If not it will
     * let the user know that it did not recognize it
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_out);
        setupPermissions();  // sets permission to use camera

        reff = FirebaseDatabase.getInstance().getReference().child("Removed"); // reference to our database
        ref = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback()
        {
            @Override
            public void onDecoded(@NonNull final Result result)  // "result" contains the string that the qr code represents
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    // this method decides what happens after the qr code is scanned
                    public void run() {
                        // retrieve text from qr code, seperate each word by spliting at every comma (commas where added in the AddItem class)
                        String [] arr = result.getText().split(",");

                        // will attempt to read barcode as if it was generated from within the app
                        try {
                            final String name = arr[0];
                            final String type = arr[1];
                            final String brand = arr[2];
                            final String condition = arr[3];
                            final String quantity = arr[4];
                            final String price = arr[5];
                            final String color = arr[6];
                            final String comments = arr[7];
                            Toast.makeText(ScanOut.this, name.toLowerCase() + " - " + color.toLowerCase(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct

                            final Item newItem = new Item(name,type,brand, condition, quantity, price,color ,comments);

                            // Will get a "snapshot" of all the items in the database, we wish to only delete the item only if
                            // it is found, if not a toast will mention that is was not found.
                            DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("Items");
                            ValueEventListener eventLister = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    // will traverse through each of children (which are the names of each item)
                                    for(DataSnapshot ds3 : snapshot2.getChildren())
                                    {
                                        String test = ds3.getKey();
                                        // if scanned item is found in the database (will remove it from 'items' section & will add it to the 'removed' section
                                        if(test.toLowerCase().equals(name.toLowerCase()))
                                        {
                                            // will remove the specific item from the "items" section
                                            ref.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).removeValue();
                                            // first will add the item into a new section for all removed items
                                            reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).setValue(newItem);
                                            Toast.makeText(ScanOut.this, "Item Removed!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(ScanOut.this, "Item Not Found!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            };
                            re.addListenerForSingleValueEvent(eventLister); // will allow for the event to occur

                        }catch(Exception e)
                        {
                            Toast.makeText(ScanOut.this, "QR code not found!", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                        }

                        }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCodeScanner.startPreview();
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    /**
     * Sets up permission to access the camera
     */
    private void setupPermissions()
    {
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA} , CAMERA_REQUEST_CODE );
        }
    }
    /**
     * Requests permission to access the camera
     * @param requestCode is the request code
     * @param permissions is a string that seeks to access the camera
     * @param grantResults is a request code
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     *
     * @param obj is the button that was clicked
     */
    public void test(View obj)
    {
        startActivity(new Intent(getApplicationContext(), Inventory2.class));
    }

}