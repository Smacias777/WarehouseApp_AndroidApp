package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * Generates a specific QR code for a unique item
 */
public class Barcode extends AppCompatActivity {

    private Button savingImage;
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQrBtn;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
	/**
     * Generates a qr code and displays it on the screen.
     * It then gives the user the option to save it in their gallery
	* @param savedInstanceState
	**/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        savingImage = findViewById(R.id.saveImage);

        //request permission to read and write to the gallery
        ActivityCompat.requestPermissions(Barcode.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(Barcode.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        // Receive the data(name of item) from AddItem class
        Intent intent = getIntent();
        String newName = intent.getStringExtra(AddItem.MESSAGE);

        // initializing the ImageView that will be the qr code
        qrCodeIV = findViewById(R.id.idIVQrcode);

        // getting the windowmanager service.
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // initiallizing point object.
        Point point = new Point();
        display.getSize(point);

        // getting width and height
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(newName, null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set up in a way that the it can be seen as an actual image
            qrCodeIV.setImageBitmap(bitmap);

        } catch (WriterException e)  {
            Log.e("Tag", e.toString());
        }

        // saves new barcode into the gallery/photos
        savingImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ImageView iv = (ImageView)findViewById(R.id.idIVQrcode);
                BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
                Bitmap newBitmap = draw.getBitmap();
                FileOutputStream outStream = null;
                File file = Environment.getExternalStorageDirectory();
                File a = new File(file.getAbsolutePath() + "/Pictures");
                a.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(a, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();

                    Intent intent2 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent2.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent2);
                }catch (IOException e)
                {
                    Log.e("Tag", e.toString());
                }
            }
        });
    }

    /**
     * Returns user to the main menu
     * @param obj is the button that was clicked
     */
    public void clickMainMenu(View obj)
    {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}