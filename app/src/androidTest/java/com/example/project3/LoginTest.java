package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import static org.junit.Assert.assertEquals;

public class LoginTest extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    /**
     * @param savedInstanceState
     **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // connect to xml resources
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mEmail.setText("email@email.com");
        mPassword.setText("password");
        mLoginBtn = findViewById(R.id.loginButton);
        mCreateBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * @param v
             **/
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                assertEquals(email, "email@email.com");
                assertEquals(password, "password");
                /**
                 * @return
                 **/
                // Event handling for password requirement
                if(password.length() < 6){
                    mPassword.setError("Password must be at least 6 characters long.");
                    return;
                }
            }
        });

        // if the user is not already registered then this will send them to the register screen
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * @param v
             **/
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
    /**
     * @param obj
     **/
    public void goToRegister(View obj)
    {
        startActivity(new Intent(this, Register.class));
    }
}