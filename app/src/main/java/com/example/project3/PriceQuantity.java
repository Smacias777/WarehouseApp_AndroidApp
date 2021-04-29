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
 * Allows the user to find specific information of any item
 */
public class PriceQuantity extends AppCompatActivity {

    public static final String MESSAGE2 = "Something_else"; // key used to pass data using 'intent extras'
    private final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;
    private DatabaseReference reff;

    Button menuButton;

    @Override
    /**
     * Retrieves the proper information to retrieve the necessary information for a given item
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_qunatity);

        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Search.class));
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database
        setupPermissions();  // sets permission to use camera

        CodeScannerView scannerView = findViewById(R.id.scannerView);
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
                    public void run()
                    {
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
                            Toast.makeText(PriceQuantity.this, name.toLowerCase() + " - " + color.toLowerCase(), Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct

                            // will traverse through the database until it reaches the right section (name>>color>>condition)
                            reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // tries to retrieve info from database to update quantity (if it cant, it will inform user that the qr code is not in the system)
                                    try {
                                        // retriving current quantity from database
                                        Map<String, String> someMap = (Map<String, String>) snapshot.getValue();
                                        String brand = someMap.get("brand");
                                        String color = someMap.get("color");
                                        String comments = someMap.get("comments");
                                        String condition = someMap.get("condition");
                                        String name = someMap.get("name");
                                        String price = someMap.get("price");
                                        String quantity = someMap.get("quantity");
                                        String type = someMap.get("type");

                                        // creating a string with all the properties of the given qr code
                                        String message = brand + "," + color + "," + comments + "," + condition + "," + name + "," + price + "," + quantity + "," + type;

                                        goToResults(message);


                                    } catch (Exception e) {
                                        Toast.makeText(PriceQuantity.this, "QR code not found!", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        // if its unable to read barcode it will inform the user that the QR code is not in the database
                        catch(Exception ee)
                        {
                            Toast.makeText(PriceQuantity.this, "QR code not found!", Toast.LENGTH_SHORT).show();  // displays toast, displaying name, helping user know that the item scanned it correct
                            return;
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

    @Override
    /**
     * Requests permission to access the camera
     * @param requestCode is the request code
     * @param permissions is a string that seeks to access the camera
     * @param grantResults is a request code
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Will allow user to go to the SearchResult class, to display the item's details
     * @param a is a String containing an item's details
     */
    public void goToResults(String a)
    {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(MESSAGE2, a);
        startActivity(intent);
    }



}