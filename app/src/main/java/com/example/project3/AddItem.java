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

public class AddItem extends AppCompatActivity {

    private String name = "";
    private String type = "";
    private String brand = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";

    @Override
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

         //   Toast toast = new Toast.makeText(getApplicationContext(), R.string.toast_message_error, Toast.LENGTH_SHORT);
         //   toast.show();
        }
        else {
            Intent intent = new Intent(this, Barcode.class);
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