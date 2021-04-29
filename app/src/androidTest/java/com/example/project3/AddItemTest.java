package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static org.junit.Assert.assertEquals;

public class AddItemTest extends AppCompatActivity{

    public static final String MESSAGE = "Something"; // key used to pass data using 'intent extras'
    private String name = "";
    private String type = "";
    private String brand = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";
    public void clickEnter(View obj) {
        name = "phone";
        brand = "idk";
        quantity = "6";
        price = "40";
        color = "red";
        comments = "new";
        Intent intent = new Intent(this, Barcode.class);
            // passing the items name so that it can be used to create a qr code
        String message = name;
        intent.putExtra(MESSAGE, message);
        startActivity(intent);

    }

    public void chooseType(View obj)
    {
        Button androidButton = (Button) obj;
        androidButton.getText().equals("Android");
        assertEquals("Android", type);

        Button iPhoneButton = (Button) obj;
        iPhoneButton.getText().equals("iPhone");
        assertEquals("Iphone", type);
    }

    public void chooseCondition(View obj) {
        Button newButton = (Button) obj;
        newButton.getText().equals("New");
        assertEquals("New", condition);

        Button usedButton = (Button) obj;
        usedButton.getText().equals("Used");
        assertEquals("Used", condition);
    }
}

