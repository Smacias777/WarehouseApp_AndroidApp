package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class takes specific properties of an item from the user. Then
 * it calls the Barcode class.
 */
public class AddItem extends AppCompatActivity {

    public static final String MESSAGE = "Something"; // key used to pass data using 'intent extras'
    private String name = "";
    private String type = "";
    private String brand = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";

    private DatabaseReference reff;

    @Override
	/**
     * Creates the activity layout
	* @param savedInstanceState
	*/
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }
    /**
     * Will open new activity when 'enter' button is clicked
     * @param obj is the object clicked
     */
    public void clickEnter(View obj)
    {
        reff = FirebaseDatabase.getInstance().getReference().child("Items"); // reference to our database

        name = ((TextView) findViewById(R.id.name_input)).getText().toString();
        brand = ((TextView) findViewById(R.id.brand_input)).getText().toString();
        quantity = ((TextView) findViewById(R.id.quantity_input)).getText().toString();
        price = ((TextView) findViewById(R.id.price_input)).getText().toString();
        color = ((TextView) findViewById(R.id.color_input)).getText().toString();
        comments = ((TextView) findViewById(R.id.comments_input)).getText().toString();

        // if any option is blank then do it again
        if(name.equals("") || type.equals("") || brand.equals("") || condition.equals("") || quantity.equals("") || price.equals("") || color.equals("") || comments.equals(""))
        {
            TextView text = (TextView) findViewById(R.id.error_message);
            text.setText("Enter all Fields!");
            text.setTypeface(null, Typeface.BOLD);                 // makes text bold
            text.setTextColor(Color.parseColor("#FF0000")); // changes text color to red
        }
        else {
            // Write a message to the database
            // Creating a new new 'Item' object (will be placed into database)
            Item newItem = new Item(name, type, brand, condition, quantity, price, color, comments);

            // Adding 'newItem' - placed into new child(bracket) by it name
            // The values are using toLowerCase(), this will help organize them in the database
            reff.child(name.toLowerCase()).child(color.toLowerCase()).child(condition.toLowerCase()).setValue(newItem);
            Intent intent = new Intent(this, Barcode.class);


            // passing the item's name (as a intent extra bundle) so that it can be used to create a qr code
            String message = name + "," + type + "," + brand+ ","+ condition+ ","+ quantity+","+ price +","+ color + "," + comments;
            intent.putExtra(MESSAGE, message);
            startActivity(intent);
        }
    }
    /**
     * Sets the type, based on the button clicked by user
     * @param obj is the button that was clicked
     */
    public void chooseType(View obj)
    {
        Button clickedButton = (Button) obj;
        if(clickedButton.getText().equals("Android"))
        {
            type = "Android";
        }
        if(clickedButton.getText().equals("iPhone"))
        {
            type = "iPhone";
        }
    }
    /**
     * Sets the condition, based on the button clicked by user
     * @param obj is the button that was clicked
     */
    public void chooseCondition(View obj)
    {
        Button clickedButton = (Button) obj;
        if(clickedButton.getText().equals("New"))
        {
            condition = "New";
        }
        if(clickedButton.getText().equals("Used"))
        {
            condition = "Used";
        }
    }

}