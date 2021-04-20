package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScanIn extends AppCompatActivity {
    private final int CAMERA_REQUEST_CODE = 101;
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_in);

        setupPermissions();  // sets permission to use camera

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback()
        {
            @Override
            public void onDecoded(@NonNull final Result result)  // results contains the 'word' that the qr code represents
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        Toast.makeText(ScanIn.this, result.getText(), Toast.LENGTH_SHORT).show();  // displays toast of what the QR code represents
                     //   TextView text = findViewById(R.id.camera_text);
                     //   text.setText(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCodeScanner.startPreview();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void setupPermissions()
    {
        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if(permission != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA} , CAMERA_REQUEST_CODE );
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void gotoInventory(View obj)
    {
        Intent intent = new Intent(this, Inventory.class);
        startActivity(intent);
    }

}