package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Add_product extends AppCompatActivity {

    EditText editName  , editPrice ;
    static EditText edittCode;

    Button btnConfirm;
    ImageButton pickImag ,scan_code;

    byte[] image = null;
    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // declari lbase
        db = new DBhelper(this);

        editName= (EditText) findViewById(R.id.product_name);
        edittCode= (EditText) findViewById(R.id.product_code);
        editPrice= (EditText) findViewById(R.id.product_price);

        pickImag = (ImageButton) findViewById(R.id.imageView);

if (ContextCompat.checkSelfPermission(Add_product.this,
        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
    ActivityCompat.requestPermissions(Add_product.this,
            new String[]{
                    Manifest.permission.CAMERA
            },
            100);
}

pickImag.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // open camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }
});



    }


    public void add_product_function(View view) {
        String name = editName.getText().toString();
        String code = edittCode.getText().toString();
        float price = Float.parseFloat(editPrice.getText().toString());


        BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        image = getBytes(bitmap);

        if(name.equals("") || code.equals("") || price ==0 || image == null) {
            AlertDialog dialog = new AlertDialog.Builder(Add_product.this)
                    .setTitle("Champs Manquant")
                    .setMessage("Remplir les champs !!")
                    .setPositiveButton("Ok", null)
                    .show();
        }
          else {



        Product contact = new Product(code,name, price, image);

        db.addProduct(contact);

        Toast.makeText(Add_product.this, "success", Toast.LENGTH_LONG).show();
        finish();
    }}

    //-------------------------------photo inser------------------

    /*public void openGalleries(View view) {

        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT); // te5ou mo7tewe chte5ou image mn ay naw3 /*
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100); // lezem te5ou m3ak limage result

        // request code t7ot eli t7eb bech ba3d tthabat kn rja3 bekel num ta3ref rahi mchet

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {  //requestCode==result code

            Uri uri = data.getData(); // data mawjoooda fel uri

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                pickImag.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);

            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }

        }
    }
    */


    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
if( requestCode==100){// get capture image
    Bitmap captureImage =(Bitmap) data.getExtras().get("data");
    // set the capture image to the image view
    pickImag.setImageBitmap(captureImage);

}


}


}