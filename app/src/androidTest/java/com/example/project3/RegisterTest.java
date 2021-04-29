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

public class RegisterTest extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword;
    Button mRegisterButton;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    /**
     * @param savedInstanceState
     **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connect to xml resources
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterButton = findViewById(R.id.loginButton);
        mLoginBtn = findViewById(R.id.createText);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        mFullName.setText("full name");
        mEmail.setText("email@email.com");
        mPassword.setText("password");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * @param v
             **/
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                assertEquals(email, "email@email.com");
                assertEquals(password, "password");

                // Reveal the progress bar to let the user know that the app is working and not frozen
                progressBar.setVisibility(View.VISIBLE);

                //Register the user in firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    /**
                     * @param task
                     **/
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterTest.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        else{
                            Toast.makeText(RegisterTest.this, "Error has occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // if the user is already registered then this will send them to the login screen
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * @param v
             **/
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}