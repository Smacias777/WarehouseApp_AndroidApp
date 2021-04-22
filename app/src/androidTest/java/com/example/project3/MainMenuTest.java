package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenuTest extends AppCompatActivity {

    // action for when "Add new Item" is selected
    /**
     * @param obj
     **/
    public void addItem(View obj)
    {
        Button addItemButton = (Button) obj;
        addItemButton.getText().equals("Add Item");
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }
    /**
     * @param obj
     **/
    public void scanIn(View obj)
    {
        Button scanInButton = (Button) obj;
        scanInButton.getText().equals("Scan Item");
        Intent intent  = new Intent(this, ScanIn.class);
        startActivity(intent);
    }
    /**
     * @param view
     **/
    public void logout(View view){
        Button logoutButton = (Button) view;
        logoutButton.getText().equals("Logout");
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}