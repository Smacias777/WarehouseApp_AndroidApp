package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    // action for when "Add new Item" is selected
    public void addItem(View obj)
    {
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }

    public void scanIn(View obj)
    {
        Intent intent  = new Intent(this, ScanIn.class);
        startActivity(intent);
    }
}

