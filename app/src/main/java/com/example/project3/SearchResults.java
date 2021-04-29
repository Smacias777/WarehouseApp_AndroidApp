package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Simply outputs the information of a specific barcode that is scanned
 */
public class SearchResults extends AppCompatActivity {

    private TextView name;
    private TextView brand;
    private TextView color;
    private TextView comments;
    private TextView condition;
    private TextView price;
    private TextView quantity;
    private TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        name = findViewById(R.id.name_);
        brand = findViewById(R.id.brand_);
        color = findViewById(R.id.color_);
        comments = findViewById(R.id.comments_);
        condition = findViewById(R.id.condition_);
        price = findViewById(R.id.price_);
        quantity = findViewById(R.id.quantity_);
        type = findViewById(R.id.type_);

        // will try to display the follwing information
        // if it fails it will let the user know, that it could not retrieve the information
        try {

            // Receive the data(name of item) from PriceQuantity class
            Intent intent = getIntent();
            String newName = intent.getStringExtra(PriceQuantity.MESSAGE2);

            String[] results = newName.split(",");

            brand.setText(brand.getText() + results[0]);
            color.setText(color.getText() + results[1]);
            comments.setText(comments.getText() + results[2]);
            condition.setText(condition.getText() + results[3]);
            name.setText(name.getText() + results[4]);
            price.setText(price.getText() + results[5]);
            quantity.setText(quantity.getText() + results[6]);
            type.setText(type.getText() + results[7]);
        }catch(Exception e)
        {
            Toast.makeText(SearchResults.this, "Info could not be found!" , Toast.LENGTH_SHORT).show();
        }

    }


}