package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Update_product extends AppCompatActivity {

    DBhelper db;

    EditText editName;
    EditText editPrice;
    static EditText editCode;
    Button btnUpdate;
    ImageButton pickImag,scan_code;

    byte[] image = null;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        id = getIntent().getIntExtra("id", 0);

        db = new DBhelper(this);

        Product product = db.getProduct_by_id(id);


        editName = (EditText) findViewById(R.id.product_name);
        editCode = (EditText) findViewById(R.id.product_code);
        editPrice = (EditText) findViewById(R.id.product_price);
        pickImag = (ImageButton) findViewById(R.id.pickImg);
        scan_code = (ImageButton) findViewById(R.id.scan_icon);


        scan_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ScanCodeActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        editName.setText(product.getName());
        editCode.setText(product.getCode() );
        editPrice.setText(product.getPrice()+"");

        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        pickImag.setImageBitmap(bitmap);
        image = getBytes(bitmap);


        if (ContextCompat.checkSelfPermission(Update_product.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Update_product.this,
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // trakablek lmenu fel action bar

        getMenuInflater().inflate(R.menu.delete_menu, menu);  // inflate trakabhoolek

        return super.onCreateOptionsMenu(menu);
    }

//-------------------------------------------------------
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_delete:

                showAlert();  // ken nzelt 3la iconet delet tatla3 show dialog bech yzid ythabat

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlert() {

        AlertDialog.Builder alertBilder = new AlertDialog.Builder(this);
        alertBilder.setTitle("Confirmition")
                .setMessage("Dude u sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete contact
                         db.delete_Product(id);
                         finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = alertBilder.create();
        dialog.show();
    }

    public void update_meth(View view) {
        String name = editName.getText().toString();
        String code = editCode.getText().toString();
        Float price =Float.parseFloat(editPrice.getText().toString());


        BitmapDrawable drawable = (BitmapDrawable) pickImag.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        image = getBytes(bitmap);


        Product newProduct = new Product(id,code, name, price, image);
        db.update_Product(newProduct);
        Toast.makeText(Update_product.this, "updated success", Toast.LENGTH_LONG).show();
        finish();
    }


    //-------------------------------photo inser------------------

  /* public void openGalleries(View view) {

        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT); // te5ou mo7tewe chte5ou image mn ay naw3 /*
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100); // lezem te5ou m3ak limage result

        // request code t7ot eli t7eb bech ba3d tthabat kn rja3 bekel num ta3ref rahi mchet

    }

*/

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }



  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

