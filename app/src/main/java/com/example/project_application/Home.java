package com.example.project_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.parceler.Parcels;

import static android.content.Intent.ACTION_CALL;
import static android.content.Intent.ACTION_DIAL;

public class Home extends AppCompatActivity {
    Button scan_product_btn;


    DBhelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scan_product_btn = (Button) findViewById(R.id.start);


        db = new DBhelper(this);

        scan_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();


            }
        });

    }

    void scanCode() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);


        intentIntegrator.setCaptureActivity(CaptureAct.class);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("scanning code");
        intentIntegrator.initiateScan();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {

                // Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                Product product = db.getProduct_by_code_a_barre(result.getContents()); // hedheya lproduit eli nhebou nab3thouh
                Parcelable parcelable = Parcels.wrap(product);
                Intent intent = new Intent(this, Show_product.class);
                intent.putExtra("produit_scanee", parcelable);
                startActivity(intent);


            } else {
                Toast.makeText(this, "no result", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }


    // my options menu


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item3:


                String number = "21900520";

                Uri uri = Uri.parse("tel:" + number);
                Intent intent = new Intent(ACTION_DIAL, uri);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    startActivity(intent);

                }
                startActivity(intent);

                return true;
            case R.id.item4:

                bonneAffaireMarket();
                return true;
            case R.id.item5:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void signout() {
        Intent intent = new Intent(this, Login_activity.class);

        startActivity(intent);
        Toast.makeText(this, "Sign out ", Toast.LENGTH_SHORT).show();

    }

    private void bonneAffaireMarket() {
        Uri uri = Uri.parse("https://www.facebook.com/BonneAffaireMarket/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void callManager() {

        Uri uri = Uri.parse("tel:21900520");
        Intent i = new Intent(ACTION_CALL, uri);

        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(i);
    }



}