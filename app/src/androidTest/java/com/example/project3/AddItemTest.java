package com.example.project3;
import org.junit.Test;

import static org.junit.Assert.*;

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

public class AddItemTest
{
    public static final String MESSAGE = "Something"; // key used to pass data using 'intent extras'
    private String name = "";
    private String type = "";
    private String brand = "";
    private String condition = "";
    private String quantity = "";
    private String price = "";
    private String color = "";
    private String comments = "";

    @Test
    public void chooseType()
    {
        String a = "Iphone";

        if(a.equals("Android")) {
            assertEquals("Android", a);
        }
        else {
            assertEquals("Iphone", a);
        }
    }

    @Test
    public void chooseCondition() {
        String b = "Used";
        if(b.equals("New")) {
            assertEquals("New", b);
        }
        else {
            assertEquals("Used", b);
        }
    }

}

