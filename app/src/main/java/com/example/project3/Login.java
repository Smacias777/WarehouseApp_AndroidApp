package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class asks the user to enter credentials in order to access the other features of the app
 */
public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    /**
     * Will ensure that the data entered is found inside the database
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect to xml resources
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.loginButton);
        mCreateBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                // Event Handling in the case that their is nothing in text fields
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Please Enter an Email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please Enter a Password");
                    return;
                }

                // Event handling for password requirement
                if(password.length() < 6){
                    mPassword.setError("Password must be at least 6 characters long.");
                    return;
                }

                // Reveal the progress bar to let the user know that the app is working and not frozen
                progressBar.setVisibility(View.VISIBLE);

                // Authenticate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Register.class));
                        }
                        else{
                            Toast.makeText(Login.this, "Error has occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // if the user is not already registered then this will send them to the register screen
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    /**
     * Opens the Register activity
     * @param obj is the button that was clicked
     */
    public void goToRegister(View obj)
    {
        startActivity(new Intent(this, Register.class));
    }
}